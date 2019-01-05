package com.kingwarluo.orm;

import java.util.Map;

public abstract class DB {

    private Map<Class<? extends IEntity>, Meta> metas;

}
