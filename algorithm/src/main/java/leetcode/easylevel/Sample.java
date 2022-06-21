package leetcode.easylevel;

public class Sample {

    public static void main(String[] args) {

        String temp = "() ([)] [] {}" ;
        temp = temp.replaceAll("\\(\\)", "");
        temp = temp.replaceAll("\\{}", "");
        temp = temp.replaceAll("\\[]", "");
        System.out.println("temp : " + temp);
    }
    
}
