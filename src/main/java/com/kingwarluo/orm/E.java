package com.kingwarluo.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class E {

    /**
     * 注解，表示该类为表
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface Table{

    }

    /**
     * 注解，表示该属性为字段
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Column{
        String name();

        String type();

        boolean nullable() default true;

        boolean autoincrement() default false;

        String defaultValue() default "";
    }

    /**
     * 定义表的物理结构属性，如engine=innodb,用于动态创建表
     */
    public static class TableOptions{
        private List<Option> options = new ArrayList<>();

        public TableOptions option(String key, String value){
            this.options.add(new Option(key, value));
            return this;
        }

        public List<Option> options(){
            return options;
        }
    }

    /**
     * 定义表的主键和索引信息,用于动态创建表
     */
    public static class TableIndices{
        private List<Index> indices = new ArrayList<>();
        private Index primary;

        public TableIndices primary(String... columns){
            this.primary = new Index(true, false, columns);
            this.indices.add(primary);
            return this;
        }

        public Index primary(){
            return this.primary;
        }

        public TableIndices unique(String... columns){
            this.indices.add(new Index(false, true, columns));
            return this;
        }

        public TableIndices index(String... columns){
            this.indices.add(new Index(false, false, columns));
            return this;
        }

        public List<Index> indices(){
            return this.indices;
        }
    }
}
