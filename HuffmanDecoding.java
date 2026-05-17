import java.io.*;
import java.util.*;

abstract class Node implements Comparable<Node> {
    public int frequency;
    public char data;
    public Node left, right;

    public Node(int freq) { frequency = freq; }

    public int compareTo(Node tree) { return frequency - tree.frequency; }
}

class HuffmanLeaf extends Node {
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        data = val;
    }
}

class HuffmanNode extends Node {
    public HuffmanNode(Node l, Node r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

public class HuffmanDecoding {

    public static void decode(String s, Node root) {
        Node current = root;
        
        for (int i = 0; i < s.length(); i++) {
            current = (s.charAt(i) == '0') ? current.left : current.right;
            
            if (current.left == null && current.right == null) {
                System.out.print(current.data);
                current = root; 
            }
        }
    }
    
    private static void buildCodeMap(Node node, String currentCode, Map<Character, String> codeMap) {
        if (node instanceof HuffmanLeaf) {
            codeMap.put(((HuffmanLeaf) node).data, currentCode);
            return;
        }
        if (node != null) {
            buildCodeMap(node.left, currentCode + "0", codeMap);
            buildCodeMap(node.right, currentCode + "1", codeMap);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        sc.close();

        if (userInput == null || userInput.isEmpty()) {
            // System.out.println("Input cannot be empty.");
            return;
        }

        int[] charFreqs = new int[256];
        for (char c : userInput.toCharArray()) {
            charFreqs[c]++;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < 256; i++) {
            if (charFreqs[i] > 0) {
                pq.add(new HuffmanLeaf(charFreqs[i], (char) i));
            }
        }

        
        if (pq.size() == 1) {
            // System.out.println("Standard Huffman Coding requires at least two distinct characters.");
            return;
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new HuffmanNode(left, right));
        }
        Node root = pq.poll();

        Map<Character, String> codeMap = new HashMap<>();
        buildCodeMap(root, "", codeMap);

        StringBuilder encodedString = new StringBuilder();
        for (char c : userInput.toCharArray()) {
            encodedString.append(codeMap.get(c));
        }
        
        decode(encodedString.toString(), root);
        System.out.println(); 
    }
}