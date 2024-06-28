package com.skhuthon.sweet_little_kitty.app.repository.region;

import com.skhuthon.sweet_little_kitty.app.entity.region.Region;
import com.skhuthon.sweet_little_kitty.app.entity.region.RegionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByCategory(RegionCategory category);
}
