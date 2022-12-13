package codingtest;

import java.util.Arrays;
public class Test01 {

    public static void main(String[] args) {
        Test01 t = new Test01();
        System.out.println(t.solution(1000));
    }

    public int solution(int N){
        int answer = 0;
        // 정수 각 자리의 합
        int sum = calcSum(N);

        int compareSum = 0;
        int start = N + 1;
        // N 보다 큰 정수
        while(true) {
            compareSum = calcSum(start);
            if(compareSum == sum) break;

            start += 1;
        }
        answer = start;
        return answer;
    }

    public int calcSum(int num) {
        int sum = 0;
        while(num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
