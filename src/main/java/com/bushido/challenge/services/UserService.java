package com.bushido.challenge.services;

import com.bushido.challenge.dtos.UserCreateDTO;
import com.bushido.challenge.dtos.UserDTO;
import com.bushido.challenge.dtos.UserUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserCreateDTO userCreateDTO);

    UserDTO getUserById(UUID id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO);

    void deleteUser(UUID id);

    void assignGroupsToUser(UUID userId, List<UUID> groupIds);

    void removeGroupsFromUser(UUID userId, List<UUID> groupIds);

}