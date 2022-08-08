package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon9020 {
    private static final int PRIME_SIZE = 10001;
    private static boolean[] prime = new boolean[PRIME_SIZE];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuffer sb = new StringBuffer();

        int loopCnt = Integer.parseInt(br.readLine());

        // 소수 false / 소수 X true
        makePrime();

        for (int loop = 0; loop < loopCnt; loop += 1) {
            int input = Integer.parseInt(br.readLine());
            int firNum = getPrimeLocation(input / 2), resNum = 0;

            while (true) {
                resNum = input - firNum;
                if (!prime[firNum] && !prime[resNum]) {
                    break;
                } else {
                    firNum -= 1;
                }

            }

            sb.append(firNum).append(" ").append(resNum).append("\n");

        }

        br.close();
        System.out.println(sb);
    }

    private static void makePrime() {

        prime[0] = prime[1] = true;
        for (int i = 2; i <= Math.sqrt(prime.length); i++) {
            if (prime[i]) continue;
            for (int j = i * i; j < prime.length; j += i) {
                prime[j] = true;
            }
        }
    }

    private static int getPrimeLocation(int n) {

        int rVal = 0;
        // n * 0.5가 소수이면 return
        if (!prime[n]) {
            rVal = n;
        }
        // 소수가 아니면 n * 0.5 보다 작은 소수 return
        else {
            for (int i = n - 1; i > 1; i -= 1) {
                if (!prime[i]) {
                    rVal = i;
                    break;
                }
            }
        }

        return rVal;
    }
}
