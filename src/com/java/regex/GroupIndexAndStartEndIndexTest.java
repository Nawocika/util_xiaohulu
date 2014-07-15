package com.java.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lw on 14-7-14.
 * <p/>
 * group(),start(),end()所带的参数i就是正则表达式中的子表达式索引（第几个子表达式），
 * 当将“组”的概念与“子表达式”对应起来之后，理解matcher的group,start,end就完全没有障碍了。
 */
public class GroupIndexAndStartEndIndexTest {

    public static void main(String[] args) {

        //matcher();
        appendReplacement();
    }

    private static void matcher() {
        String str = "Hello,World! in Java.Hello,World! in Java.";
        String str_1 = "in Java.";
        Pattern pattern = Pattern.compile("W(or)(ld!)");
        Matcher matcher = pattern.matcher(str);
        // matcher.reset(str_1);
        while (matcher.find()) {
            System.out.println("Group 0:" + matcher.group(0));//得到第0组——整个匹配
            System.out.println("Group 1:" + matcher.group(1));//得到第一组匹配——与(or)匹配的
            System.out.println("Group 2:" + matcher.group(2));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
            System.out.println("Start 0:" + matcher.start(0) + " End 0:" + matcher.end(0));//总匹配的索引
            System.out.println("Start 1:" + matcher.start(1) + " End 1:" + matcher.end(1));//第一组匹配的索引
            System.out.println("Start 2:" + matcher.start(2) + " End 2:" + matcher.end(2));//第二组匹配的索引
            System.out.println(str.substring(matcher.start(0), matcher.end(1)));//从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
        }
    }

    private static void appendReplacement() {
        Pattern p = Pattern.compile("cat");
        Matcher m = p.matcher("one cat two cats in the yard");
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "dog");
        }
        m.appendTail(sb);
        System.out.println(sb.toString());

    }
}
