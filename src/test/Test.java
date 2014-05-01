package test;

/**
 * Created by lw on 14-4-24.
 */
public class Test {
    static String s = "hello";

    Test() {
        System.out.println("create test->"+s);
    }

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println("" + s);
    }
}
