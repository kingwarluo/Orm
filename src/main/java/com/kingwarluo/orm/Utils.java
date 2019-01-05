package com.kingwarluo.orm;

import java.nio.charset.Charset;

public class Utils {

    public static Charset UTF8 = Charset.forName("utf8");

    public static String tableWithSuffix(String table, String suffix){
        if(suffix == null){
            return table;
        }
        return suffix + "_" + table;
    }

}
