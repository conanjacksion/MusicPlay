package com.hvph.musicplay.model;

import android.provider.BaseColumns;

import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.annotation.DBColumn;

/**
 * Created by HoangHVP on 11/4/2014.
 */
public class Model {
    @DBColumn(type = "INTEGER", isPrimaryKey = true, isAutoincrement = true, name = BaseColumns._ID)
    public String id;
    @DBColumn(name = DaoDefinition.COLUMN_NAME_NAME)
    public String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
