package com.bushido.challenge.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data required to assign or remove groups from a user")
public class AssignGroupsDTO {

    @Schema(description = "A list of Group UUIDs to be assigned or removed.",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "[\"123e4567-e89b-12d3-a456-426614174001\", \"123e4567-e89b-12d3-a456-426614174002\"]")
    @NotEmpty(message = "Group IDs list cannot be empty")
    private List<UUID> groupIds;
}
