package com.example.util.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.GregorianCalendar;

//#{ } 标记会提示Spring 这个标记里的内容是SpEL表达式。 当然，这个可以通过Expression templating功能扩展做改造
//#{rootBean.nestBean.propertiy} “.”操作符表示属性或方法引用，支持层次调用
//#{aList[0] } 数组和列表使用方括号获得内容
//#{aMap[key] } maps使用方括号获得内容
//#{rootBean?.propertiy} 此处"?"是安全导航运算符器，避免空指针异常
//#{condition ? trueValue : falseValue} 三元运算符（IF-THEN-ELSE）
//#{valueA?:defaultValue} Elvis操作符,当valueA为空时赋值defaultValue

@Slf4j
public class BeanWrapperTest {

    @Test
    public void beanWapper() {
        ExpressionParser parser = new SpelExpressionParser();
        //a. 调用String这个javaBean的'getBytes()'
        Expression exp = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp.getValue();
        log.info("字节内容："+ bytes);

        //b.嵌套的bean实例方法调用，通过'.'运算符
        exp = parser.parseExpression("'Hello World'.bytes.length");
        int len = (Integer) exp.getValue();
        log.info("字节长度：" + len);

    }

}