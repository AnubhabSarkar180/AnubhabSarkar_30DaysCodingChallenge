import java.io.*;
import java.util.stream.*;

class Result {

    /*
     * Complete the 'downToZero' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER n as parameter.
     */
    
    private static final int MAX = 1000001;
    private static final int[] dp = new int[MAX];
    
    static{
        for(int i = 1; i < MAX; i++)
        {
            if (dp[i] == 0 || dp[i] > dp[i - 1] + 1) {
                dp[i] = dp[i - 1] + 1;
            }
            
            for (int j = 2; j <= i && i * j < MAX; j++) {
                if (dp[i * j] == 0 || dp[i * j] > dp[i] + 1) {
                    dp[i * j] = dp[i] + 1;
                }
            }
        }
    }

    public static int downToZero(int n) {
    // Write your code here
        return dp[n];
    }

}

public class downToZero {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                int result = Result.downToZero(n);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
