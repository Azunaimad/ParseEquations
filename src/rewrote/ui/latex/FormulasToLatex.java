package rewrote.ui.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum OperationsDictionary{
    SIN ("sin"),
    COS ("cos"),
    LN ("ln"),
    LG ("lg"),
    TG ("tg"),
    CTG("ctg"),
    SQRT ("sqrt");

    private final String name;

    OperationsDictionary(String s){
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}


public class FormulasToLatex {

    /**
     * Translate formula from system notation to LaTeX notation
     * @param formula - formula, which we want to translate, i.e., z[t+1]*f+e^(x+y)+sin(x)
     * @return translated formula, i.e., z_{t+1}\cdot f+e^{x+y}+\sin(x)
     */
    public static String translate(String formula){
        formula = formula.replaceAll("\\*","\\\\cdot ");

        //Translate time index into subscript: z[t] -> z_{t}
        formula = formula.replaceAll("\\[","_{");
        formula = formula.replaceAll("\\]","}");

        Matcher m;

        //Translate any expression with power in parenthesis, like: a^(x+y) -> a^{x+y}
        m = Pattern.compile("\\^\\((.*?)\\)").matcher(formula);
        while (m.find()){
            String tmp = m.group();
            formula = formula.replace(tmp,"^{"+tmp.substring(2,tmp.length()-1)+"}");
        }

        //Translate expression from operations dictionary, like:
        // sin(x) -> \sin(x)
        // sqrt(x) -> \sqrt{x}
        for(OperationsDictionary operation : OperationsDictionary.values()){
            m=Pattern.compile(operation.toString()+"\\((.*?)\\)").matcher(formula);
            while (m.find()){
                String tmp = m.group();
                String replaceTmp;
                if (operation.toString().equals("sqrt"))
                    replaceTmp = "\\" + operation.toString() + "{" +
                            tmp.substring(operation.toString().length()+1, tmp.length() - 1) + "}";
                else
                    replaceTmp = "\\" + tmp;

                formula = formula.replace(tmp,replaceTmp);
            }
        }

        return formula;
    }


    public static void main(String[] args) {
        String test = "z[t+1]=z[t]*f+z*(a+b)+k^(c+g)+sqrt(x+20)+sin(x)+x^y+y^(x+2)";
        System.out.println(FormulasToLatex.translate(test));
    }
}
