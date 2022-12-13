package codingtest;

public class Test02 {

    public static void main(String[] args) {
        Test02 t = new Test02();
        System.out.println(t.solution(new int[]{4,2,3,3}, 3));

    }
    public boolean solution(int[] A, int K) {
        int n = A.length;
        for (int i = 0; i < n - 1; i++) {
            if (A[i] + 1 < A[i + 1])
                return false;
        }
        if (A[0] != 1 && A[K - 1] != K)
            return false;
        else
            return true;
    }
}
