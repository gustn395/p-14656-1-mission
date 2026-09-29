package com.back.domain.post.comment.document;

import com.back.global.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "comments")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseDocument<String> {

    // 연관 Post ID - 정확한 일치 검색용
    @Field(type = FieldType.Keyword)
    private String postId;

    // content: 내용 - 전문 검색 가능
    @Field(type = FieldType.Text)
    private String content;

    // 작성자 - 정확한 일치 검색용
    @Field(type = FieldType.Keyword)
    private String author;

    public Comment(String postId, String content, String author) {
        this.postId = postId;
        this.content = content;
        this.author = author;
    }
}
