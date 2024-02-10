package com.customerconnect.data.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.customerconnect.data.dto.CustomerDto;
import com.customerconnect.data.entity.CustomerEntity;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
  CustomerEntity toEntity(CustomerDto dto);

  CustomerDto toDto(CustomerEntity entity);

  List<CustomerDto> toDtoList(List<CustomerEntity> entityList);

  List<CustomerEntity> toEntityList(List<CustomerDto> dtoList);
}
