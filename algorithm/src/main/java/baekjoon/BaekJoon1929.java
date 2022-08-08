package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon1929 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int sNum = Integer.parseInt(st.nextToken());
        int eNum = Integer.parseInt(st.nextToken());

        for (int n = sNum; n <= eNum; n += 1) {
            if (isPrime(n)) {
                System.out.println(n);
            }
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
