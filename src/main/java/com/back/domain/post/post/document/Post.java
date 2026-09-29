package com.back.domain.post.post.document;

import com.back.global.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "posts")
@Data // @Data는 @Getter, @Setter, @ToString, @EqualsAndHashCode 포함
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseDocument<String> {
    // FieldType.Text : 전문 검색 가능
    @Field(type= FieldType.Text)
    private String title;
    @Field(type= FieldType.Text)
    private String content;

    // FieldType.Keyword : 정확한 일치 검색용
    @Field(type= FieldType.Keyword)
    private String author;

    // 생성자
    public Post(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

}
