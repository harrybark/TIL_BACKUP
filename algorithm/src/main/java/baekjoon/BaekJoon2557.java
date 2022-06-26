package baekjoon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.06.26
 * 백준
 * Problem 2557
 * Hello World!를 출력
 */
public class BaekJoon2557 {

    public static void main(String[] args) throws IOException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write("Hello World!");
        bw.flush();
        bw.close();
    }
}
