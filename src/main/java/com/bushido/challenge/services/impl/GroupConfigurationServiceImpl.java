package com.bushido.challenge.services.impl;

import com.bushido.challenge.dtos.GroupConfigurationCreateDTO;
import com.bushido.challenge.dtos.GroupConfigurationDTO;
import com.bushido.challenge.dtos.GroupConfigurationUpdateDTO;
import com.bushido.challenge.dtos.ModifyAccessDTO;
import com.bushido.challenge.entities.Group;
import com.bushido.challenge.entities.GroupConfiguration;
import com.bushido.challenge.enums.AccessType;
import com.bushido.challenge.exceptions.ResourceNotFoundException;
import com.bushido.challenge.mapper.GroupConfigurationMapper;
import com.bushido.challenge.repositories.GroupConfigurationRepository;
import com.bushido.challenge.repositories.GroupRepository;
import com.bushido.challenge.services.GroupConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupConfigurationServiceImpl implements GroupConfigurationService {

    private final GroupConfigurationRepository groupConfigurationRepository;
    private final GroupRepository groupRepository;

    @Override
    public GroupConfigurationDTO createConfiguration(GroupConfigurationCreateDTO createDTO) {

        Group parentGroup = groupRepository.findById(createDTO.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot create configuration: Group not found with id: " + createDTO.getGroupId()));

         GroupConfiguration newConfig = GroupConfigurationMapper.toGroupConfiguration(createDTO);

         newConfig.setGroup(parentGroup);

         GroupConfiguration savedConfig = groupConfigurationRepository.save(newConfig);

         return GroupConfigurationMapper.toGroupConfigurationDTO(savedConfig);
    }

    @Transactional(readOnly = true)
    @Override
    public GroupConfigurationDTO getConfigurationById(UUID id) {
        GroupConfiguration config = groupConfigurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupConfiguration not found with id: " + id));
        return GroupConfigurationMapper.toGroupConfigurationDTO(config);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupConfigurationDTO> getAllConfigurations() {
        List<GroupConfiguration> configs = groupConfigurationRepository.findAll();
        return GroupConfigurationMapper.toGroupConfigurationDTOs(configs);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupConfigurationDTO> getConfigurationsByGroupId(UUID groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id: " + groupId);
        }

        List<GroupConfiguration> allConfigs = groupConfigurationRepository.findAll();
        List<GroupConfiguration> groupConfigs = allConfigs.stream()
                .filter(config -> config.getGroup() != null && config.getGroup().getId().equals(groupId))
                .toList();
        return GroupConfigurationMapper.toGroupConfigurationDTOs(groupConfigs);
    }


    @Override
    public GroupConfigurationDTO updateConfiguration(UUID id, GroupConfigurationUpdateDTO updateDTO) {
        GroupConfiguration existingConfig = groupConfigurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupConfiguration not found with id: " + id));

         GroupConfigurationMapper.updateGroupConfigurationFromDto(updateDTO, existingConfig);

        GroupConfiguration updatedConfig = groupConfigurationRepository.save(existingConfig);
        return GroupConfigurationMapper.toGroupConfigurationDTO(updatedConfig); // Usa mapper manual
    }

    @Override
    public void deleteConfiguration(UUID id) {
        if (!groupConfigurationRepository.existsById(id)) {
            throw new ResourceNotFoundException("GroupConfiguration not found with id: " + id);
        }
        groupConfigurationRepository.deleteById(id);
    }

    @Override
    public GroupConfigurationDTO modifyAccess(UUID id, AccessType newAccess) {
        GroupConfiguration existingConfig = groupConfigurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupConfiguration not found with id: " + id));

        existingConfig.setAccess(newAccess);
        GroupConfiguration updatedConfig = groupConfigurationRepository.save(existingConfig);

        return GroupConfigurationMapper.toGroupConfigurationDTO(updatedConfig);
    }

    @Override
    public GroupConfigurationDTO modifyAccess(UUID id, ModifyAccessDTO dto) {
        return modifyAccess(id, dto.getAccess());
    }
}