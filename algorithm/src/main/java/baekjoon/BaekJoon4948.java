package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon4948 {

    private static final int SIZE = 123456;
    private static boolean[] prime = new boolean[SIZE * 2 + 1];

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuffer sb = new StringBuffer();
        int N;

        makePrime();

        while( ( N = Integer.parseInt(br.readLine())) != 0 ) {
            int count = 0 ;
            for ( int i = N + 1 ; i <= (2*N); i+=1 ) {
                if ( !prime[i]) count+=1;
            }
            sb.append(count).append("\n");
        }
        br.close();
        System.out.println(sb);
    }

    private static void makePrime() {
        prime[0] = prime[1] = true;
        for(int i = 2; i <= Math.sqrt(prime.length); i++) {
            if(prime[i]) continue;
            for(int j = i * i; j < prime.length; j += i) {
                prime[j] = true;
            }
        }
    }
}
