package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon2581 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int startNum = Integer.parseInt(br.readLine());
        int endNum = Integer.parseInt(br.readLine());

        int sum = 0, min = endNum + 1;
        for (int n = startNum; n <= endNum; n++) {
            if (isPrime(n)) {
                sum += n;
                min = Math.min(min, n);
            }
        }

        if (sum > 0) {
            System.out.println(sum);
            System.out.println(min);
        } else {
            System.out.println(-1);
        }
    }

    private static boolean isPrime(int x) {
        if (x == 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(x); i += 1) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
