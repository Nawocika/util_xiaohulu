package test;

/**
 * Created by lw on 14-5-26.
 */

import java.util.Arrays;
import java.util.Scanner;
public class Test4
{
    public static void main(String args[])
    {
        Scanner in=new Scanner(System.in);
        System.out.println("How many number do you want to draw?");
        int k=in.nextInt();
        System.out.println("What is the higest number you can draw?");
        int n=in.nextInt();
        int[] numbers=new int[n];
        for(int i=0;i<numbers.length;++i)
            numbers[i]=i+1;
        int[] result=new int[k];
        for(int i=0;i<result.length;++i)
        {
            int r=(int)(Math.random()*n);
            result[i]=numbers[r];
            numbers[r]=numbers[n-1];
            n--;
        }
        Arrays.sort(result);
        System.out.println("Hello");
        for(int a:result)
            System.out.println(a);
    }


}
