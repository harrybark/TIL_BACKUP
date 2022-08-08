package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon10818 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int b = Integer.parseInt(st.nextToken());
        int max = b;
        int min = b;

        while (st.hasMoreTokens()) {
            int curVal = Integer.parseInt(st.nextToken());

            if (min > curVal) {
                min = curVal;
            }

            if (max < curVal) {
                max = curVal;
            }
        }
        br.close();

        System.out.println(min + " " + max);
    }
}
