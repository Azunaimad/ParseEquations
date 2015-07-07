package rewrote.tree;

/**
 * @author Ponomarev George
 */
public enum TreeNodeType {
    OPERATOR_PLUS,
    OPERATOR_MINUS,
    OPERATOR_MULT,
    OPERATOR_DIV,
    OPERATOR_EQ,
    OPERATOR_LOG,
    OPERATOR_SQRT,
    OPERATOR_POWER,

    OPERAND_CONST,
    OPERAND_RAND,
    OPERAND_ARRAY,
    OPERAND_VAR
}
