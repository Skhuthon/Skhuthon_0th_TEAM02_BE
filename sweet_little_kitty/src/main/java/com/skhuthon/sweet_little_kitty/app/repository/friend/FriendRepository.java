package com.skhuthon.sweet_little_kitty.app.repository.friend;

import com.skhuthon.sweet_little_kitty.app.entity.friend.Friend;
import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserUserId(Long userId);
    void deleteByFriendIdAndUserUserId(Long id, Long userId);
    boolean existsByUserAndFriendName(User user, String friendName);
}
