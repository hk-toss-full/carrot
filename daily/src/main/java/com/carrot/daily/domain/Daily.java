package com.carrot.daily.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="daylife_id")
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="location_id")
    private Long locationId;
    @Column(name="DCategory_id")
    private Long categoryId;
    @Column(name="daily_title")
    private String title;
    @Column(name="daily_content")
    private String content;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    @OneToMany
    @JoinColumn(name="comment_id")
    private List<Comment> comments;


    public void updated( Long categoryId, String title, String content){
        this.categoryId=categoryId;
        this.title=title;
        this.content=content;
    }
}
