package com.skhuthon.sweet_little_kitty.app.entity.region;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum RegionCategory {

    INCHEON("INCHEON", "인천"),
    SEOUL("SEOUL", "서울"),
    GYEONGGI_DO("GYEONGGI_DO", "경기도"),
    GANGWON_DO("GANGWON_DO", "강원도"),
    CHUNGBUK_DO("CHUNGBUK_DO", "충청북도"),
    CHUNGNAM_DO("CHUNGNAM_DO", "충청남도"),
    DAEJEON("DAEJEON", "대전"),
    JEONBUK_DO("JEONBUK_DO", "전라북도"),
    JEONNAM_DO("JEONNAM_DO", "전라남도"),
    GWANGJU("GWANGJU", "광주"),
    GYEONGBUK_DO("GYEONGBUK_DO", "경상북도"),
    GYEONGNAM_DO("GYEONGNAM_DO", "경상남도"),
    DAEGU("DAEGU", "대구"),
    ULSAN("ULSAN", "울산"),
    BUSAN("BUSAN", "부산"),
    JEJU_DO("JEJU_DO", "제주도"),
    ULLEUNG_DO("ULLEUNG_DO", "울릉도"),
    DOKDO("DOKDO", "독도");

    private final String code;
    private final String displayName;

    public static RegionCategory convertToCategory(String categoryStr) {
        for (RegionCategory category : RegionCategory.values()) {
            if (category.code.equals(categoryStr)) {
                return category;
            }
        }
        // TODO CustomException
        throw new NoSuchElementException();
    }

    public static List<RegionCategory> convertToCategoryList(List<String> categoryList) {
        return categoryList.stream()
                .map(RegionCategory::convertToCategory)
                .collect(Collectors.toList());
    }
}
