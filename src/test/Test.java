package test;

/**
 * Created by lw on 14-4-24.
 */
public class Test {
    static String s = "hello";
    static char c = 87;

    Test() {
        System.out.println("create test->" + s);
    }

    public static void main(String[] args) {
        int temp = new Test().getInAnInt();
        System.out.println(temp);
    }

    private int getInAnInt() {

        int i = 0;

        try {
            System.out.println("try...");
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally...");
            i = 20;
        }
        return i;
    }
}

interface A {
    void AA();
}

abstract class BB implements A {

    abstract void AAA();
}

class CC extends BB {

    @Override
    void AAA() {

    }

    @Override
    public void AA() {

    }
}