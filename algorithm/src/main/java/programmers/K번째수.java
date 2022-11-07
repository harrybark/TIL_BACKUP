package programmers;

import java.util.Arrays;

public class K번째수 {

    public static void main(String[] args) {
        K번째수 problem = new K번째수();
        System.out.println(Arrays.toString(problem.solution(new int[]{1, 5, 2, 6, 3, 7, 4}, new int[][]{{2, 5, 3}, {4, 4, 1}, {1, 7, 3}})));
    }

    public int[] solution(int[] array, int[][] commands) {
        int[] answer = {};
        int commandsLength = commands.length;
        answer = new int[commandsLength];
        int[] temp = {};
        for (int index = 0; index < commandsLength; index++) {
            int k = 0, min = 0;
            ;
            temp = new int[commands[index][1] - commands[index][0] + 1];
            for (int i = commands[index][0] - 1; i < commands[index][1]; i++) {
                temp[k++] = array[i];
            }

            for (int i = 0; i < temp.length - 1; i++) {
                min = i;
                for (int j = i + 1; j < temp.length; j++) {
                    if (temp[j] < temp[min]) {
                        min = j;
                    }
                }
                swap(temp, min, i);

            }
            answer[index] = temp[commands[index][2] - 1];
        }
        return answer;
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
