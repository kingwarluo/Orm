package com.kingwarluo.orm;

import java.util.*;

public class Q {
    static enum Type{
        Select, Insert, Update, Delete, Create, Drop, Truncate
    }

    /**
     * 操作类型，增删改查、创建删除重置表
     */
    private Type type;
    /**
     * 查询返回字段集合
     */
    private List<String> fields = new ArrayList<String>();
    private String table;
    private String suffix;
    private Filterable filter;
    private List<String> groupBys = new ArrayList<>();
    private Filterable having;
    private List<OrderBy> orderBys = new ArrayList<>();
    private int offset;
    private int limit;
    private boolean offset_;
    private boolean limit_;

    /**
     * 插入，修改时使用。Setter由name，value组成
     */
    private List<Setter> setters = new ArrayList<>();
    /**
     * 创建表时使用。字段类型的集合
     */
    private List<Column> columns = new ArrayList<>();
    /**
     * 创建表时使用，索引集合
     */
    private List<Index> indices = new ArrayList<>();
    /**
     * 创建表时使用，标明表的物理属性
     */
    private List<Option> options = new ArrayList<>();

    private static final Placeholder __ = new Placeholder();

    public static Q select(){
        Q q = new Q();
        q.type = Type.Select;
        return q;
    }

    public static Q insert(){
        Q q = new Q();
        q.type = Type.Insert;
        return q;
    }

    public static Q update(){
        Q q = new Q();
        q.type = Type.Update;
        return q;
    }

    public static Q delete(){
        Q q = new Q();
        q.type = Type.Delete;
        return q;
    }

    public static Q create(){
        Q q = new Q();
        q.type = Type.Create;
        return q;
    }

    public static Q drop(){
        Q q = new Q();
        q.type = Type.Drop;
        return q;
    }

    public static Q truncate(){
        Q q = new Q();
        q.type = Type.Truncate;
        return q;
    }

    public String sql(){
        switch(type){
            case Create:
                return createSQL();
            case Drop:
                return dropSQL();
            case Truncate:
                return truncateSQL();
            case Insert:
                return insertSQL();
            case Delete:
                return deleteSQL();
            case Update:
                return updateSQL();
            case Select:
                return selectSQL();
            default:
                return null;
        }
    }

    public Q field(String... fs){
        for(String f : fs){
            this.fields.add(f);
        }
        return this;
    }

    public Q table(String table){
        this.table = table;
        return this;
    }

    public Q table(String table, String suffix){
        this.table = table;
        this.suffix = suffix;
        return this;
    }

    public Q suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String table(){
        return this.table;
    }

    public String suffix(){
        return this.suffix;
    }

    public Q groupBy(String... gbs){
        for (String gb : gbs) {
            this.groupBys.add(gb);
        }
        return this;
    }

    public Q where(Filterable filter){
        this.filter = filter;
        return this;
    }

    public Q having(Filterable filter){
        this.having = filter;
        return this;
    }

    public Q orderBy(String name){
        this.orderBys.add(new OrderBy(name));
        return this;
    }

    public Q orderBy(String name, String direction){
        this.orderBys.add(new OrderBy(name, direction));
        return this;
    }

    public Q offset(int offset){
        this.offset = offset;
        return this;
    }

    public Q limit(int limit){
        this.limit = limit;
        return this;
    }

    public Q offset_(){
        this.offset_ = true;
        return this;
    }

    public Q limit_(){
        this.limit_ = true;
        return this;
    }

    public Q with(String name, Object value){
        this.setters.add(new Setter(name, primitive(value)));
        return this;
    }

    public Q with(String name){
        this.setters.add(new Setter(name, __));
        return this;
    }

    public static And and(Filterable... filters){
        return new And(filters);
    }

    public static Or or(Filterable... filters){
        return new Or(filters);
    }

    public static Comparator eq(String name, Object o) {
        return new Comparator(name, "=", primitive(o));
    }

    public static Comparator eq_(String name) {
        return new Comparator(name, "=", __);
    }

    public static Comparator ne(String name, Object o) {
        return new Comparator(name, "!=", primitive(o));
    }

