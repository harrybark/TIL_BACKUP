package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon2869 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int move = Integer.parseInt(st.nextToken());
        int slide = Integer.parseInt(st.nextToken());
        int peak = Integer.parseInt(st.nextToken());

        int curLocation = (peak - slide) / (move - slide);

        if ((peak - slide) % (move - slide) != 0) {
            curLocation++;
        }
        br.close();
        System.out.println(curLocation);
    }
}

