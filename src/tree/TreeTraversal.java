package tree;


import namedstruct.ArrayStructure;
import namedstruct.ConstStructure;

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
                                   ConstStructure constants,
                                   int position,
                                   int iteration){

        switch (rootNode.type){


            //Operators
            case OPERATOR_DIV:
                return calcTreeFromRoot(rootNode.left, array, arrayRand, constants, position, iteration)
                       / calcTreeFromRoot(rootNode.right, array, arrayRand, constants, position, iteration);
            case OPERATOR_MULT:
                return calcTreeFromRoot(rootNode.left, array, arrayRand, constants, position, iteration)
                        * calcTreeFromRoot(rootNode.right, array, arrayRand, constants, position, iteration);
            case OPERATOR_PLUS:
                return calcTreeFromRoot(rootNode.left, array, arrayRand, constants, position, iteration)
                        + calcTreeFromRoot(rootNode.right, array, arrayRand, constants, position, iteration);
            case OPERATOR_MINUS:
                return calcTreeFromRoot(rootNode.left, array, arrayRand, constants, position, iteration)
                        - calcTreeFromRoot(rootNode.right, array, arrayRand, constants, position, iteration);

            //Operands
            case OPERAND_ARRAY:
                return array.getElement(rootNode.name, position, iteration);
            case OPERAND_RAND:
                return arrayRand.getElement(rootNode.name, position, iteration);
            case OPERAND_VAR:
                return constants.getConstValue(rootNode.name);
            default:
                System.out.println(rootNode.value);
                return rootNode.value;
        }
    }
}
