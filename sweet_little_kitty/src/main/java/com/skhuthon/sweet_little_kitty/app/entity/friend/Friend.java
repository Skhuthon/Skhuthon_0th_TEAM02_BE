package com.skhuthon.sweet_little_kitty.app.entity.friend;

import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long friendId;

    @Column(name = "friend_name", length = 10, nullable = false)
    private String friendName;

    @Column(name = "are_we_friend", nullable = false)
    private boolean areWeFriend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Friend(String friendName, boolean areWeFriend, User user) {
        this.friendName = friendName;
        this.areWeFriend = areWeFriend;
        this.user = user;
    }
}
