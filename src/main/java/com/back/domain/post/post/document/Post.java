package com.back.domain.post.post.document;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.OffsetDateTime;

@Document(indexName = "posts")
@Getter
public class Post {
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
    private OffsetDateTime createdAt;

    @Field(
            type = FieldType.Date,
            format = DateFormat.date_time
    )
    private OffsetDateTime lastModifiedAt;

    // 생성자
    public Post(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;

        // createdAt, lastModifiedAt을 OffsetDateTime.now()로 자동 설정
        this.createdAt = OffsetDateTime.now();
        this.lastModifiedAt = OffsetDateTime.now();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                '}';
    }
}
