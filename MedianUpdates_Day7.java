import java.util.*;

public class Solution {
    private static PriorityQueue<Integer> leftMaxHeap = new PriorityQueue<>(Collections.reverseOrder());
    private static PriorityQueue<Integer> rightMinHeap = new PriorityQueue<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        
        int n = sc.nextInt();
        
        for (int i = 0; i < n; i++) {
            String op = sc.next();
            int x = sc.nextInt();
            
            if (op.equals("a")) {
                addNumber(x);
                System.out.println(getMedian());
            } else if (op.equals("r")) {
                if (removeNumber(x)) {
                    System.out.println(getMedian());
                } else {
                    System.out.println("Wrong!");
                }
            }
        }
        sc.close();
    }

    private static void addNumber(int num) {
        if (leftMaxHeap.isEmpty() || num <= leftMaxHeap.peek()) {
            leftMaxHeap.add(num);
        } else {
            rightMinHeap.add(num);
        }
        balance();
    }

    private static boolean removeNumber(int num) {
        boolean found = false;
        if (!leftMaxHeap.isEmpty() && num <= leftMaxHeap.peek()) {
            found = leftMaxHeap.remove(num);
        } else {
            found = rightMinHeap.remove(num);
        }
        
        if (found) {
            balance();
            return true;
        }
        return false;
    }

    private static void balance() {
        if (leftMaxHeap.size() > rightMinHeap.size() + 1) {
            rightMinHeap.add(leftMaxHeap.poll());
        } else if (rightMinHeap.size() > leftMaxHeap.size() + 1) {
            leftMaxHeap.add(rightMinHeap.poll());
        }
    }

    private static String getMedian() {
        if (leftMaxHeap.isEmpty() && rightMinHeap.isEmpty()) return "Wrong!";
        
        int totalSize = leftMaxHeap.size() + rightMinHeap.size();

        if (totalSize % 2 != 0) {
            // Odd total: Median is the top of the larger heap
            return String.valueOf(leftMaxHeap.size() > rightMinHeap.size() ? 
                                 leftMaxHeap.peek() : rightMinHeap.peek());
        } else {
            // Even total: Average of both tops
            long sum = (long)leftMaxHeap.peek() + (long)rightMinHeap.peek();
            if (sum % 2 == 0) return String.valueOf(sum / 2);
            else return String.format("%.1f", sum / 2.0);
        }
    }
}