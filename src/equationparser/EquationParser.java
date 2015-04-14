package equationparser;


import namedstruct.Array;
import namedstruct.ArrayStructure;
import namedstruct.ConstStructure;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationParser {

    public ArrayStructure arrays;
    public ArrayStructure randArrays;
    private ConstStructure constants;
    private Map<String, String> new_oldNames;

    private int nOfElements;
    private int generatorNumber;
    private int nOfIterations;


    private static String randRegExp = "rand\\(\\w+,\\w\\)";


    public EquationParser(int nOfElements){
        this.nOfElements = nOfElements;
    }

    public void detectArrays(String infixStr){
        /// Detect parameter, that we want to model in this equation
        String[] tmp = infixStr.split("=");
        String arrayName = tmp[0].replaceAll("\\(.*\\)", "");
        Array array = new Array(arrayName, nOfElements);
        if(arrays == null)
            arrays = new ArrayStructure();
        arrays.addArray(array);

        /// Detect rand arrays

        //Get rand(.. , ..)
        StringBuilder sb = new StringBuilder();

        Pattern patternRand = Pattern.compile(randRegExp);
        Matcher matcherRand = patternRand.matcher(infixStr);
        while (matcherRand.find()){
            sb.append(matcherRand.group(0));
            sb.append(";");
        }
        String[] randExpr = sb.toString().split(";");

        //Get rand array names
        patternRand = Pattern.compile("\\w+,\\w");
        sb = new StringBuilder();
        for (String randExprElement : randExpr) {
            matcherRand = patternRand.matcher(randExprElement);
            if (matcherRand.find()) {
                sb.append(matcherRand.group(0));
                sb.append(";");
            }
        }
        String[] inPar = (sb.toString().split(";|,"));
        String[] randArrayNames = new String[inPar.length/2];
        for(int i=0; i<inPar.length; i+=2){
            randArrayNames[i/2] = inPar[i];
        }

        //Create named rand array if it not exist
        for (String randArrayName : randArrayNames) {
            if(randArrays == null){
                Array rAr = new Array(randArrayName, nOfElements);
                randArrays = new ArrayStructure();
                randArrays.addArray(rAr);
            }
            else
                if (!randArrays.exist(randArrayName)) {
                Array rAr = new Array(randArrayName, nOfElements);
                randArrays.addArray(rAr);
            }
        }
    }

    private void fillRandArray(){

    }

    private String renameForConversion(String infixStr){
        return "";
    }

    private String[] renameForTree(String[] postfixStr){
        return null;
    }

    private String splitForTree(String postfixStr){
        return "";
    }


    public void setGeneratorNumber(int generatorNumber) {
        this.generatorNumber = generatorNumber;
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
}
