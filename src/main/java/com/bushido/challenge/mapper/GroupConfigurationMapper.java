package com.bushido.challenge.mapper;

import com.bushido.challenge.dtos.GroupConfigurationCreateDTO;
import com.bushido.challenge.dtos.GroupConfigurationDTO;
import com.bushido.challenge.dtos.GroupConfigurationUpdateDTO;
import com.bushido.challenge.entities.GroupConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GroupConfigurationMapper {

     public static GroupConfigurationDTO toGroupConfigurationDTO(GroupConfiguration config) {
        if (config == null) {
            return null;
        }
         UUID groupId = (config.getGroup() != null) ? config.getGroup().getId() : null;

        return new GroupConfigurationDTO(
                config.getId(),
                config.getRuleName(),
                config.getAccess(),
                groupId,
                config.isActive()
        );
    }

     public static List<GroupConfigurationDTO> toGroupConfigurationDTOs(List<GroupConfiguration> configs) {
        if (configs == null) {
            return Collections.emptyList();
        }
        return configs.stream()
                .map(GroupConfigurationMapper::toGroupConfigurationDTO)
                .collect(Collectors.toList());
    }

     public static GroupConfiguration toGroupConfiguration(GroupConfigurationCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        GroupConfiguration config = new GroupConfiguration();
        config.setRuleName(dto.getRuleName());
        config.setAccess(dto.getAccess());
        return config;
    }

     public static void updateGroupConfigurationFromDto(GroupConfigurationUpdateDTO dto, GroupConfiguration config) {
        if (dto == null || config == null) {
            return;
        }
        config.setRuleName(dto.getRuleName());
        config.setAccess(dto.getAccess());
     }
}
