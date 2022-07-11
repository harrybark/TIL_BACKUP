package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon1712 {
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int fixedCost = Integer.parseInt(st.nextToken());
        int variableCost = Integer.parseInt(st.nextToken());
        int productPrice = Integer.parseInt(st.nextToken());

        br.close();

        if(variableCost >= productPrice ) {
            System.out.println(-1);
        } else {
            System.out.println(fixedCost/(productPrice-variableCost) + 1);
        }


    }
}
