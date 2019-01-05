package com.kingwarluo.orm;

import java.util.StringJoiner;

/**
 * 标示主键、普通索引、唯一索引的类
 */
public class Index {

    /**
     * 字段数组
     */
    private String[] columns;
    private String[] fields;
    /**
     * 是否主键
     */
    private boolean primaryKey;
    /**
     * 是否唯一索引
     */
    private boolean unique;

    public Index(boolean primaryKey, boolean unique, String[] columns){
        this.primaryKey = primaryKey;
        this.unique = unique;
        this.columns = columns;
    }

    public Index fields(String[] fields){
        this.fields = fields;
        return this;
    }

    public String s(){
        var joiner = new StringJoiner(",");
        for (var column : columns) {
            joiner.add(String.format("`%s`", column));
        }
        if(primaryKey){
            return String.format("primary key(%s)", joiner.toString());
        }
        if(unique){
            return String.format("unique key(%s)", joiner.toString());
        }
        return String.format("key(%s)", joiner.toString());
    }

    public String[] columns(){
        return columns;
    }

    public String[] fields(){
        return fields;
    }

    public boolean primaryKey(){
        return primaryKey;
    }

    public boolean unique(){
        return unique;
    }
}
