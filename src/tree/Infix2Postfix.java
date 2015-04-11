package tree;

import java.util.Scanner;
import java.util.Stack;


/**
 * Convert math expression in infix notation into postfix notation
 *
 * @author ACHCHUTHAN
 * @link http://www.java.achchuthan.org/2012/03/convert-infix-to-postfix-using-stack-in.html
 */
public class Infix2Postfix extends Stack {

    public Infix2Postfix() {
        super();
    }

    /**
     * Convert expression in infix notation into postfix notation
     * @param infixString - expression in infix notation
     * @return postfix string
     * Example: (1+2)*(3+4)/(12-5) => 12+34+*125-/
     */

    public String convertInfToPostf(String infixString) {
        String postfixString = " ";

        for (int index = 0; index < infixString.length(); ++index) {
            char chValue = infixString.charAt(index);
            if (chValue == '(') {
                push('(');
            } else if (chValue == ')') {
                Character oper = (Character) peek();
                while (!(oper.equals('(')) && !(isEmpty())) {
                    postfixString += oper.charValue();
                    pop();
                    oper = (Character) peek();
                }
                pop();
            } else if (chValue == '+' || chValue == '-') {
                //Stack is empty
                if (isEmpty()) {
                    push(chValue);
                    //current Stack is not empty
                } else {
                    Character oper = (Character) peek();
                    while (!(isEmpty() || oper.equals(new Character('(')) || oper.equals(new Character(')')))) {
                        pop();
                        postfixString += oper.charValue();
                    }
                    push(chValue);
                }
            } else if (chValue == '*' || chValue == '/') {
                if (isEmpty()) {
                    push(chValue);
                } else {
                    Character oper = (Character) peek();
                    while (!oper.equals(new Character('+')) && !oper.equals(new Character('-')) && !isEmpty()) {
                        pop();
                        postfixString += oper.charValue();
                    }
                    push(chValue);
                }
            } else {
                postfixString += chValue;
            }
        }
        while (!isEmpty()) {
            Character oper = (Character) peek();
            if (!oper.equals(new Character('('))) {
                pop();
                postfixString += oper.charValue();
            }
        }
        return postfixString;
    }

    public static void main(String[] args) {
        Infix2Postfix mystack = new Infix2Postfix();
        System.out.println("Type in an expression like (1+2)*(3+4)/(12-5)\n "
                + "with no monadic operators like in-5 or +5 followed by key");
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        System.out.println("The Expression you have typed in infix form :\n"+str);
        System.out.println("The Equivalent Postfix Expression is :\n"+mystack.convertInfToPostf(str));
    }
}
