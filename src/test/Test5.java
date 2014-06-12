package test;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by lw on 14-6-10.
 */
public class Test5 {

    private static class MyRunable implements Runnable {
        int index = 0;
        int max = 4 * 2;

        /**
         * ***
         * <p/>
         * 构造数据
         *
         * 4-----list
         */
        HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();

        {
            for (int i = 0; i < 4; i++) {
                map.put(i, new ArrayList<Integer>());
            }
        }

        /*线程运行判断*/
        boolean isRunning = true;

        /* 构造数据列表*/
        public synchronized void run() {

            while (true) {
                int name = Integer.valueOf(Thread.currentThread().getName());
                /*判断线程是否需要继续运行*/
                while (index % 4 != name) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isRunning) {
                        return;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                /******
                 *
                 *
                 *
                 *
                 */
                for (int i = 0, arrIndex = index; i < 4; i++) {
                    try {

                        if (arrIndex-- < 0) {
                            break;
                        }

                        List<Integer> list = map.get(i);
                        if (list.size() < max) {
                            list.add(name + 1);
                        }
                    } catch (Exception e) {
                    }
                }
                System.err.println("A:" + map.get(0));
                System.err.println("B:" + map.get(1));
                System.err.println("C:" + map.get(2));
                System.err.println("D:" + map.get(3));
                System.err.println("-----------------------");

                if (map.get(map.size() - 1).size() == max) {

                    isRunning = false;
                    this.notifyAll();
                    return;

                } else {

                    index++;
                }

                this.notifyAll();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {

        Runnable runable = new MyRunable();

        //启动4个线程
        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(runable);
            t.setName(String.valueOf(i));
            t.start();
        }
    }
}


