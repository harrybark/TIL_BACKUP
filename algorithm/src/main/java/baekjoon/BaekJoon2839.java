package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon2839 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int plasticBag = Integer.parseInt(br.readLine());
        br.close();
        int compareNum = plasticBag % 5;

        if (plasticBag == 4 || plasticBag == 7) {
            System.out.println(-1);
        } else if (compareNum == 0) {
            System.out.println(plasticBag / 5);
        } else if (compareNum == 1 || compareNum == 3) {
            System.out.println((plasticBag / 5) + 1);
        } else if (compareNum == 2 || compareNum == 4) {
            System.out.println((plasticBag / 5) + 2);
        }
    }
}
