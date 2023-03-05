/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tree;

/**
 *
 * @author dangh
 */
public class TreeNode {

    public TreeNode left;
    public TreeNode right;
    public Object data;

    public TreeNode() {
    }

    public TreeNode(Object data) {
        this.data = data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
