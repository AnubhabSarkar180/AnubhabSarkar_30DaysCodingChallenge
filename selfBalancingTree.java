import java.util.*;

class Node {
    int val;
    int ht;
    Node left, right;

    Node(int val) {
        this.val = val;
        this.ht = 0; 
    }
}

public class selfBalancingTree {

    static int height(Node n) {
        return (n == null) ? -1 : n.ht;
    }

    static int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.ht = Math.max(height(y.left), height(y.right)) + 1;
        x.ht = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.ht = Math.max(height(x.left), height(x.right)) + 1;
        y.ht = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    static Node insert(Node node, int val) {
        if (node == null) return new Node(val);

        if (val < node.val)
            node.left = insert(node.left, val);
        else if (val > node.val)
            node.right = insert(node.right, val);
        else 
            return node;

        node.ht = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && val < node.left.val)
            return rightRotate(node);
        if (balance < -1 && val > node.right.val)
            return leftRotate(node);
        if (balance > 1 && val > node.left.val) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && val < node.right.val) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    static void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.val + "(BF=" + getBalance(node) + ") ");
            inOrder(node.right);
        }
    }

    static void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.val + "(BF=" + getBalance(node) + ") ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Node root = null;

        if (sc.hasNextInt()) {
            int n = sc.nextInt(); 
            for (int i = 0; i < n; i++) {
                if (sc.hasNextInt()) root = insert(root, sc.nextInt());
            }
        }
        
        while (sc.hasNextInt()) {
            root = insert(root, sc.nextInt());
        }
        
        inOrder(root);
        System.out.println();
        preOrder(root);
        
        sc.close();
    }
}