package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon2562 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int max = 0;
        int maxIdx = 0;
        int curIdx = 0;
        int comparedNum;
        String str;
        while( (str = br.readLine()) != null ) {
            comparedNum = Integer.valueOf(str);
            curIdx++;
            if(comparedNum > max) {
                max = comparedNum;
                maxIdx = curIdx;
            }
        }

        br.close();
        System.out.println(max);
        System.out.println(maxIdx);
    }
}
