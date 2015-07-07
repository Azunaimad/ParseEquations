package rewrote.notations;

import java.util.Scanner;
import java.util.Stack;


public class NotationsConverter{
    private StringBuilder sb = new StringBuilder();


    public static String convertInfToPost(String infix){
        return "";
    }

    public static String convertInfToPref(String infix){
        return "";
    }

    public static String convertPostToPref(String posfix){
        return "";
    }

    public static String convertPrefToPost(String prefix){
        return "";
    }

    /**
     * TODO: Wrong!
     * http://javaingrab.blogspot.ru/2014/07/postfix-to-infix-conversion-using-stack.html
     * @param postfix
     * @return infix equation
     */
    public static String convertPostToInf(String postfix){

        return "";
    }

    public static String convertPrefToInf(String prefix){
        return "";
    }

    private static boolean isOperator(char c){
        return (c == '-' ||
                c == '+' ||
                c == '=' ||
                c == '*' ||
                c == '/' ||
                c == '^');
    }

}
