package com.skhuthon.sweet_little_kitty.app.repository.diary;

import com.skhuthon.sweet_little_kitty.app.entity.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d WHERE d.visibility = 'PUBLIC'")
    List<Diary> findAllPublicDiaries();

    @Query("SELECT d FROM Diary d WHERE d.user.userId = :friendId AND d.visibility = 'PUBLIC'")
    List<Diary> findPublicDiariesByFriendId(Long friendId);

    @Query("SELECT d FROM Diary d WHERE d.user.userId = :diaryId AND d.user.userId = :friendId AND d.visibility = 'PUBLIC'")
    Optional<Diary> findPublicDiaryByIdAndFriendId(Long diaryId, Long friendId);
}
