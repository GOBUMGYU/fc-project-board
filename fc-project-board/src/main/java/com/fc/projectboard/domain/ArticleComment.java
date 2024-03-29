package com.fc.projectboard.domain;

import com.fc.projectboard.domain.Article;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //댓글을 변경하거나 지웠을 때 관련되어 있는 게시글이 영향을 받아야 하는가? 댓글은 혼자 지워지고 게시글은 영향을 받지 않아야 함
    @Setter @ManyToOne(optional = false) private Article article; //게시글(ID)
    @Setter @Column(nullable = false, length = 2000) private String content; //내용

    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; //생성일자
    @CreatedBy @Column(nullable = false, length = 100)private String createdBy; //생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; //수정일자
    @LastModifiedBy @Column(nullable = false, length = 100)private String modifiedBy; //수정자

    protected ArticleComment() {}

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
