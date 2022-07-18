package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon1978 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        String str;
        int count = 0;
        while ( st.hasMoreElements() ) {
            str = st.nextToken();
            if(isPrime(Integer.parseInt(str))) {
                count += 1;
            }
        }
        System.out.println(count);
    }

    private static boolean isPrime(int x) {
        if ( x == 1 ) {
            return false;
        }
        for ( int i = 2 ; i <= Math.sqrt(x); i+=1 ) {
            if( x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
