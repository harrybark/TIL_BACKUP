package programmers;

import java.util.Arrays;

public class 키패드누르기 {

    private static final int[][] INPUT_CASE = {
            {1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5},
            {7, 0, 8, 2, 8, 3, 1, 5, 7, 6, 2},
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 0}
    };

    private static final String[] HAND = {
            "RIGHT", "LEFT", "RIGHT"
    } ;

    private static final String[] ANSWER = {
            new String("LRLLLRLLRRL"),
            new String("LRLLRRLLLRR"),
            new String("LLRLLRLLRL")
    };

    public String solution(int[] numbers, String hand) {
        StringBuffer answer = new StringBuffer();
        int lPos = 10;
        int rPos = 12;

        for ( int num : numbers ) {
            if( num == 1 || num == 4 || num == 7 ) {
                answer.append("L");
                lPos = num;
            }
            else if ( num == 3 || num == 6 || num == 9 ) {
                answer.append("R");
                rPos = num;
            } else {
                if( num == 0 ) num = 11;
                int lDigest = Math.abs(num - lPos) / 3 + Math.abs(num - lPos) % 3;
                int rDigest = Math.abs(num - rPos) / 3 + Math.abs(num - rPos) % 3;

                if ( lDigest < rDigest ) {
                    answer.append("L");
                    lPos = num ;
                } else if ( lDigest > rDigest ) {
                    answer.append("R");
                    rPos = num ;
                } else {
                    switch (hand) {
                        case "RIGHT" :
                            answer.append("R");
                            rPos = num;
                            break;
                        case "LEFT" :
                            answer.append("L");
                            lPos = num;
                            break;
                    }
                }
            }
        }

        return answer.toString();
    }

    public static void main(String[] args) {
        키패드누르기 problem = new 키패드누르기();
        for(int i = 0 ; i < INPUT_CASE.length ; i += 1 ) {
            System.out.printf("The Answer is %s.\n", ANSWER[i].equals(problem.solution(INPUT_CASE[i], HAND[i])));
        }
    }

}
