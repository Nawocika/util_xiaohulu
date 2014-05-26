package test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by lw on 14-5-21.
 */
public class Test3 {

    static int i = 0;

    private static boolean aBoolean(char c) {
        System.out.print(c);
        return true;
    }

    public static void main(String[] args) {
        updateFileName("/lw");
    }

    /**
     * @param url 操作的目录地址
     * @return 执行结果描述
     */
    public static String updateFileName(String url) {

        File file = new File(url);
        String fielName;
        String temp_FileName;
        File temp;

        //如果文件目录存在
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                //如果是文件
                if (f.isFile()) {
                    temp_FileName = f.getPath();
                    //如果是隐藏文件则跳过
                    if (f.getName().indexOf('.')==0) {
                        continue;
                    }

                    fielName = temp_FileName.substring(0, temp_FileName.lastIndexOf('.')) + ".txt";
                    temp = new File(fielName);
                    try {
                        FileUtils.copyFile(f, temp);
                        //删除原文件
                        //f.delete();
                    } catch (IOException e) {
                        return "Update File " + f.getName() + " error !";
                    }
                }
            }
        }

        return "Update OK !";
    }

}
