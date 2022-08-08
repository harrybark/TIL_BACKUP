package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon2292 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력된 숫자
        int input = Integer.parseInt(br.readLine());

        if (input == 1) {
            System.out.println(input);
        } else {
            int range = 2;
            int count = 1;
            while (range <= input) {
                range = range + (6 * (count));
                count += 1;
            }

            System.out.println(count);
        }
    }
}
