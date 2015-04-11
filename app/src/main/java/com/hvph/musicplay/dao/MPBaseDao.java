package com.hvph.musicplay.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hvph.musicplay.MusicPlayApplication;
import com.hvph.musicplay.model.Model;
import com.hvph.musicplay.model.annotation.DBColumn;
import com.hvph.musicplay.model.annotation.DBTable;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by HoangHVP on 11/6/2014.
 */
public class MPBaseDao<T extends Model> {
    private Class<T> tClass;
    /**
     * The application context.
     */
    protected Context mContext;
    /**
     * Keep the static connection to database.
     */
    protected SQLiteDatabase mDB;

    /**
     * The default constructor.
     */
    public MPBaseDao(Class<T> tClass) {
        mContext = MusicPlayApplication.mContext;
        if (MusicPlayApplication.mDatabaseConnection != null) {
            mDB = MusicPlayApplication.mDatabaseConnection.getDatabase();
        }
        this.tClass = tClass;
    }

    public void dropTable(SQLiteDatabase db) {
        if (getTableName(tClass) == null) {
            return;
        }
        StringBuffer sql = new StringBuffer("DROP TABLE IF EXISTS ");
        sql.append(getTableName(tClass));
        db.execSQL(sql.toString());
    }

    public void createTable(SQLiteDatabase db) {
        if (getTableName(tClass) == null) {
            return;
        }
        StringBuffer sql = new StringBuffer("CREATE TABLE ");
        sql.append(getTableName(tClass));
        sql.append(" (");
        //sql.append(BaseColumns._ID);
        //sql.append(" INTEGER PRIMARY KEY AUTOINCREMENT");
        ArrayList<Field> fields = getAllField();
        for (Field field : fields) {
            DBColumn fieldEntityAnnotation = field.getAnnotation(DBColumn.class);
            if (fieldEntityAnnotation != null) {
                if (fieldEntityAnnotation.isPrimaryKey()) {
                    sql.append(fieldEntityAnnotation.name());
                    sql.append(" ");
                    sql.append(fieldEntityAnnotation.type());
                    sql.append(" ");
                    sql.append("PRIMARY KEY");
                    sql.append(" ");
                    sql.append("AUTOINCREMENT");

                } else {
                    sql.append(", ");
                    sql.append(fieldEntityAnnotation.name());
                    sql.append(" ");
                    sql.append(fieldEntityAnnotation.type());
                }
            }
        }
        for (Field field : fields) {
            DBColumn fieldEntityAnnotation = field.getAnnotation(DBColumn.class);
            if (fieldEntityAnnotation != null && fieldEntityAnnotation.isForeignKey()) {
                sql.append(", ");
                sql.append("FOREIGN KEY(");
                sql.append(fieldEntityAnnotation.name());
                sql.append(") REFERENCES ");
                sql.append(fieldEntityAnnotation.foreignTable());
                sql.append("(");
                sql.append(fieldEntityAnnotation.foreignColumn());
                sql.append(")");
                if(fieldEntityAnnotation.onUpdateCascade()){
                    sql.append(" ON UPDATE CASCADE");
                }
                if(fieldEntityAnnotation.onDeleteCascade()){
                    sql.append(" ON DELETE CASCADE");
                }
            }
        }
        sql.append(");");
        db.execSQL(sql.toString());
    }

