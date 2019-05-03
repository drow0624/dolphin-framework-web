package com.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONWriter;
import com.example.annotion.DBColumn;
import com.example.annotion.DBTable;
import com.example.entity.Article;
import com.example.entity.CommentText;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class DBReflectionUtil {

    private DBReflectionUtil() {

    }

    /**
     * 通过持久化对象获取所有数据库字段名
     * @param clazz 对象
     * @return 含有@DBField的字段集合
     */
    private static List<String> getAllDBFieldNames(Class<?> clazz) {
        List<String> fieldNames = new ArrayList<>();
        if (!clazz.isAnnotationPresent(DBTable.class)) {
            return fieldNames;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBColumn.class)) {
                continue;
            }
            // 获取db字段名
            DBColumn dbField = field.getAnnotation(DBColumn.class);
            String fieldName;
            if (StringUtils.isEmpty(dbField.value())) {
                // 直接使用field的名字作为字段名
                fieldName = field.getName();
            } else {
                fieldName = dbField.value().toString();
            }
            fieldNames.add(fieldName);
        }
        return fieldNames;
    }

    /**
     * 综合多个持久化类的字段
     * @param classes class 组
     * @return 含有@DBField的字段集合
     */
    public static List<String> allDBFieldNames(Class<?>... classes) {
        Set<String> fieldSet = new HashSet<>();
        for (Class<?> clazz : classes) {
            do {
                final List<String> allDBFieldNames = getAllDBFieldNames(clazz);
                fieldSet.addAll(allDBFieldNames);
                clazz = clazz.getSuperclass();
            } while (clazz != Object.class);
        }
        return new ArrayList<>(fieldSet);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */

    public static Method declaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                log.warn("获取类定义方法失败");
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters     : 父类中的方法参数
     * @return 父类中方法的执行结果
     */

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) {
        //根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = declaredMethod(object, methodName, parameterTypes);

        //抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true);

        try {
            if (null != method) {
                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException e) {
            log.warn("调用方法失败");
        } catch (IllegalAccessException e) {
            log.warn("调用方法失败");
        } catch (InvocationTargetException e) {
            log.warn("调用方法失败");
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */

    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;

        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                log.warn("获取定义方法失败");
            }
        }

        return null;
    }


    public static Collection<Field> getAllFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String name = declaredField.getName();
                if (!fields.containsKey(name)) {
                    fields.put(name, declaredField);
                }
            }
        }
        return fields.values();
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */

    public static void setFieldValue(Object object, String fieldName, Object value) {
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            log.warn("设置属性值失败");
        } catch (IllegalAccessException e) {
            log.warn("设置属性值失败");
        }

    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */

    public static Object getFieldValue(Object object, String fieldName) {

        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);

        //抑制Java对其的检查
        field.setAccessible(true);

        try {
            //获取 object 中 field 所代表的属性值
            return field.get(object);

        } catch (Exception e) {
            log.warn("获取属性值失败");
        }

        return null;
    }

    /**
     * 将model属性拷贝到clazz实例中
     *
     * @param model
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Map<String, Object> model, Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException e) {
            log.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.warn(e.getMessage(), e);
        }
        copyProperties(model, instance);
        return instance;
    }

    private static <T> void copyProperties(Map<String, Object> model, T instance) {
        Class<?> clazz = instance.getClass();
        Collection<Field> fields = DBReflectionUtil.getAllFields(clazz);
        for (Field field : fields) {
            String name = field.getName();
            DBColumn annotation = field.getAnnotation(DBColumn.class);
            if (annotation != null) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                String columnName = annotation.value();
                Object fieldValue = model.get(columnName);
                if (fieldValue != null) {
                    if (fieldType.isAssignableFrom(fieldValue.getClass())) {
                        try {
                            field.set(instance, fieldValue);
                        } catch (IllegalAccessException e) {
                            log.warn(e.getMessage(), e);
                        }
                    } else {
                        log.warn("字段名["+field.getName()+"]设置字段值和字段类型不匹配["
                                +fieldValue.getClass().getName()+","+fieldType.getName()+"]");
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("id", 123456L);
        model.put("hy_url", "www.baidu.com");
        model.put("name", "终究一尘不染");
        CommentText text = new CommentText();
        text.setText("看暮色已淹没城");
        model.put("comment", text);

        Article article = DBReflectionUtil.copyProperties(model, Article.class);

        log.info(JSON.toJSONString(article));

        JSONWriter writer = new JSONWriter(new FileWriter("../article.json"));
        writer.startArray();
//        writer.startObject();
        for (int i = 0; i < 2; ++i) {
            writer.writeValue(article);
        }
        writer.endArray();
        writer.close();

    }

}
