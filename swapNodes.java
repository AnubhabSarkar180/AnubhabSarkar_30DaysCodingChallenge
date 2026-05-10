    import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

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

class Result {

    // 1. Build the tree using the indexes provided
    private static Node buildTree(List<List<Integer>> indexes) {
        int n = indexes.size();
        Node[] nodes = new Node[n + 1];
        
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }

        for (int i = 1; i <= n; i++) {
            int leftVal = indexes.get(i - 1).get(0);
            int rightVal = indexes.get(i - 1).get(1);

            if (leftVal != -1) nodes[i].left = nodes[leftVal];
            if (rightVal != -1) nodes[i].right = nodes[rightVal];
        }
        return nodes[1]; 
    }

    private static void performSwap(Node root, int k, int depth) {
        if (root == null) return;

        if (depth % k == 0) {
            Node temp = root.left;
            root.left = root.right;
            root.right = temp;
        }

        performSwap(root.left, k, depth + 1);
        performSwap(root.right, k, depth + 1);
    }

    private static void getInOrder(Node root, List<Integer> traversal) {
        if (root == null) return;
        getInOrder(root.left, traversal);
        traversal.add(root.data);
        getInOrder(root.right, traversal);
    }

    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {
        List<List<Integer>> result = new ArrayList<>();
        Node root = buildTree(indexes);

        for (int k : queries) {
            performSwap(root, k, 1);
            List<Integer> currentTraversal = new ArrayList<>();
            getInOrder(root, currentTraversal);
            result.add(currentTraversal);
        }

        return result;
    }
}
public class swapNodes {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> indexes = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                indexes.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        List<List<Integer>> result = Result.swapNodes(indexes, queries);

        result.stream()
            .map(
                r -> r.stream()
                    .map(Object::toString)
                    .collect(joining(" "))
            )
            .map(r -> r + "\n")
            .collect(toList())
            .forEach(e -> {
                try {
                    bufferedWriter.write(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
