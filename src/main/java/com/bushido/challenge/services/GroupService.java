package com.bushido.challenge.services;

import com.bushido.challenge.dtos.GroupCreateDTO;
import com.bushido.challenge.dtos.GroupDTO;
import com.bushido.challenge.dtos.GroupUpdateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    GroupDTO createGroup(GroupCreateDTO groupCreateDTO);

    @Transactional(readOnly = true)
    GroupDTO getGroupById(UUID id);

    @Transactional(readOnly = true)
    List<GroupDTO> getAllGroups();

    GroupDTO updateGroup(UUID id, GroupUpdateDTO groupUpdateDTO);

    void deleteGroup(UUID id);
}
