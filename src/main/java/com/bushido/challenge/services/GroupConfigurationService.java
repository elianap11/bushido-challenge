package com.bushido.challenge.services;

import com.bushido.challenge.dtos.GroupConfigurationCreateDTO;
import com.bushido.challenge.dtos.GroupConfigurationDTO;
import com.bushido.challenge.dtos.GroupConfigurationUpdateDTO;
import com.bushido.challenge.dtos.ModifyAccessDTO;
import com.bushido.challenge.enums.AccessType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface GroupConfigurationService {
    GroupConfigurationDTO createConfiguration(GroupConfigurationCreateDTO createDTO);

    @Transactional(readOnly = true)
    GroupConfigurationDTO getConfigurationById(UUID id);

    @Transactional(readOnly = true)
    List<GroupConfigurationDTO> getAllConfigurations();

    @Transactional(readOnly = true)
    List<GroupConfigurationDTO> getConfigurationsByGroupId(UUID groupId);

    GroupConfigurationDTO updateConfiguration(UUID id, GroupConfigurationUpdateDTO updateDTO);

    void deleteConfiguration(UUID id);

    GroupConfigurationDTO modifyAccess(UUID id, AccessType newAccess);

    GroupConfigurationDTO modifyAccess(UUID id, ModifyAccessDTO dto);
}
