package com.bushido.challenge.services.impl;

import com.bushido.challenge.dtos.UserCreateDTO;
import com.bushido.challenge.dtos.UserDTO;
import com.bushido.challenge.dtos.UserUpdateDTO;
import com.bushido.challenge.entities.Group;
import com.bushido.challenge.entities.User;
import com.bushido.challenge.exceptions.ResourceNotFoundException;
import com.bushido.challenge.mapper.UserMapper;
import com.bushido.challenge.repositories.GroupRepository;
import com.bushido.challenge.repositories.UserRepository;
import com.bushido.challenge.services.UserService;
import com.bushido.challenge.services.audit.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AuditService auditService;

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
         userRepository.findByEmail(userCreateDTO.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("User with email " + userCreateDTO.getEmail() + " already exists.");
        });

        User user = UserMapper.toUser(userCreateDTO);
        User savedUser = userRepository.save(user);

         try {
            auditService.logUserActivity(savedUser.getId(), "CREATE");
            log.debug("Dispatched CREATE audit log for User ID: {}", savedUser.getId());
        } catch (Exception e) {
             log.error("Error dispatching audit log for CREATE User ID {}: {}", savedUser.getId(), e.getMessage());
         }
        return UserMapper.toUserDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserMapper.toUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toUserDTOs(users);
    }

    @Override
    public UserDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

         userRepository.findByEmail(userUpdateDTO.getEmail()).ifPresent(u -> {
            if (!u.getId().equals(id)) {
                throw new IllegalArgumentException("Email " + userUpdateDTO.getEmail() + " is already in use by another user.");
            }
        });

        UserMapper.updateUserFromDto(userUpdateDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);

         try {
            auditService.logUserActivity(updatedUser.getId(), "UPDATE");
            log.debug("Dispatched UPDATE audit log for User ID: {}", updatedUser.getId());
        } catch (Exception e) {
            log.error("Error dispatching audit log for UPDATE User ID {}: {}", updatedUser.getId(), e.getMessage());
        }
        return UserMapper.toUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
         if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);

         try {
            auditService.logUserActivity(id, "DELETE");
            log.debug("Dispatched DELETE audit log for User ID: {}", id);
        } catch (Exception e) {
            log.error("Error dispatching audit log for DELETE User ID {}: {}", id, e.getMessage());

        }
     }

    @Override
    public void assignGroupsToUser(UUID userId, List<UUID> groupIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

         Set<Group> groupsToAdd = groupIds.stream()
                .map(groupId -> groupRepository.findById(groupId)
                        .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId)))
                .collect(Collectors.toSet());

        user.getGroups().addAll(groupsToAdd);

        userRepository.save(user);
    }

    @Override
    public void removeGroupsFromUser(UUID userId, List<UUID> groupIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

         user.getGroups().removeIf(group -> groupIds.contains(group.getId()));

        userRepository.save(user);
    }
}