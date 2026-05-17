import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.util.*;

class Result {

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */
    public static long solve(List<Integer> arr) {
        int n = arr.size();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = arr.get(i);
        }

        int[] L = new int[n];
        int[] R = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            while (top >= 0 && a[stack[top]] < a[i]) {
                top--;
            }
            L[i] = (top >= 0) ? stack[top] : -1;
            stack[++top] = i;
        }

        top = -1;
        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && a[stack[top]] <= a[i]) {
                top--;
            }
            R[i] = (top >= 0) ? stack[top] : n;
            stack[++top] = i;
        }

        int capacity = Math.max(10000, n * 10);
        int[] qLimit = new int[capacity];
        int[] qSign = new int[capacity];
        int[] qId = new int[capacity];
        long[] packed = new long[capacity];
        
        int qCount = 0;
        int idCount = 0;

        for (int k = 0; k < n; k++) {
            int L_len = k - L[k];
            int R_len = R[k] - k;

            if (L_len <= R_len) {
                for (int i = L[k] + 1; i <= k; i++) {
                    int left_idx = Math.max(k, i + 1);
                    int right_idx = R[k] - 1;
                    if (left_idx <= right_idx) {
                        int max_val = a[k] / a[i];

                        if (qCount + 2 > capacity) {
                            capacity *= 2;
                            qLimit = Arrays.copyOf(qLimit, capacity);
                            qSign = Arrays.copyOf(qSign, capacity);
                            qId = Arrays.copyOf(qId, capacity);
                            packed = Arrays.copyOf(packed, capacity);
                        }

                        qLimit[qCount] = right_idx;
                        qSign[qCount] = 1;
                        qId[qCount] = idCount;
                        packed[qCount] = ((long) max_val << 32) | qCount;
                        qCount++;
                        qLimit[qCount] = left_idx - 1;
                        qSign[qCount] = -1;
                        qId[qCount] = idCount;
                        packed[qCount] = ((long) max_val << 32) | qCount;
                        qCount++;

                        idCount++;
                    }
                }
            } else {
                for (int j = k; j <= R[k] - 1; j++) {
                    int left_idx = L[k] + 1;
                    int right_idx = Math.min(k, j - 1);
                    if (left_idx <= right_idx) {
                        int max_val = a[k] / a[j];

                        if (qCount + 2 > capacity) {
                            capacity *= 2;
                            qLimit = Arrays.copyOf(qLimit, capacity);
                            qSign = Arrays.copyOf(qSign, capacity);
                            qId = Arrays.copyOf(qId, capacity);
                            packed = Arrays.copyOf(packed, capacity);
                        }

                        qLimit[qCount] = right_idx;
                        qSign[qCount] = 1;
                        qId[qCount] = idCount;
                        packed[qCount] = ((long) max_val << 32) | qCount;
                        qCount++;

                        qLimit[qCount] = left_idx - 1;
                        qSign[qCount] = -1;
                        qId[qCount] = idCount;
                        packed[qCount] = ((long) max_val << 32) | qCount;
                        qCount++;

                        idCount++;
                    }
                }
            }
        }

        Arrays.sort(packed, 0, qCount);

        long[] aPacked = new long[n];
        for (int i = 0; i < n; i++) {
            aPacked[i] = ((long) a[i] << 32) | i;
        }
        Arrays.sort(aPacked);

        long[] result = new long[idCount];
        int[] bit = new int[n + 2]; 

        int aPtr = 0;
        
        for (int i = 0; i < qCount; i++) {
            long p = packed[i];
            int max_val = (int) (p >>> 32);
            int idx = (int) (p & 0xFFFFFFFFL); 

            while (aPtr < n) {
                long ap = aPacked[aPtr];
                int val = (int) (ap >>> 32);
                
                if (val <= max_val) {
                    int origIdx = (int) (ap & 0xFFFFFFFFL);
                    for (int x = origIdx + 1; x < bit.length; x += x & -x) {
                        bit[x]++;
                    }
                    aPtr++;
                } else {
                    break;
                }
            }

            int limit = qLimit[idx];
            int sum = 0;
            for (int x = limit + 1; x > 0; x -= x & -x) {
                sum += bit[x];
            }

            result[qId[idx]] += sum * qSign[idx];
        }

        long finalAnswer = 0;
        for (int i = 0; i < idCount; i++) {
            finalAnswer += result[i];
        }

        return finalAnswer;
    }
}
public class arrayPairs {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.solve(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
