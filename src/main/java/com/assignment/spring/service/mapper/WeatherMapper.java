package com.assignment.spring.service.mapper;

import com.assignment.spring.domain.Weather;
import com.assignment.spring.service.dto.WeatherDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ControlType and its DTO ControlTypeDTO.
 */
@Mapper(componentModel = "spring")
public interface WeatherMapper extends EntityMapper<WeatherDTO, Weather> {
}