    /**
     * Check whether a specified record exist or not
     *
     * @param id specified id of record wants to check.
     * @return return true if specified data exist.
     */
    public boolean isExistence(final String id) {
        Cursor cursor = get(id);
        if (cursor.getCount() > 0) {
            // Close cursor after using
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * Delete specified record by id.
     *
     * @param id specified id of record wants to delete.
     * @return true if delete successfully
     */
    public int delete(String id) {
        StringBuffer whereClause = new StringBuffer(getPrimaryKeyColumnName());
        whereClause.append("='");
        whereClause.append(id);
        whereClause.append("'");
        return mDB.delete(getTableName(tClass), whereClause.toString(), null);
    }

    /**
     * Delete specified record by item.
     *
     * @param item specified id of record wants to delete.
     * @return true if delete successfully
     */
    public int delete(T item) {
        StringBuffer whereClause = new StringBuffer(getPrimaryKeyColumnName());
        whereClause.append("=?");
        //whereClause.append(item.getId());
        String[] whereArgs = {item.getId()};
        //whereClause.append("'");
        int rowEffected = mDB.delete(getTableName(tClass), whereClause.toString(), whereArgs);
        return rowEffected;
    }

    /**
     * Delete all data of table.
     */
    public int deleteAll() {
        return mDB.delete(getTableName(tClass), null, null);
    }

    /**
     * Insert a object to corresponding table.
     * <p/>
     * <p/>
     * data wants to insert
     *
     * @param item
     */
    public long insert(T item) {
        if (item != null) {
            try {
                ContentValues value = getFilledContentValues(item);
                long id = mDB.insert(getTableName(tClass), null, value);
                return id;
            } catch (IllegalAccessException e) {

            }
        }
        return -1;
    }

    /**
     * Update data of specified item.
     *
     * @param item object wants to update
     */
    public int update(T item) {
        if (item != null) {
            try {
                ContentValues value = getFilledContentValues(item);
                String whereClause = getPrimaryKeyColumnName() + "=?";
                String[] whereArgs = new String[]{item.getId()};
                return mDB.update(getTableName(tClass), value, whereClause, whereArgs);
            } catch (IllegalAccessException e) {
            }
        }
        return -1;
    }

    /**
     * Get all data in a specified table.
     *
     * @return array list keeps all data of table
     */
    public ArrayList<T> getAll() {

        if (mDB == null) {
            return null;
        }

        ArrayList<T> items = new ArrayList<T>();
        Cursor cursor = null;

        synchronized (mDB) {
            cursor = getAllByCursor();
        }
        // convert cursor to list items.
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                T newTObject;
                try {
                    newTObject = tClass.newInstance();
                    bindObject(newTObject, cursor);
                    items.add(newTObject);
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                } catch (NoSuchFieldException e) {
                }
            }
            cursor.close();
            cursor = null;
        }
        return items;
    }

    /**
     * Get record by specified id.
     *
     * @param id specified id
     */
    public T getItem(String id) {
        Cursor cursor = get(id);
        // convert cursor to list items.
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            T newTObject = null;
            try {
                newTObject = tClass.newInstance();
                bindObject(newTObject, cursor);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (NoSuchFieldException e) {
            }
            cursor.close();
            cursor = null;
            return newTObject;
        }
        return null;
    }

    public void resetSequence() {
        mDB.execSQL("delete from sqlite_sequence where name='" + getTableName(tClass) + "'");//reset auto increment field to zero
    }

    private void bindObject(T newTObject, Cursor cursor)
            throws NoSuchFieldException, IllegalAccessException {
        for (Field field : tClass.getDeclaredFields()) {
            if (!field.isAccessible())
                field.setAccessible(true); // for private variables
            DBColumn fieldEntityAnnotation = field.getAnnotation(DBColumn.class);
            if (fieldEntityAnnotation != null) {
                field.set(newTObject, getValueFromCursor(cursor, field));
            }
        }
    }

    // Get content from specific types
    private Object getValueFromCursor(Cursor cursor, Field field)
            throws IllegalAccessException {
        Class<?> fieldType = field.getType();
        Object value = null;
        int columnIndex = cursor.getColumnIndex(getColumnName(field));
        if (fieldType.isAssignableFrom(Long.class)
                || fieldType.isAssignableFrom(long.class)) {
            value = cursor.getLong(columnIndex);
        } else if (fieldType.isAssignableFrom(String.class)) {
            value = cursor.getString(columnIndex);
        } else if ((fieldType.isAssignableFrom(Integer.class) || fieldType
                .isAssignableFrom(int.class))) {
            value = cursor.getInt(columnIndex);
        } else if ((fieldType.isAssignableFrom(Byte[].class) || fieldType
                .isAssignableFrom(byte[].class))) {
            value = cursor.getBlob(columnIndex);
        } else if ((fieldType.isAssignableFrom(Double.class) || fieldType
                .isAssignableFrom(double.class))) {
            value = cursor.getDouble(columnIndex);
        } else if ((fieldType.isAssignableFrom(Float.class) || fieldType
                .isAssignableFrom(float.class))) {
            value = cursor.getFloat(columnIndex);
        } else if ((fieldType.isAssignableFrom(Short.class) || fieldType
                .isAssignableFrom(short.class))) {
            value = cursor.getShort(columnIndex);
        } else if (fieldType.isAssignableFrom(Byte.class)
                || fieldType.isAssignableFrom(byte.class)) {
            value = (byte) cursor.getShort(columnIndex);
        } else if (fieldType.isAssignableFrom(Boolean.class)
                || fieldType.isAssignableFrom(boolean.class)) {
            int booleanInteger = cursor.getInt(columnIndex);
            value = booleanInteger == 1;
        }
        return value;
    }

