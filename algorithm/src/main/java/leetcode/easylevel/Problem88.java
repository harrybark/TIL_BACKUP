package leetcode.easylevel;

import java.util.Arrays;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.05.09
 * Leetcode
 * Merge Sorted Array
 */
public class Problem88 {

    private static int[] nums1 = {1, 2, 3, 0, 0, 0};
    private static int[] nums2 = {2, 5, 6};
    private static int m = 3;
    private static int n = 3;

    public static void main(String[] args) {
        Problem88 execute = new Problem88();
        execute.merge(nums1, m, nums2, n);
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int k = m - 1;
        int j = n - 1;
        int rtnSize = m + n - 1;

        while (true) {
            if (j < 0) {
                break;
            }

            if (k < 0 || nums1[k] < nums2[j]) {
                nums1[rtnSize--] = nums2[j--];
                continue;
            }

            nums1[rtnSize--] = nums1[k--];
        }

        System.out.println(String.format("The Answer is %s.", Arrays.toString(nums1)));
    }
}
