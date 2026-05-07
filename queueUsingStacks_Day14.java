import java.util.*;

class MyQueue {
    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    public void enqueue(int x) {
        stack1.push(x);
    }

    private void prepareStack2() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
    }

    public void dequeue() {
        prepareStack2();
        if (!stack2.isEmpty()) {
            stack2.pop();
        }
    }

    public int peek() {
        prepareStack2();
        return stack2.peek();
    }
}

public class queueUsingStacks_Day14 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyQueue queue = new MyQueue();
        
        if (!sc.hasNextInt()) return;
        int q = sc.nextInt(); 

        while (q-- > 0) {
            int type = sc.nextInt();
            if (type == 1) {
                int x = sc.nextInt();
                queue.enqueue(x);
            } else if (type == 2) {
                queue.dequeue();
            } else if (type == 3) {
                System.out.println(queue.peek());
            }
        }
        sc.close();
    }
}