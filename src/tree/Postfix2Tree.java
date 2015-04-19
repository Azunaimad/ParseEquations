package tree;


import java.util.Stack;


/**
 * Convert expression in postfix notation into tree structure
 *
 * @author Ponomarev George
 */
public class Postfix2Tree extends TreeNode {

    /**
     * Create tree from postfix expression
     * @param postfixExpr - String array, contains expression in postfix notation.
     *                    For example, 123+* should be {"1","2","3","+","*"}
     * @return root tree node
     */
    public TreeNode createTree(String[] postfixExpr){
        Stack<TreeNode> stack = new Stack<TreeNode>();
        for(String s : postfixExpr){
            if(isOperator(s)) {
                TreeNode right = stack.pop();
                TreeNode left = stack.pop();

                if(s.equals("+"))
                    stack.push(new TreeNode(TreeNodeType.OPERATOR_PLUS,left,right));
                if(s.equals("-"))
                    stack.push(new TreeNode(TreeNodeType.OPERATOR_MINUS,left,right));
                if(s.equals("*"))
                    stack.push(new TreeNode(TreeNodeType.OPERATOR_MULT,left,right));
                if(s.equals("/"))
                    stack.push(new TreeNode(TreeNodeType.OPERATOR_DIV,left,right));
            }
            else {
                if (isNumeric(s))
                    stack.push(new TreeNode(Double.parseDouble(s)));
                else if(s.contains("rand"))
                    stack.push(new TreeNode(TreeNodeType.OPERAND_RAND,s));
                else
                    stack.push(new TreeNode(TreeNodeType.OPERAND_ARRAY,s));

            }
        }
        return stack.pop();
    }

    /**
     * Check: is string an operator?
     * @param s - string, which we want to check
     * @return true if s is operator, false if not
     */
    public boolean isOperator(String s){
        return s.equals("+") ||
                s.equals("-") ||
                s.equals("*") ||
                s.equals("/");
    }

    /**
     * Check: is string a number?
     * @param s - string, which we want to check
     * @return true if s is number, false if not
     */
    public static boolean isNumeric(String s){
        try
        {
            double d = Double.parseDouble(s);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
