package com.bushido.challenge.services.impl;

import com.bushido.challenge.dtos.GroupCreateDTO;
import com.bushido.challenge.dtos.GroupDTO;
import com.bushido.challenge.dtos.GroupUpdateDTO;
import com.bushido.challenge.entities.Group;
import com.bushido.challenge.exceptions.ResourceNotFoundException;
import com.bushido.challenge.mapper.GroupMapper;
import com.bushido.challenge.repositories.GroupRepository;
import com.bushido.challenge.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public GroupDTO createGroup(GroupCreateDTO groupCreateDTO) {

         groupRepository.findByName(groupCreateDTO.getName()).ifPresent(g -> {
            throw new IllegalArgumentException("Group with name " + groupCreateDTO.getName() + " already exists.");
        });

        Group group = GroupMapper.toGroup(groupCreateDTO);
        Group savedGroup = groupRepository.save(group);
        return GroupMapper.toGroupDTO(savedGroup);
    }

    @Transactional(readOnly = true)
    @Override
    public GroupDTO getGroupById(UUID id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + id));
        return GroupMapper.toGroupDTO(group);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return GroupMapper.toGroupDTOs(groups);
    }

    @Override
    public GroupDTO updateGroup(UUID id, GroupUpdateDTO groupUpdateDTO) {
        Group existingGroup = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + id));

         groupRepository.findByName(groupUpdateDTO.getName()).ifPresent(g -> {
            if (!g.getId().equals(id)) {
                throw new IllegalArgumentException("Group name " + groupUpdateDTO.getName() + " is already in use by another group.");
            }
        });

        GroupMapper.updateGroupFromDto(groupUpdateDTO, existingGroup);
        Group updatedGroup = groupRepository.save(existingGroup);
        return GroupMapper.toGroupDTO(updatedGroup);
    }

    @Override
    public void deleteGroup(UUID id) {
        if (!groupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Group not found with id: " + id);
        }
        groupRepository.deleteById(id);
    }
}
