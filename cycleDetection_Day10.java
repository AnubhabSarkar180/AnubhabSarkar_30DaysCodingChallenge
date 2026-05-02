import java.util.*;

class Node {
    int data;
    Node next;
    Node(int d) {
        data = d;
        next = null;
    }
}

public class cycleDetection_Day10 {

    public static int hasCycle(Node head) {
        if (head == null) return 0;

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;          
            fast = fast.next.next;     

            if (slow == fast) {
                return 1; 
            }
        }
        return 0; 
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int t = sc.nextInt(); 

        while (t-- > 0) {
            int k = sc.nextInt(); 
            int n = sc.nextInt(); 

            Node head = null;
            Node tail = null;
            Node cycleTarget = null;

            for (int i = 0; i < n; i++) {
                int data = sc.nextInt();
                Node newNode = new Node(data);

                if (head == null) {
                    head = newNode;
                    tail = newNode;
                } else {
                    tail.next = newNode;
                    tail = newNode;
                }

                
                if (i == k) {
                    cycleTarget = newNode;
                }
            }

            
            if (k != -1 && tail != null) {
                tail.next = cycleTarget;
            }

            System.out.println(hasCycle(head));
        }
        sc.close();
    }
}