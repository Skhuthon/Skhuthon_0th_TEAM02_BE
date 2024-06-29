package com.skhuthon.sweet_little_kitty.global.config;

import com.skhuthon.sweet_little_kitty.app.entity.region.Region;
import com.skhuthon.sweet_little_kitty.app.entity.region.RegionCategory;
import com.skhuthon.sweet_little_kitty.app.repository.region.RegionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DataInit {

    private final RegionRepository regionRepository;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            for (RegionCategory category : RegionCategory.values()) {
                regionRepository.findByCategory(category)
                        .orElseGet(() -> regionRepository.save(Region.builder().category(category).build()));
            }
        };
    }
}
