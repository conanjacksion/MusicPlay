package com.hvph.musicplay.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by HoangHVP on 11/5/2014.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DBColumn {
    boolean isPrimaryKey() default false;
    boolean isAutoincrement() default false;
    String type() default "TEXT";
    String name();
    boolean isForeignKey() default false;
    String foreignColumn() default "";
    String foreignTable() default "";
    boolean onUpdateCascade() default true;
    boolean onDeleteCascade() default true;

}