    public static Comparator ne_(String name) {
        return new Comparator(name, "!=", __);
    }

    public static Comparator lt(String name, Object o) {
        return new Comparator(name, "<", primitive(o));
    }

    public static Comparator lt_(String name) {
        return new Comparator(name, "<", __);
    }

    public static Comparator le(String name, Object o) {
        return new Comparator(name, "<=", primitive(o));
    }

    public static Comparator le_(String name) {
        return new Comparator(name, "<=", __);
    }

    public static Comparator gt(String name, Object o) {
        return new Comparator(name, ">", primitive(o));
    }

    public static Comparator gt_(String name) {
        return new Comparator(name, ">", __);
    }

    public static Comparator ge(String name, Object o) {
        return new Comparator(name, ">=", primitive(o));
    }

    public static Comparator ge_(String name) {
        return new Comparator(name, ">=", __);
    }

    public static Between between(String name, Object min, Object max) {
        return new Between(name, primitive(min), primitive(max));
    }

    public static Between between_(String name) {
        return new Between(name, __, __);
    }

    public static In in(String name, Object... os) {
        return new In(name, set(os));
    }

    public static In in_(String name, int includes) {
        var sets = set();
        for (int i = 0; i < includes; i++) {
            sets.values.add(__);
        }
        return new In(name, sets);
    }

    public static In in(String name, Collection<Object> os) {
        return new In(name, set(os));
    }

    public static In in(String name, SubQuery subQ) {
        return new In(name, subQ);
    }

    public static NotIn notin(String name, Object... os) {
        return new NotIn(name, set(os));
    }

    public static NotIn notin_(String name, int includes) {
        var sets = set();
        for (int i = 0; i < includes; i++) {
            sets.values.add(__);
        }
        return new NotIn(name, sets);
    }

    public static NotIn notin(String name, Collection<Object> os) {
        return new NotIn(name, set(os));
    }

    public static NotIn notin(String name, SubQuery subQ) {
        return new NotIn(name, subQ);
    }

    public static Not not(Filterable filter) {
        return new Not(filter);
    }

    public static Placeholder placeholder() {
        return new Placeholder();
    }

    public String insertSQL(){
        var builder = new StringBuilder();
        builder.append("insert into ");
        builder.append(String.format("%s ", tableWithSuffix()));
        builder.append("(");
        var joiner = new StringJoiner(",");
        for (var setter : setters) {
            joiner.add(setter.name);
        }
        builder.append(joiner.toString());
        builder.append(") values(");
        joiner = new StringJoiner(", ");
        for (var setter : setters) {
            joiner.add(setter.value.value());
        }
        builder.append(")");
        return builder.toString();
    }

    public String deleteSQL() {
        var builder = new StringBuilder();
        if (this.filter == null) {
            builder.append("delete * from ");
            builder.append(String.format("`%s`", tableWithSuffix()));
        } else {
            builder.append("delete from ");
            builder.append(String.format("`%s`", tableWithSuffix()));
            builder.append(" where ");
            builder.append(this.filter.s(0));
        }

        return builder.toString();
    }

    public String updateSQL() {
        var builder = new StringBuilder();
        builder.append("update ");
        builder.append(String.format("`%s`", tableWithSuffix()));
        builder.append(" set ");
        StringJoiner joiner = new StringJoiner(", ");
        for (var setter : setters) {
            joiner.add(setter.s());
        }
        builder.append(joiner.toString());
        if (this.filter != null) {
            builder.append(" where ");
            builder.append(this.filter.s(0));
        }
        return builder.toString();
    }

