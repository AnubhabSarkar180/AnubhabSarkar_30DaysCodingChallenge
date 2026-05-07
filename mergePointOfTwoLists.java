import java.util.*;

class SinglyLinkedListNode {
    int data;
    SinglyLinkedListNode next;

    SinglyLinkedListNode(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Solution {

    static int findMergeNode(SinglyLinkedListNode head1, SinglyLinkedListNode head2) {
        SinglyLinkedListNode p1 = head1;
        SinglyLinkedListNode p2 = head2;

        while (p1 != p2) {
            p1 = (p1 == null) ? head2 : p1.next;
            p2 = (p2 == null) ? head1 : p2.next;
        }
        return p1.data;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int tests = sc.nextInt();

        while (tests-- > 0) {
            int index = sc.nextInt(); 
            int n = sc.nextInt();
            SinglyLinkedListNode head1 = null, tail1 = null;
            SinglyLinkedListNode mergeNode = null;

            for (int i = 0; i < n; i++) {
                int data = sc.nextInt();
                SinglyLinkedListNode newNode = new SinglyLinkedListNode(data);
                if (head1 == null) {
                    head1 = newNode;
                    tail1 = newNode;
                } else {
                    tail1.next = newNode;
                    tail1 = newNode;
                }
                
                if (i == index) mergeNode = newNode;
            }

            int m = sc.nextInt();
            SinglyLinkedListNode head2 = null, tail2 = null;

            for (int i = 0; i < m; i++) {
                int data = sc.nextInt();
                SinglyLinkedListNode newNode = new SinglyLinkedListNode(data);
                if (head2 == null) {
                    head2 = newNode;
                    tail2 = newNode;
                } else {
                    tail2.next = newNode;
                    tail2 = newNode;
                }
            }

            if (tail2 != null) {
                tail2.next = mergeNode;
            }

            System.out.println(findMergeNode(head1, head2));
        }
        sc.close();
    }
}