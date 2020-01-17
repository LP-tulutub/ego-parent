package com.lp.test;

import com.ego.commons.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {
    public static void main(String[] args) {
        LpPojo lp = new LpPojo(1, "lp", 23);
        List<LpPojo> list = new ArrayList<>();
        list.add(lp);
        list.add(lp);
        System.out.println(list);
        String str = JsonUtils.objectToJson(list);
        System.out.println(str);
        List<LpPojo> list2 = JsonUtils.jsonToList(str, LpPojo.class);
        System.out.println(list2);


    }
}
