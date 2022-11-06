package programmers;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/131128
 * @Date : 2022.10.31 (Mon)
 * @Author : 갈색토마토(@ka)
 * @Description : 숫자짝꿍
 *
 */
public class PairNumber {

    public String solution(String X, String Y) {
        int[] arrX = new int[10];
        int[] arrY = new int[10];

        for (int i = 0 ; i < X.length(); i += 1) {
            arrX[X.charAt(i) - 48] += 1;
        }

        for (int i = 0 ; i < Y.length(); i += 1) {
            arrY[Y.charAt(i) - 48] += 1;
        }

        StringBuilder answer = new StringBuilder();
        for(int i = arrX.length - 1 ; i >= 0 ; i -= 1) {
            for(int j = 0 ; j < Math.min(arrX[i], arrY[i]) ; j += 1) {
                answer.append(i);
            }
        }

        if("".equals(answer.toString())) {
            return "-1";
        } else if (answer.toString().charAt(0) == '0') {
            return "0";
        }

        return answer.toString();
    }

    public static void main(String[] args) {
        PairNumber question = new PairNumber();
        String answer = question.solution("5525", "1255");
        String format = String.format("The Answer is %s.", answer);
        System.out.println(format);
    }
}
