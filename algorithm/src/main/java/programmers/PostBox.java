package programmers;

import java.util.Stack;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/131704
 * @Date : 2022.10.29 (Sat)
 * @Author : 갈색토마토(@ka)
 * @Description :
 *
 */
public class PostBox {

    private static final int[] ORDER = new int[]{4,3,1,2,5};

    public int solution(int[] order) {
        int answer = 0;

        Stack<Integer> saveOrder = new Stack<Integer>();
        int orderIdx = 0 ;
        int postBoxIdx = 1;

        while(true) {
            if(!saveOrder.isEmpty() && order[orderIdx] == saveOrder.peek()) {
                answer +=1 ;

                orderIdx += 1;
                saveOrder.pop();
                continue;
            }

            if ( postBoxIdx > order.length ) break;

            if(order[orderIdx] == postBoxIdx) {
                answer += 1;

                postBoxIdx += 1;
                orderIdx += 1;
                continue;
            }

            saveOrder.push(postBoxIdx);
            postBoxIdx += 1 ;
        }

        return answer;
    }

    public static void main(String[] args) {
        PostBox postBox = new PostBox();
        String answer = String.format("The Answer is %s", postBox.solution(ORDER));
        System.out.println(answer);
    }
}