    public String selectSQL(){
        var builder = new StringBuilder();
        builder.append("select ");
        for(var i = 0; i < fields.size(); i++){
            builder.append(fields.get(i));
            if(i < fields.size() - 1){
                builder.append(",");
            }
        }
        if(this.table != null){
            builder.append(" from ");
            builder.append(String.format("`%s`", tableWithSuffix()));
        }
        if(this.filter != null){
            builder.append(" where ");
            builder.append(this.filter.s(0));
        }
        if (!this.groupBys.isEmpty()) {
            builder.append(" group by ");
            StringJoiner joiner = new StringJoiner(", ");
            for (String by : groupBys) {
                joiner.add(by);
            }
            builder.append(joiner.toString());
        }
        if (this.having != null) {
            builder.append(" having ");
            builder.append(this.having.s(0));
        }
        if (!this.orderBys.isEmpty()) {
            builder.append(" order by ");
            StringJoiner joiner = new StringJoiner(", ");
            for (var by : orderBys) {
                joiner.add(by.s());
            }
            builder.append(joiner.toString());
        }
        if (this.offset_) {
            builder.append(" offset ?");
        } else if (this.offset > 0) {
            builder.append(" offset ");
            builder.append(this.offset);
        }
        if (this.limit_) {
            builder.append(" limit ?");
        } else if (this.limit > 0) {
            builder.append(" limit ");
            builder.append(this.limit);
        }
        return builder.toString();
    }

    public String createSQL() {
        var builder = new StringBuilder();
        builder.append(String.format("create table if not exists `%s`(", tableWithSuffix()));
        var joiner = new StringJoiner(", ");
        for (var column : columns) {
            joiner.add(column.s());
        }
        for (var index : indices) {
            joiner.add(index.s());
        }
        builder.append(joiner.toString());
        builder.append(")");
        if (!options.isEmpty()) {
            builder.append(" ");
            joiner = new StringJoiner(" ");
            for (var option : options) {
                joiner.add(String.format("%s=%s", option.key(), option.value()));
            }
            builder.append(joiner.toString());
        }
        return builder.toString();
    }

    public String dropSQL() {
        return String.format("drop table if exists `%s`", tableWithSuffix());
    }

    public String truncateSQL() {
        return String.format("truncate table `%s`", tableWithSuffix());
    }

    public static Primitive primitive(Object o){
        return new Primitive(o);
    }

    public static PrimitiveCollection set(Object... os){
        var values = new ArrayList<Value>();
        for(Object o : os){
            values.add(primitive(o));
        }
        return new PrimitiveCollection(values);
    }

    public static PrimitiveCollection set(Collection<Object> cs){
        var values = new ArrayList<Value>();
        for(Object o : cs){
            values.add(primitive(o));
        }
        return new PrimitiveCollection(values);
    }

    public static SubQuery subQuery(Q q) {
        return new SubQuery(q);
    }

    public String tableWithSuffix() {
        return Utils.tableWithSuffix(table, suffix);
    }

    @Override
    public String toString(){
        if (this.type == Type.Select) {
            return this.selectSQL();
        } else if (this.type == Type.Insert) {
            return this.insertSQL();
        } else if (this.type == Type.Update) {
            return this.updateSQL();
        } else if (this.type == Type.Delete) {
            return this.deleteSQL();
        } else if (this.type == Type.Create) {
            return this.createSQL();
        } else if (this.type == Type.Drop) {
            return this.dropSQL();
        } else if (this.type == Type.Truncate) {
            return this.truncateSQL();
        }
        throw new IllegalArgumentException("sql not supported yet");
    }

    @FunctionalInterface
    public static interface Filterable{
        String s(int depth);
    }

    static class And implements Filterable{
        private List<Filterable> filters = new ArrayList<>();

        public And(Filterable... filters){
            for (Filterable filter : filters){
                this.filters.add(filter);
            }
        }

        public And then(Filterable... filters){
            for (Filterable filter : filters){
                this.filters.add(filter);
            }
            return this;
        }

        @Override
        public String s(int depth) {
            var joiner = new StringJoiner(" and ");
            for(var filter : filters){
                joiner.add(filter.s(depth + 1));
            }
            if(filters.size() == 1 || depth == 0){
                return joiner.toString();
            }
            return String.format("(%s)", joiner.toString());
        }
    }

    static class Or implements Filterable{
        private List<Filterable> filters = new ArrayList<>();

        public Or(Filterable... filters){
            for (Filterable filter : filters) {
                this.filters.add(filter);
            }
        }

        public Or then(Filterable... filters) {
            for (Filterable filter : filters) {
                this.filters.add(filter);
            }
            return this;
        }

