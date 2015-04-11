package tree;

/**
 * @author Ponomarev George
 */
public enum TreeNodeType {
    OPERATOR_PLUS,
    OPERATOR_MINUS,
    OPERATOR_MULT,
    OPERATOR_DIV,
    OPERATOR_EQ,

    OPERAND_CONST,
    OPERAND_RAND,
    OPERAND_ARRAY,
    OPERAND_VAR
}
