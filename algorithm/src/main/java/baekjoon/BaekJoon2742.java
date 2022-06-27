package baekjoon;
import java.io.*;

public class BaekJoon2742
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int loopNum = Integer.valueOf(br.readLine());
        StringBuilder sb = new StringBuilder();

        for ( int n = loopNum; n > 0 ; n-- ) {
            sb.append(n).append("\n");
        }
        br.close();
        System.out.println(sb.toString());
    }
}