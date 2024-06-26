package com.skhuthon.sweet_little_kitty.app.entity.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skhuthon.sweet_little_kitty.app.entity.region.Region;
import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long diaryId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000)
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd - HH:mm", timezone = "Asia/Seoul")
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @PrePersist
    public void prePersist() {
        this.dateCreated = LocalDateTime.now();
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diary_image_urls", joinColumns = @JoinColumn(name = "diary_id"))
    @Column(name = "image_urls")
    private List<String> imageUrls;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Builder
    private Diary(String title, String content, List<String> imageUrls, Visibility visibility, User user, Region region) {
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
        this.visibility = visibility;
        this.user = user;
        this.region = region;
    }
}
