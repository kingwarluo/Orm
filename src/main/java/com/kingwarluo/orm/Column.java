package com.kingwarluo.orm;

import java.lang.reflect.Field;

/**
 * 数据库表属性
 */
public class Column {

    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 是否主键
     */
    private boolean primary;
    /**
     * 是否自增属性
     */
    private boolean autoIncrement;
    /**
     * 是否可空
     */
    private boolean nullable;
    /**
     * 默认值
     */
    private String defaultValue;

    private Field field;

    public Column(String name, String type){
        this(name, type, false, true, "");
    }

    public Column(String name, String type, boolean autoIncrement, boolean nullable, String defaultValue){
        this.name = name;
        this.type = type;
        this.autoIncrement = autoIncrement;
        this.nullable = nullable;
        this.defaultValue = defaultValue;
    }

    public Column field(Field field){
        this.field = field;
        return this;
    }

    public void primary(boolean primary) {
        this.primary = primary;
    }

    public boolean primary() {
        return this.primary;
    }

    public Field field() {
        return this.field;
    }

    public String s(){
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("`%s`", name));
        builder.append(" ");
        builder.append(type);
        if (!nullable) {
            builder.append(" not null");
        }
        if (autoIncrement) {
            builder.append(" auto_increment");
        }

        if (defaultValue != null && !defaultValue.isEmpty()) {
            builder.append(" default ");
            builder.append(defaultValue);
        }
        return builder.toString();
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public boolean autoIncrement() {
        return autoIncrement;
    }

    public boolean nullable() {
        return nullable;
    }

    public String defaultValue() {
        return defaultValue;
    }
}
