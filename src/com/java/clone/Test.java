package com.java.clone;

import java.util.Vector;

/**
 * Created by lw on 14-5-5.
 */
public class Test {

    /**
     * 克隆对象拥有和原始对象相同的引用，不是值拷贝。
     *
     * @param args
     */
    public static void main(String[] args) {
        int age = 18;
        String name = "LW";
        Vector vector = new Vector();
        vector.add("JAVA");
        vector.add("C");
        vector.add("C++");

        System.out.println("------------初始化被克隆的对象-----------");
        Student student = new Student(age, name, vector);

        System.out.println("------------浅拷贝-----------");
        Student student1 = student.newInstance();
        System.out.println("浅拷贝后在被克隆的对象中修改值->执行 student.getCourses().add(\"IOS\");");
        student.getCourses().add("IOS");
        System.out.println(student1);

        System.out.println("------------深拷贝-----------");
        Student student2 = (Student) student.clone();
        System.out.println("深拷贝后在被克隆的对象中修改值->执行 student.getCourses().add(\"C#\");");
        student.getCourses().add("C#");
        System.out.println(student2);
    }

}
