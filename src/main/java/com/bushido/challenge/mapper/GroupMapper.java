package com.bushido.challenge.mapper;

import com.bushido.challenge.dtos.GroupCreateDTO;
import com.bushido.challenge.dtos.GroupDTO;
import com.bushido.challenge.dtos.GroupUpdateDTO;
import com.bushido.challenge.entities.Group;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

     public static GroupDTO toGroupDTO(Group group) {
        if (group == null) {
            return null;
        }
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.isActive()
        );
    }

     public static List<GroupDTO> toGroupDTOs(List<Group> groups) {
        if (groups == null) {
            return Collections.emptyList();
        }
        return groups.stream()
                .map(GroupMapper::toGroupDTO)
                .collect(Collectors.toList());
    }

     public static Group toGroup(GroupCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        Group group = new Group();
        group.setName(dto.getName());
         return group;
    }

     public static void updateGroupFromDto(GroupUpdateDTO dto, Group group) {
        if (dto == null || group == null) {
            return;
        }
        group.setName(dto.getName());
     }
}
