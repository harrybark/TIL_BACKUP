package baekjoon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.06.26
 * 백준
 * Problem 10718
 * "강한친구 대한육군"을 한줄에 한 번씩 2줄에 걸쳐 출력
 */
public class BaekJoon10718 {
    public static void main(String[] args) throws IOException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write("강한친구 대한육군");
        bw.newLine();
        bw.write("강한친구 대한육군");
        bw.flush();
        bw.close();
    }
}
