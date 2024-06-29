package com.skhuthon.sweet_little_kitty.app.repository.friend;

import com.skhuthon.sweet_little_kitty.app.entity.friend.Friend;
import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUser(User user);
    void deleteByFriendIdAndUserUserId(Long id, Authentication authentication);
    boolean existsByUserAndFriendName(Authentication authentication, String friendName);
}
