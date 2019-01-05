package com.kingwarluo.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class E {

    /**
     * ע�⣬��ʾ����Ϊ��
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface Table{

    }

    /**
     * ע�⣬��ʾ������Ϊ�ֶ�
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
     * ����������ṹ���ԣ���engine=innodb,���ڶ�̬������
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
     * ������������������Ϣ,���ڶ�̬������
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
