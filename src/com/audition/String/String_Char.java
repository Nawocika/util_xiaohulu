package com.audition.String;


/**
 * Created by lw on 14-5-5.
 */
public class String_Char {
    private static String string = "liwei我爱我的China!";

    /**
     * 截取字符串，保证不截取半个汉字
     *
     * @param string
     * @param index
     * @return
     */
    private String subString2GBK(String string, int index) {
        int temp = string.length();
        boolean b = false;
        //byte[] bytes = string.getBytes("UTF-8");
        byte[] bytes = string.getBytes();
        index = index > temp ? temp : index;
        temp = 0;
        for (int i = 0; i < index; i++) {
            if (bytes[i] < 0 && !b) {
                b = true;
            } else {
                temp++;
                b = false;
            }
        }
        return string.substring(0, temp);
    }

    public static void main(String[] args) {
        String_Char aChar = new String_Char();
        String str = aChar.subString2GBK(string, 7);
        System.out.println(str);
    }
}