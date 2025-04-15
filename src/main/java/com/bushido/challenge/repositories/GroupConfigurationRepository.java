package com.bushido.challenge.repositories;

import com.bushido.challenge.entities.GroupConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupConfigurationRepository extends JpaRepository<GroupConfiguration, UUID> {

}
