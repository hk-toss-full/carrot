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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dcategory_id")
    private DCategory dCategory;

    @Column(name="daily_title")
    private String title;

    @Column(name="daily_content")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "daily")
    private List<Comment> comments;

    public void updated(DCategory dCategory, String title, String content){
        this.dCategory = dCategory;
        this.title = title;
        this.content = content;
    }
}
