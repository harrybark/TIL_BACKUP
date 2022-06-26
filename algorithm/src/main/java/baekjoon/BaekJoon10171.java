package baekjoon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.06.26
 * 백준
 * Problem 10171
 * 고양이를 출력하라.
 */
public class BaekJoon10171 {
    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write("\\    /\\");
        bw.newLine();
        bw.write(" )  ( ')");
        bw.newLine();
        bw.write("(  /  )");
        bw.newLine();
        bw.write(" \\(__)|");

        bw.flush();
        bw.close();

    }
}
