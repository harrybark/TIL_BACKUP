package baekjoon;

import java.io.*;

public class BaekJoon2577 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String val =
                String.valueOf(
                        Integer.parseInt(br.readLine()) * Integer.parseInt(br.readLine()) * Integer.parseInt(br.readLine())
                );

        for (int i = 0; i < 10; i++) {
            int cnt = 0;
            for (int j = 0; j < val.length(); j++) {
                if (val.charAt(j) - '0' == i) {
                    cnt++;
                }
            }
            sb.append(cnt).append("\n");
        }

        br.close();
        System.out.println(sb);
    }
}
