package com.skhuthon.sweet_little_kitty.app.repository.diary;

import com.skhuthon.sweet_little_kitty.app.entity.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
