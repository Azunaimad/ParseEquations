package tree;


import namedarray.ArrayStructure;

import java.util.Scanner;

/**
 * @author Ponomarev George
 */
public class TreeTraversal {

    public double calcTreeFromRoot(TreeNode rootNode){

        switch (rootNode.type){
            case OPERAND_CONST:
                return rootNode.value;

            case OPERATOR_DIV:
                return calcTreeFromRoot(rootNode.left) / calcTreeFromRoot(rootNode.right);
            case OPERATOR_MULT:
                return calcTreeFromRoot(rootNode.left) * calcTreeFromRoot(rootNode.right);
            case OPERATOR_PLUS:
                return calcTreeFromRoot(rootNode.left) + calcTreeFromRoot(rootNode.right);
            case OPERATOR_MINUS:
                return calcTreeFromRoot(rootNode.left) - calcTreeFromRoot(rootNode.right);

        }

        return 0.0;
    }

//TODO: Добавить проверку на то, что элемент из ArrayStructure не нулевой
    public double calcTreeFromRoot(TreeNode rootNode,
                                   ArrayStructure array,
                                   ArrayStructure arrayRand,
                                   int position){

        switch (rootNode.type){
            //Operands
            case OPERAND_CONST:
                return rootNode.value;
            case OPERAND_ARRAY:
                return array.getElement(rootNode.name, position);
            case OPERAND_RAND:
                return arrayRand.getElement(rootNode.name, position);

            //Operators
            case OPERATOR_DIV:
                return calcTreeFromRoot(rootNode.left, array, arrayRand, position)
                       / calcTreeFromRoot(rootNode.right, array, arrayRand, position);
            case OPERATOR_MULT:
                return calcTreeFromRoot(rootNode.left, array, arrayRand, position)
                        * calcTreeFromRoot(rootNode.right, array, arrayRand, position);
            case OPERATOR_PLUS:
                return calcTreeFromRoot(rootNode.left, array, arrayRand, position)
                        + calcTreeFromRoot(rootNode.right, array, arrayRand, position);
            case OPERATOR_MINUS:
                return calcTreeFromRoot(rootNode.left, array, arrayRand, position)
                        - calcTreeFromRoot(rootNode.right, array, arrayRand, position);
        }

        return 0.0;
    }

    public static void main(String[] args) {
        Infix2Postfix i2p = new Infix2Postfix();
        System.out.println("Type in an expression like (1+2)*(3+4)/(12-5)\n "
                + "with no monadic operators like in-5 or +5 followed by key");
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        String expr = i2p.convertInfToPostf(str);
        System.out.println(expr);

        Postfix2Tree p2t = new Postfix2Tree();
        String[] arrayStr = expr.split("");

        TreeTraversal tt = new TreeTraversal();
        double result = tt.calcTreeFromRoot(p2t.createTree(arrayStr));
        System.out.println("Result: " + result);
    }

}
