package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon2775 {

    private static int[][] apt = new int[15][15];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        // APT 배열 생성
        makeAPT();

        int cnt = Integer.parseInt(br.readLine());

        for ( int cur = 0 ; cur < cnt; cur += 1 ) {
            int floor = Integer.parseInt(br.readLine());
            int layer = Integer.parseInt(br.readLine());
            sb.append(apt[floor][layer]).append("\n");
        }
        br.close();
        System.out.println(sb.toString());
    }

    public static void makeAPT() {

        for (int i = 0 ; i < apt.length; i+=1 ) {
            apt[i][1] = 1; // i층 1호는 1
            apt[0][i] = i; // 0층 1호 ~ 14호
        }

        // 1층부터 14층까지
        // 2호부터 14호까지
        for ( int floor = 1 ; floor < 15; floor+=1 ) {
            for ( int layer = 2 ; layer < 15; layer += 1) {
                apt[floor][layer]  = apt[floor][layer-1] + apt[floor-1][layer];
            }
        }
    }
}
