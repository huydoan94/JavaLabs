/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tree;

/**
 *
 * @author dangh
 */
public class Tree {

    private TreeNode root;

    public int searchCount = 0;

    public Tree() {
    }

    public void add(Object o, TreeNode temp) {
        Comparable c = (Comparable) o;
        if (root == null) {
            root = new TreeNode(o);
        } else if (c.compareTo(temp.getData()) < 0) {
            if (temp.left == null) {
                temp.left = new TreeNode(o);
            } else {
                add(o, temp.left);
            }
        } else if (c.compareTo(temp.getData()) > 0) {
            if (temp.right == null) {
                temp.right = new TreeNode(o);
            } else {
                add(o, temp.right);
            }
        } else if (c.compareTo(temp.getData()) >= 0) {
            if (temp.right == null) {
                temp.right = new TreeNode(o);
            } else {
                add(o, temp.right);
            }
        }
    }

    public void add(Object o) {
        add(o, root);
    }

    public void printTree(TreeNode temp) {
        if (temp.left != null) {
            printTree(temp.left);
        }
        System.out.println(temp.getData());
        if (temp.right != null) {
            printTree(temp.right);
        }
    }

    public void printTree() {
        printTree(root);
    }

    public Object searchTree(TreeNode temp, Object o) {

        searchCount++;

        Comparable c = (Comparable) o;
        if (c.compareTo(temp.getData()) == 0) {
            return temp.getData();
        } else if (c.compareTo(temp.getData()) < 0 && temp.left != null) {
            return searchTree(temp.left, o);
        } else if (c.compareTo(temp.getData()) > 0 && temp.right != null) {
            return searchTree(temp.right, o);
        } else {
            return null;
        }
    }

    public Object searchTree(Object o) {
        return searchTree(root, o);
    }
}
