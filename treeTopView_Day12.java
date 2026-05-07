import java.util.*;

class Node {
    int data;
    Node left, right;

    Node(int data) {
        this.data = data;
        left = right = null;
    }
}

class QueueNode {
    Node node;
    int hd;

    QueueNode(Node node, int hd) {
        this.node = node;
        this.hd = hd;
    }
}

public class treeTopView_Day12 {

    public static void topView(Node root) {
        if (root == null) return;

        TreeMap<Integer, Integer> map = new TreeMap<>();
        Queue<QueueNode> queue = new LinkedList<>();

        queue.add(new QueueNode(root, 0));

        while (!queue.isEmpty()) {
            QueueNode temp = queue.poll();
            int hd = temp.hd;
            Node node = temp.node;

            if (!map.containsKey(hd)) {
                map.put(hd, node.data);
            }

            if (node.left != null) {
                queue.add(new QueueNode(node.left, hd - 1));
            }
            if (node.right != null) {
                queue.add(new QueueNode(node.right, hd + 1));
            }
        }

        for (int value : map.values()) {
            System.out.print(value + " ");
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

        topView(root);
    }
}