package test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lw on 14-5-7.
 */
public class Test2 extends Thread {
    public static void main(String[] args) {
        Test2.writeUrlAndCount("/lw/url.txt");
    }

    public static void writeUrlAndCount(String url_path) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //利用map的key-value进行映射
        Map<String, Integer> map = new HashMap<String, Integer>();

        try {
            String str = "";
            fis = new FileInputStream(url_path);// FileInputStream
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                if (str.equals("")){
                    continue;
                }
                if(map.containsKey(str)) {
                    map.put(str, map.get(str) + 1);
                }else {
                    map.put(str, 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定url文件");
        } catch (IOException e) {
            System.out.println("读取url文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(map);

    }

}
