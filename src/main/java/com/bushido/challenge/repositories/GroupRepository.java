package com.bushido.challenge.repositories;

import com.bushido.challenge.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Optional<Group> findByName(String name);
}