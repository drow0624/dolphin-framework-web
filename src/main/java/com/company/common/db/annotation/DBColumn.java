package com.company.common.db.annotation;


import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DBColumn {
    /**
     * 字段名
     * @return
     */
    String value() default "";
    /**
     * 自增长字段
     *
     * @return
     */
    boolean autoInc() default false;
}
