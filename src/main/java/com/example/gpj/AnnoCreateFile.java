package com.example.gpj;

import com.yaoxiaowen.comp.proce.db.SQLString;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2019-12-01 18:54
 */
public class AnnoCreateFile {
    @SQLString(name = "yw")
    String filed;
    @SQLString(name = "yaow", value = 1)
    String name;

    /**
     * @author www.yaoxiaowen.com
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("hello world");
    }
}