    /**
     * Get all data of specified table and return in a cursor.
     *
     * @return the cursor keeps all data of table
     */
    public Cursor getAllByCursor() {
        return mDB.query(getTableName(tClass), null, null, null, null, null,
                null);
    }

    /**
     * Get record by specified id.
     *
     * @param id specified id
     * @return the cursor
     */
    private Cursor get(String id) {
        Cursor cursor = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" select * ");
        sql.append(" from " + getTableName(tClass));
        sql.append(" where " + getPrimaryKeyColumnName() + " = ?");
        cursor = mDB.rawQuery(sql.toString(), null);
        return cursor;
    }

    private void putInContentValues(ContentValues contentValues, Field field,
                                    Object object) throws IllegalAccessException {
        if (!field.isAccessible())
            field.setAccessible(true); // for private variables
        Object fieldValue = field.get(object);
        String key = getColumnName(field);
        if (fieldValue instanceof Long) {
            contentValues.put(key, Long.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof String) {
            contentValues.put(key, fieldValue.toString());
        } else if (fieldValue instanceof Integer) {
            contentValues.put(key, Integer.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Float) {
            contentValues.put(key, Float.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Byte) {
            contentValues.put(key, Byte.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Short) {
            contentValues.put(key, Short.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Boolean) {
            contentValues.put(key, Boolean.parseBoolean(fieldValue.toString()));
        } else if (fieldValue instanceof Double) {
            contentValues.put(key, Double.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Byte[] || fieldValue instanceof byte[]) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        outputStream);
                objectOutputStream.writeObject(fieldValue);
                contentValues.put(key, outputStream.toByteArray());
                objectOutputStream.flush();
                objectOutputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
            }
        }
    }

    private static String getColumnName(Field field) {
        DBColumn annotationColumn = field.getAnnotation(DBColumn.class);
        String column = null;
        if (annotationColumn != null) {
            if (annotationColumn.name().equals("")) {
                column = field.getName();
            } else {
                column = annotationColumn.name();
            }
        }
        return column;
    }

    private ContentValues getFilledContentValues(Object object)
            throws IllegalAccessException {
        ContentValues contentValues = new ContentValues();
        ArrayList<Field> fields = getAllField();
        for (Field field : fields) {
            DBColumn fieldEntityAnnotation = field.getAnnotation(DBColumn.class);
            if (fieldEntityAnnotation != null) {
                if (!fieldEntityAnnotation.isAutoincrement()) {
                    putInContentValues(contentValues, field, object);
                }
            }
        }
        return contentValues;
    }

    public static String getTableName(Class<?> tClass) {
        DBTable annotationTable = tClass.getAnnotation(DBTable.class);
        String table = tClass.getSimpleName();
        if (annotationTable != null) {
            if (!annotationTable.name().equals("")) {
                table = annotationTable.name();
            }
        }
        return table;
    }

    private String getPrimaryKeyColumnName() {
        for (Field field : tClass.getDeclaredFields()) {
            DBColumn fieldEntityAnnotation = field.getAnnotation(DBColumn.class);
            if (fieldEntityAnnotation != null) {
                String columnName = getColumnName(field);
                if (columnName != null) {
                    DBColumn annotationColumn = field.getAnnotation(DBColumn.class);
                    if (annotationColumn.isPrimaryKey()) {
                        return columnName;
                    }
                }
            }
        }
        return "_id";
    }

    private ArrayList<Field> getAllField() {
        ArrayList<Field> parentFields = new ArrayList<Field>(Arrays.asList(tClass.getFields()));
        ArrayList<Field> childFields = new ArrayList<Field>(Arrays.asList(tClass.getDeclaredFields()));
        ArrayList<Field> fields = parentFields;
        fields.addAll(childFields);
        return fields;
    }


}
