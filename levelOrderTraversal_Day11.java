import java.util.*;
import java.io.*;

class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

public class Solution {

    
    public static void levelOrder(Node root) {
        if (root == null) return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.data + " ");

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }

    
    public static Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        } else {
            if (data <= root.data) {
                root.left = insert(root.left, data);
            } else {
                root.right = insert(root.right, data);
            }
            return root;
        }
    }

  
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        
        if (!scan.hasNextInt()) return;
        int t = scan.nextInt();
        
        Node root = null;
        
      
        while (t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        
        scan.close();
        
       
        levelOrder(root);
    }
}