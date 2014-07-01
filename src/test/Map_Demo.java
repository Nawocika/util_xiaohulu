package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by lw on 14-6-18.
 */
public class Map_Demo {

    public static void main(String[] args) {
        Map<String, String> person = new HashMap();
        person.put("id", "liw");
        //这么遍历
        Set<String> keySet = person.keySet();
        for (String s : keySet) {
            System.out.println("ID:" + s);
            System.out.println("Value:" + person.get(s));
        }

        Set<Map.Entry<String,String>>  entries=person.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            entry.getKey();
            entry.getValue();
        }
    }
}
