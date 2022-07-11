package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon1193 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int preCountSum = 0;
        int curCross = 1;

        br.close();
        while ( true ) {
            if( N <= preCountSum + curCross ) {
                // 분모가 큰 수부터 시작
                if ( curCross % 2 == 1 ) {
                    System.out.println((curCross - (N - preCountSum -1 )) + "/" + (N - preCountSum));
                    break;
                } else {
                    System.out.println((N - preCountSum) + "/" + (curCross - (N - preCountSum -1 )));
                    break;
                }
            }

            else {
                preCountSum += curCross;
                curCross += 1;
            }
        }
    }
}
