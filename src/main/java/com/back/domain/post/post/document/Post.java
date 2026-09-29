package com.back.domain.post.post.document;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.OffsetDateTime;

@Document(indexName = "posts")
@Data // @Data는 @Getter, @Setter, @ToString, @EqualsAndHashCode 포함
public class Post implements Persistable<String> {
    @Id
    // Elasticsearch에서는 ID가 보통 String 타입입니다. 자동 생성 시 UUID 형태의 문자열이 할당
    private String id;

    // FieldType.Text : 전문 검색 가능
    @Field(type= FieldType.Text)
    private String title;
    @Field(type= FieldType.Text)
    private String content;

    // FieldType.Keyword : 정확한 일치 검색용
    @Field(type= FieldType.Keyword)
    private String author;

    @Field(
            type = FieldType.Date,
            format = DateFormat.date_time
    )
    @CreatedDate
    private OffsetDateTime createdAt;

    @Field(
            type = FieldType.Date,
            format = DateFormat.date_time
    )
    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;

    // 생성자
    public Post(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @Override
    public boolean isNew() {
        return id == null || (createdAt == null && lastModifiedAt == null);
    }
}
