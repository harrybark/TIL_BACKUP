package baekjoon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/***
 *
 * @author @ka.갈색토마토
 * @Date 05.July.2022
 * @category Algorithm(BaekJoon)
 *
 * 문제 - 백준 10757
 * 두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 A와 B가 주어진다. (0 < A,B < 1010000)
 *
 * 예제 입력
 * 9223372036854775807 9223372036854775808
 * 예제 출력
 * 18446744073709551615
 *
 */
public class BaekJoon10757 {

    /*
    입력 값은 long의 범위를 초과 할 수 있으므로 풀이 방법은 크게 2가지가 될 수 있다.
    */
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        String input1 = st.nextToken();
        String input2 = st.nextToken();
        br.close();
        // 풀이 방법1
        //addByBigInteger(input1, input2);
        // 풀이 방법2
        addPosByPos(input1, input2);

    }

    // 1.BigInteger를 쓰는 경우
    public static void addByBigInteger(String input1, String input2) {
        BigInteger bigNum1 = new BigInteger(input1);
        BigInteger bigNum2 = new BigInteger(input2);

        System.out.println(bigNum1.add(bigNum2));
    }

    // 2.직접 계산
    public static void addPosByPos(String input1, String input2) {

        int max_length = Math.max(input1.length(), input2.length());

        // 각 자리수의 합에 따라 올림이 생길 수 있으므로 문자열 길이에 +1
        int[] arr1 = new int[max_length + 1];
        int[] arr2 = new int[max_length + 1];

        // 각 배열 초기화 후 역순으로 값 저장
        for ( int index = input1.length()-1, idx = 0 ; index >= 0; index--, idx++ ) {

            arr1[idx] = input1.charAt(index) - '0';
        }

        for ( int index = input2.length()-1, idx = 0 ; index >= 0; index--, idx++ ) {
            arr2[idx] = input2.charAt(index) - '0';
        }

        // 0부터 합산 시작
        for ( int index = 0 ; index < max_length; index++ ) {
            int value = arr1[index] + arr2[index];
            arr1[index] = value % 10; // 1의 자리를 해당 자리에 저장
            arr1[index+1] = arr1[index+1] + (value / 10);
        }

        if(arr1[max_length] != 0) {
            System.out.print(arr1[max_length]);
        }

        for(int i = max_length - 1; i >= 0; i--) {
            System.out.print(arr1[i]);
        }
    }
}

