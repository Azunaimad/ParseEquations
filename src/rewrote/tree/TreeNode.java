package rewrote.tree;

import tree.TreeNodeType;

/**
 * @author Ponomarev George
 */
public class TreeNode {

    protected tree.TreeNodeType type;
    protected TreeNode left;
    protected TreeNode right;
    protected double value;
    protected String name;

    public TreeNode(){}

    /**
     * Constructor for operators
     * @param type - type of operator
     * @param left - left tree node
     * @param right - right tree node
     */
    public TreeNode(tree.TreeNodeType type, TreeNode left, TreeNode right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    /**
     * Constructor for nameless operand (numbers)
     * @param value - value (number)
     */
    public TreeNode(double value) {
        this.type = tree.TreeNodeType.OPERAND_CONST;
        this.value = value;
    }

    /**
     * Constructor for named operand: variables, parameters
     * @param type - type of operand
     * @param value - value
     * @param name - name
     */
    public TreeNode(tree.TreeNodeType type, double value, String name) {
        this.type = type;
        this.value = value;
        this.name = name;
    }

    /**
     * Constructor for named operand: arrays, arrays with random numbers
     * @param type - type of operand
     * @param name - name
     */
    public TreeNode(TreeNodeType type, String name){
        this.type = type;
        this.name = name;
    }
}
