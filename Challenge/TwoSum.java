package Challenge;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two numbers add up to the target.");
    }

    public static void main(String[] args) {
        int[] num1 = {2, 7, 11, 15};
        int num1Target = 9;

        int[] num2 = {3, 2, 4};
        int num2Target = 6;

        int[] num3 = {2, 3, 6, 6};
        int num3Target = 20;

        // Check for num1 target 9
        int[] result1 = twoSum(num1, num1Target);
        System.out.println("num1 indices: " + result1[0] + ", " + result1[1]);

        // Check for num2 target 6
        int[] result2 = twoSum(num2, num2Target);
        System.out.println("num2 indices: " + result2[0] + ", " + result2[1]);

        // Check for num3 target 20 (will throw an exception)
        try {
            int[] result3 = twoSum(num3, num3Target);
            System.out.println("num3 indices: " + result3[0] + ", " + result3[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("num3: " + e.getMessage());
        }
    }
}