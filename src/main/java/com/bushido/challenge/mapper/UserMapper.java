package com.bushido.challenge.mapper;

import com.bushido.challenge.dtos.UserCreateDTO;
import com.bushido.challenge.dtos.UserDTO;
import com.bushido.challenge.dtos.UserUpdateDTO;
import com.bushido.challenge.entities.Group;
import com.bushido.challenge.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapper {

     public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

         Set<UUID> groupIds = (user.getGroups() == null) ? Collections.emptySet() :
                user.getGroups().stream()
                        .map(Group::getId)
                        .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                groupIds
        );
    }

     public static List<UserDTO> toUserDTOs(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

     public static User toUser(UserCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
         return user;
    }

     public static void updateUserFromDto(UserUpdateDTO dto, User user) {
        if (dto == null || user == null) {
            return;
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
    }
}

