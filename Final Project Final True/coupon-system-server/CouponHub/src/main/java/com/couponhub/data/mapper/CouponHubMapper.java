package com.couponhub.data.mapper;

import java.util.Set;

import org.mapstruct.Mapper;

import com.couponhub.data.dto.CouponHubDto;
import com.couponhub.data.entity.CouponEntity;

@Mapper(componentModel = "spring")
public interface CouponHubMapper {
    CouponEntity toEntity(CouponHubDto couponHubDto);

    CouponHubDto toDto(CouponEntity couponEntity);

    Set<CouponEntity> toEntitySet(Set<CouponHubDto> couponHubDto);

    Set<CouponHubDto> toDtoSet(Set<CouponEntity> couponEntities);

}
