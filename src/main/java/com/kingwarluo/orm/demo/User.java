package com.kingwarluo.orm.demo;

import java.util.Date;
import com.kingwarluo.orm.E.Table;
import com.kingwarluo.orm.E.Column;
import com.kingwarluo.orm.E.TableOptions;
import com.kingwarluo.orm.E.TableIndices;
import com.kingwarluo.orm.IEntity;

@Table
public class User implements IEntity {

    @Column(name = "id", type = "int", autoincrement = true, nullable = false)
    private Integer id;
    private String name;
    private String nick;
    private String passwd;
    private Date createAt;

    @Override
    public String table() {
        return null;
    }

    @Override
    public TableOptions options() {
        return null;
    }

    @Override
    public TableIndices indices() {
        return null;
    }

}
