package com.util.commons.lang;

import org.apache.commons.lang.StringUtils;

/**
 * Created by lw on 14-5-18.
 */
public class StringUtils_Demo {

    private static String str = "liweityut@163.com";

    private static void demo_1() {

        System.out.println(StringUtils.abbreviate(str, 1, 8));

        System.out.println();
        System.out.println("产生一个字符串返回，该字符串长度等于size，str位于新串的中心，其他位置补字符串padStr。");
        System.out.println(StringUtils.center(str, 50));
        System.out.println(StringUtils.center(str, 50,'S'));
        System.out.println(StringUtils.center(str, 50, "STR"));

        System.out.println();
        System.out.println("以单个词为单位的反转，从separator开始");
        System.out.println(StringUtils.chomp(str,"wei"));

    }

    public static void main(String[] args) {
        StringUtils_Demo stringUtils_demo =null;
    }


}
