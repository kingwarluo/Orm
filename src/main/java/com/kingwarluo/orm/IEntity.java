package com.kingwarluo.orm;

import com.kingwarluo.orm.E.*;

public interface IEntity {
    /**
     * ����
     * @return
     */
    String table();

    /**
     * �ֱ���븲�Ǵ˷���
     * @return
     */
    default String suffix(){
        return null;
    }

    default String tableWithSuffix(){
        return tableWith(suffix());
    }

    default String tableWith(String suffix){
        return Utils.tableWithSuffix(table(), suffix);
    }

    /**
     * ����������ṹ���ԣ���engine=innodb,���ڶ�̬������
     * @return
     */
    TableOptions options();

    /**
     * ������������������Ϣ�����ڶ�̬������
     * @return
     */
    TableIndices indices();
}