        @Override
        public String s(int depth) {
            var joiner = new StringJoiner(" or ");
            for (var filter : filters) {
                joiner.add(filter.s(depth + 1));
            }
            if (filters.size() == 1 || depth == 0) {
                return joiner.toString();
            }
            return String.format("(%s)", joiner.toString());
        }
    }

    static class Comparator implements Filterable{
        private String field;
        private String op;
        private Value value;

        public Comparator(String field, String op, Value value){
            this.field = field;
            this.op = op;
            this.value = value;
        }

        @Override
        public String s(int depth) {
            return String.format("`%s` %s %s", field, op, value.value());
        }
    }

    static class Between implements Filterable {
        private String name;
        private Value min, max;

        public Between(String name, Value min, Value max) {
            this.name = name;
            this.min = min;
            this.max = max;
        }

        @Override
        public String s(int depth) {
            return String.format("`%s` between %s and %s", name, min.value(), max.value());
        }
    }

    static class In implements Filterable {
        private String name;
        private Value value;

        public In(String name, Value value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String s(int depth) {
            return String.format("`%s` in %s", name, value.value());
        }
    }

    static class NotIn implements Filterable {
        private String name;
        private Value value;

        public NotIn(String name, Value value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String s(int depth) {
            return String.format("`%s` not in %s", name, value.value());
        }
    }

    static class Not implements Filterable {
        private Filterable filter;

        public Not(Filterable filter) {
            this.filter = filter;
        }

        @Override
        public String s(int depth) {
            return String.format("not %s", filter.s(depth + 1));
        }
    }

    static class Setter {
        private String name;
        private Value value;

        public Setter(String name, Value value){
            this.name = name;
            this.value = value;
        }

        public String s(){
            return String.format("`%s` = %s", name, value.value());
        }
    }

    static class OrderBy{
        private String name;
        private String direction;

        private OrderBy(String name){
            this.name = name;
        }

        private OrderBy(String name, String direction){
            this.name = name;
            this.direction = direction.toLowerCase();
        }

        public String s(){
            if (this.direction == null) {
                return name;
            }
            return String.format(" %s %s", name, direction);
        }
    }

    @FunctionalInterface
    public static interface Value{
        String value();
    }

    static class Placeholder implements Value {
        @Override
        public String value() {
            return "?";
        }
    }

    static class Primitive implements Value{

        private Object v;

        public Primitive(Object v){
            this.v = v;
        }

        @Override
        public String value() {
            if (v == null) {
                return null;
            }
            if (v instanceof String) {
                return String.format("'%s'", v);
            }
            return v.toString();
        }
    }

    static class PrimitiveCollection implements Value{
        private List<Value> values;

        public PrimitiveCollection(List<Value> values){
            this.values = values;
        }

        public PrimitiveCollection(Value... values){
            this.values = Arrays.asList(values);
        }

        @Override
        public String value() {
            var builder = new StringBuilder();
            builder.append("(");
            var joiner = new StringJoiner(", ");
            for (var value : values) {
                joiner.add(value.toString());
            }
            builder.append(joiner.toString());
            builder.append(")");
            return builder.toString();
        }
    }

    static class SubQuery implements Value{
        private Q q;

        public SubQuery(Q q) {
            this.q = q;
        }

        @Override
        public String value() {
            return String.format("(%s)", q);
        }
    }

    public Q option(String key, String value){
        this.options.add(new Option(key, value));
        return this;
    }

    public Q index(String... columns){
        return index(false, false, columns);
    }

    public Q uniqueIndex(String... columns){
        return index(false, true, columns);
    }

    public Q primaryIndex(String... columns){
        return index(true, false, columns);
    }

    public Q index(boolean primaryKey, boolean unique, String... columns){
        this.indices.add(new Index(false, false, columns));
        return this;
    }

    public Q cloumn(String name, String type){
        return column(name, type, false, true, null);
    }

    public Q column(String name, String type, boolean autoIncrement, boolean nullable, String defaultValue){
        this.columns.add(new Column(name, type, autoIncrement, nullable, defaultValue));
        return this;
    }
}
