package com.kingwarluo.orm;

import java.util.LinkedHashMap;
import java.util.Map;

public class Meta {
    String name;
    Map<String, Column> columns = new LinkedHashMap<>(); // column_name => Column case-insensitive
    Map<String, Column> fields = new LinkedHashMap<>(); // field_name => Column case-sensitive
    E.TableOptions options;
    E.TableIndices indices;

    public Meta column(Column column){
        if(this.columns.put(column.name(), column) != null){
            throw new KidsException("duplicated column name " + column.name());
        }
        this.fields.put(column.field().getName(), column);
        return this;
    }
}
