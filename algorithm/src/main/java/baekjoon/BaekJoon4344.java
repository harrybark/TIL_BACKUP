package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon4344 {

    public static void main( String[] args ) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st;
        int loopCount = Integer.parseInt(br.readLine());
        for ( int i = 0 ; i < loopCount; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int arrSize = Integer.parseInt(st.nextToken());

            int[] arr = new int[arrSize];
            double sum = 0; // 합계
            int cur = 0; // 현재 인덱스
            double cnt = 0; // 평균 이상 수

            while(st.hasMoreTokens()) {
                int val = Integer.parseInt(st.nextToken());
                arr[cur++] = val;
                sum += val;
            }
            double avg = sum/arrSize;

            for(int val : arr) {
                if( val > avg ) {
                    cnt++;
                }
            }

            System.out.printf("%.3f%%\n", cnt/arrSize*100);
        }
        br.close();

    }

}
