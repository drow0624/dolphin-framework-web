package priv.cy.common;

import priv.cy.common.db.annotation.DBTable;
import priv.cy.project.sysmgr.demo.domain.Article;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

//@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Test
    public void contextLoads() {
        Article article = new Article();
//        ClassUtils.
//        BeanUtils.
//        ReflectionUtils.
        Class<Article> clazz = Article.class;
        DBTable annotation = clazz.getAnnotation(DBTable.class);
        System.out.println(annotation.value());
        RequestMapping rq = clazz.getAnnotation(RequestMapping.class);
        System.out.println(rq);
        Integer a1= 1;
        System.out.println(a1.getClass().isPrimitive());

        int[] a = new int[3];
        int[] b = new int[]{4,5,5};//直接赋值后不可以指定长度，否则ＣＥ
        int[][] c = new int[3][2];
        String[] d = new String[]{"jjj","kkkk"};
        System.out.println(a==b);//false
        System.out.println(a.getClass()==b.getClass());//true
        //System.out.println(a.getClass()==d.getClass());    //比较字节码a和cd也没法比
        System.out.println(a.getClass());//输出class [I
        System.out.println(a.getClass().getName());//输出[I,中括号表示数组，I表示整数

        System.out.println(a.getClass().getSuperclass());//输出class java.lang.Object
        System.out.println(d.getClass().getSuperclass());//输出class java.lang.Object

        //由于父类都是Object,下面都是可以的
        Object obj1 = a;//不可是Object[]
        Object obj2 = b;
        Object[] obj3 = c;//基本类型的一位数组只可以当做Object，非得还可以当做Object[]
        Object obj4 = d;

        //注意asList处理int[]和String[]的区别
        System.out.println(Arrays.asList(b));//1.4没有可变参数，使用的是数组,[[I@1bc4459]
        System.out.println(Arrays.asList(d));//[jjj, kkkk]

    }

}
