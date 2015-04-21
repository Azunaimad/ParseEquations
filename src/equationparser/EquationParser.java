package equationparser;


import com.sun.org.apache.xpath.internal.SourceTree;
import namedstruct.Array;
import namedstruct.ArrayStructure;
import namedstruct.Const;
import namedstruct.ConstStructure;
import tree.InToPost;
import tree.Postfix2Tree;
import tree.TreeNode;
import tree.TreeTraversal;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationParser {

    public ArrayStructure arrays;
    public ArrayStructure randArrays;
    public ConstStructure constants;
    private Map<String, String> new_oldArrayNames;
    private Map<String, String> new_oldRandArrayNames;

    private int nOfElements;
    private RandomGeneratorType generator;
    private int nOfIterations;


    private static String randRegExp = "rand\\(\\w+,\\w\\)";
    private static String arrayRegExp = "\\w+\\(\\w+[+-]\\d+\\)|\\w+\\(\\w+\\)";

    //TODO: сделать так, чтобы было удобно работать с последовательностью действий detect -> setVal -> calculate
    public EquationParser(int nOfElements, int nOfIterations, RandomGeneratorType generator){
        this.nOfElements = nOfElements;
        this.nOfIterations = nOfIterations;
        this.generator = generator;
    }

    public void detectArrays(String infixStr){
        /// Detect parameter, that we want to model in this equation
        String[] tmp = infixStr.split("=");
        String arrayName = tmp[0].replaceAll("\\(.*\\)", "");
        Array array = new Array(arrayName, nOfElements, nOfIterations);
        if(arrays == null)
            arrays = new ArrayStructure();
        arrays.addArray(array);

        /// Detect rand arrays

        //Get rand(.. , ..)
        String[] randExpr = findArrays(infixStr, randRegExp);

        //Get rand array names
        Pattern patternRand = Pattern.compile("\\w+,\\w");
        StringBuilder sb = new StringBuilder();
        Matcher matcherRand;

        for (String randExprElement : randExpr) {
            matcherRand = patternRand.matcher(randExprElement);
            if (matcherRand.find()) {
                sb.append(matcherRand.group(0));
                sb.append(";");
            }
        }
        String[] inPar = (sb.toString().split(";|,"));
        String[] randArrayNames = new String[inPar.length/2];
        if(inPar.length >= 2)
            for(int i=0; i<inPar.length; i+=2){
                randArrayNames[i/2] = "rand_"+inPar[i];
            }


        //Create named rand array if it not exist
        for (String randArrayName : randArrayNames) {
            if(randArrays == null){
                Array rAr = new Array(randArrayName, nOfElements, nOfIterations);
                randArrays = new ArrayStructure();
                randArrays.addArray(rAr);
            }
            else if (!randArrays.exist(randArrayName)) {
                Array rAr = new Array(randArrayName, nOfElements, nOfIterations);
                randArrays.addArray(rAr);
            }
        }
    }


    public void detectConstants(String infixStr){
        String[] tmp = infixStr.split("=");
        infixStr = tmp[1].replaceAll("\\w+\\(\\w+\\)|rand\\(\\w+,\\w+\\)|\\d+","");
        tmp = infixStr.split("[\\(\\)+/*-]");
        StringBuilder sb = new StringBuilder();
        for(String s : tmp)
            if(!s.equals("")){
                sb.append(s);
                sb.append(";");
            }
        String[] constNames = sb.toString().split(";");
        for (String constName : constNames) {
            if (constants == null) {
                Const c = new Const(constName);
                constants = new ConstStructure();
                constants.addConst(c);
            } else if (!constants.exist(constName)) {
                Const c = new Const(constName);
                constants.addConst(c);
            }
        }
    }

    //TODO:упрощенная версия, переписать для разных генераторов
    public void fillRandArray(){
        try{
            Random randomGenerator = new Random();
            for(int i=0; i<randArrays.length(); i++)
                for(int j=0; j<nOfElements; j++)
                    for(int k=0; k<nOfIterations; k++){
                        double rnd = randomGenerator.nextGaussian();
                        randArrays.setElement(i, j, k, rnd);
                    }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public String renameForConversion(String infixStr){
        String result = infixStr;
        String[] newArrayNames = new String[arrays.length()];
        for(int i=0; i<arrays.length(); i++)
            newArrayNames[i] = "arr"+i+"arr";
        String[] newRandArrayNames = new String[randArrays.length()];
        for(int i=0; i<randArrays.length(); i++)
            newRandArrayNames[i] = "rand"+i+"rand";

        new_oldArrayNames = new HashMap<String, String>();
        for(int i=0; i<arrays.length(); i++)
            new_oldArrayNames.put(newArrayNames[i],arrays.getArray(i).getName());


        new_oldRandArrayNames = new HashMap<String, String>();
        for(int i=0; i<randArrays.length(); i++){
            new_oldRandArrayNames.put(newRandArrayNames[i],randArrays.getArray(i).getName());
        }

        for(int i=0; i<arrays.length(); i++){
            String regExp = arrays.getArray(i).getName()+"\\(\\w+[+-]\\d+\\)|" +
                    arrays.getArray(i).getName() +"\\(\\w+\\)";
            result = result.replaceAll(regExp,newArrayNames[i]);
        }

        for(int i=0; i<randArrays.length(); i++){
            String name = randArrays.getArray(i).getName();
            name = name.substring(5);
            String str = "rand\\("+name+",t\\)";
            result = result.replaceAll(str,newRandArrayNames[i]);
        }

        return result;

    }

    private static String[] findArrays(String infixStr, String regExp){
        String[] randExpr;
        StringBuilder sb = new StringBuilder();

        Pattern patternRand = Pattern.compile(regExp);
        Matcher matcherRand = patternRand.matcher(infixStr);
        while (matcherRand.find()){
            sb.append(matcherRand.group(0));
            sb.append(";");
        }
        randExpr = sb.toString().split(";");
        return randExpr;
    }

    public String[] renameForTree(String[] postfixStrArr){
        for(int i=0; i< postfixStrArr.length; i++){
            if(postfixStrArr[i].contains("arr"))
                postfixStrArr[i] = new_oldArrayNames.get(postfixStrArr[i])+"(t)";
            if(postfixStrArr[i].contains("rand"))
                postfixStrArr[i] = new_oldRandArrayNames.get(postfixStrArr[i]);
        }
        return postfixStrArr;
    }

    public String[] splitForTree(String postfixStr, String infixStr){

        String[] tmp = infixStr.split("\\+|\\-|\\(|\\)|\\\\|\\*");

        StringBuilder sb = new StringBuilder();
        for(String s: tmp)
            if(!s.equals("")){
                sb.append(s);
                sb.append(";");
            }
        String[] operands = sb.toString().split(";");
        sb = new StringBuilder();
        int operandsUsed = 0;
        for(int i=0; i<postfixStr.length(); i++){
            char c = postfixStr.charAt(i);
            if(isOperator(c)){
                sb.append(c);
                sb.append(";");
            } else{
                sb.append(operands[operandsUsed]);
                sb.append(";");
                i+= operands[operandsUsed].length()-1;
                operandsUsed++;
            }
        }


        return sb.toString().split(";");
    }


    public void setGenerator(RandomGeneratorType generator) {
        this.generator = generator;
    }

    public void setnOfIterations(int nOfIterations) {
        this.nOfIterations = nOfIterations;
    }

    public int getnOfElements() {
        return nOfElements;
    }

    public void setnOfElements(int nOfElements) {
        this.nOfElements = nOfElements;
    }

    public void calculate(String[] infixStrArr){
        String[] rightPart = new String[infixStrArr.length];
        String[] leftPart = new String[infixStrArr.length];



        /*Scanner scanner = new Scanner(System.in);
        for(int i=0; i<arrays.length(); i++){
            System.out.println("Введите нулевое значение " + arrays.getArray(i).getName());
            String s = scanner.next();
            arrays.setElement(i,0,Double.parseDouble(s));
        }*/


        for(int i=0; i<infixStrArr.length; i++) {
            String[] temp = infixStrArr[i].split("=");
            leftPart[i] = temp[0].replaceAll("\\(.*\\)","");
            rightPart[i] = temp[1];
        }

        TreeNode[] equations = new TreeNode[rightPart.length];
        for(int i=0; i<rightPart.length; i++){
            String renamed = renameForConversion(rightPart[i]);
            InToPost itp = new InToPost(renamed);
            String converted = itp.doTrans();
            String[] splitted = splitForTree(converted,renamed);
            Postfix2Tree p2t = new Postfix2Tree();
            equations[i] = p2t.createTree(renameForTree(splitted));
        }


        fillRandArray();
        TreeTraversal treeTraversal = new TreeTraversal();

        for(int k=0; k<nOfIterations; k++)
            for(int i=0; i<nOfElements-1; i++)
                for(int j=0; j<equations.length; j++){
                    double result = treeTraversal.calcTreeFromRoot(equations[j],arrays,randArrays,constants, i,k);
                    arrays.setElement(leftPart[j],i+1,k,result);
                }
    }

    public boolean isOperator(char s){
        return s == '*' ||
                s == '-' ||
                s == '+' ||
                s == '/';
    }


}

