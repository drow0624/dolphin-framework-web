package com.example.entity;

import com.example.annotion.*;
import com.example.excel.article.ArticleRow;
import com.example.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DBTable("article")
@ESDocument(indexName="doc_index",type="article")
public class Article extends Document{

    @DBColumn("id")
    @ESField(type= FieldType.LONG)
    private Long id;

    @DBColumn("name")
    @ESField(type= FieldType.TEXT)
    private String name;

    @DBColumn("content")
    @ESField(type= FieldType.TEXT)
    private String content;

    @DBColumn("author")
    @ESField(type= FieldType.KEYWORD)
    private String author;

    @DBColumn("create_time")
    @ESField(type= FieldType.DATE,dateFormat = DateFormat.basic_date)
    private Date createTime;

    @DBColumn("size")
    @ESField(type= FieldType.INTEGER)
    private Integer size;@DBColumn("size")

    @ESField(type= FieldType.INTEGER)
    private Integer total;

    public Article from(ArticleRow row){
        this.name = row.getName();
        this.author = row.getAuthor();
        this.content = row.getContent();
//        this.createTime = LocalDateTime.now().getLong(ChronoField.);
        return this;
    }


    public static char randomChar() {
        int len =  commonChars.length();
        return commonChars.charAt((int) (Math.random() * len));
    }


    public static String statement(int len){
        StringBuilder sb = new StringBuilder();
        if(len<1)len=5;
        for(int i=0;i<len;i++){
            sb.append(randomChar());
        }
        return sb.toString();
    }

    public static Article createArticle(){
        Article article = Article.builder()
                .id(System.nanoTime())
                .name("name:" + statement(15))
                .content("content:" + statement(30))
                .author("author:" + statement(5))
                .createTime(DateTimeUtil.dateNow())
                .size((int) (Math.random()*100))
                .build();

        article.setUrl("url:"+statement(8));
        article.setKeywords("keywords"+statement(3));
        return article;
    }

    private static final String commonChars ="的一了是我不在人们有来他这上着个地到大里说就去子得也和那要下看天时过出小么起你都把好还多没为" +
            "又可家学只以主会样年想生同老中十从自面前头道它后然走很像见两用她国动进成回什边作对开而己些现山民候经发工向事命给长水几义三" +
            "声于高手知理眼志点心战二问但身方实吃做叫当住听革打呢真全才四已所敌之最光产情路分总条白话东席次亲如被花口放儿常气五第使写军" +
            "吧文运再果怎定许快明行因别飞外树物活部门无往船望新带队先力完却站代员机更九您每风级跟笑啊孩万少直意夜比阶连车重便斗马哪化太" +
            "指变社似士者干石满日决百原拿群究各六本思解立河村八难早论吗根共让相研今其书坐接应关信觉步反处记将千找争领或师结块跑谁草越字" +
            "加脚紧爱等习阵怕月青半火法题建赶位唱海七女任件感准张团屋离色脸片科倒睛利世刚且由送切星导晚表够整认响雪流未场该并底深刻平伟" +
            "忙提确近亮轻讲农古黑告界拉名呀土清阳照办史改历转画造嘴此治北必服雨穿内识验传业菜爬睡兴形量咱观苦体众通冲合破友度术饭公旁房" +
            "极南枪读沙岁线野坚空收算至政城劳落钱特围弟胜教热展包歌类渐强数乡呼性音答哥际旧神座章帮啦受系令跳非何牛取入岸敢掉忽种装顶急" +
            "林停息句区衣般报叶压慢叔背细";

    public static void main(String[] args) {
        System.out.println(randomChar());
    }
}
