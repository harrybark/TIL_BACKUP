package programmers;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/81301
 * @Date : 2022.11.17 (Thur)
 * @Author : 갈색토마토(@ka)
 * @Description : 숫자 문자열과 영단어
 */
public class 숫자문자열과영단어 {
    private static final String[] ALPHA_WORDS = {
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    public int solution(String s) {

        String temp = s;
        for(int index = 0 ; index < ALPHA_WORDS.length ; index += 1) {
            temp = temp.replace(ALPHA_WORDS[index], String.valueOf(index+1));
        }
        return Integer.parseInt(temp);
    }
}
