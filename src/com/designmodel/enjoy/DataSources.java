package com.designmodel.enjoy;

import javax.sql.DataSource;

/**
 * Created by lw on 14-5-1.
 * <p/>
 * 获取不同的数据库连接信息
 */
public class DataSources implements DataSourcesInterface {

    private String name;
    private static DataSource name1, name2;

    DataSources(String name) {
        this.name = name;
    }

    @Override
    public DataSource getDataSourceByName() {
        if (name.equals(name1.getClass().getName())) {
            return name1;
        } else if (name.equals(name2.getClass().getName())) {
            return name2;
        }
        return null;
    }
}
