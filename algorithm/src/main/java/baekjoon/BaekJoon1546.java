package baekjoon;

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BaekJoon1546 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double cnt = Double.valueOf(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        double max = -1;
        double sum = 0 ;
        // 최대값 찾기 및 합계
        for( int i = 0 ; i < cnt; i++ ) {
            double now = Double.valueOf(st.nextToken());

            sum += now;

            if(now > max){
                max = now;
            }
        }
        br.close();
        // 평균
        System.out.println( (sum/max) * 100 / cnt);


    }
}
