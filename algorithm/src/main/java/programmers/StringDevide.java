package programmers;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/140108
 * @Date : 2022.12.14 (Wed)
 * @Author : 갈색토마토(@ka)
 * @Description : 문자열 나누기
 */

/*
    s	        result
"banana"	        3
"abracadabra"	    6
"aaabbaccccabba"	3
* */
public class StringDevide {

    public static void main(String[] args) {
        StringDevide solution = new StringDevide();
        int answer = solution.solution("aaabbaccccabba");
        System.out.println(answer);
        answer = solution.solution("abracadabra");
        System.out.println(answer);

    }

    public int solution(String replaceStr) {
        int answer = 0;
        int x = 1, y = 0;

        if ( replaceStr.length() < 2) {
            answer = 1;
        }

        char cur = replaceStr.charAt(0);
        for (int i = 1 ; i < replaceStr.length() ; i += 1 ) {
            char next = replaceStr.charAt(i);
            if(cur - '0' == next - '0') {
                x += 1;
            } else {
                y += 1;
            }

            if ( x == y ) {
                answer += 1;
                if ( i < replaceStr.length() - 1) {
                    cur = replaceStr.charAt(i+1);
                    i += 1;
                    x = 1;
                    y = 0;
                    if (i == replaceStr.length() - 1) {
                        answer += 1;
                        break;
                    }
                }
            } else if ( x != y && i == replaceStr.length() - 1) {
                answer += 1;
                break;
            }
        }

        return answer;
    }
}
