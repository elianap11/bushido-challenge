package com.bushido.challenge.dtos;

import com.bushido.challenge.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a Group Configuration (access rule) within a group")
public class GroupConfigurationDTO {

    @Schema(description = "Unique identifier of the Configuration", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Name of the rule this configuration applies to", example = "Manage Users")
    private String ruleName;

    @Schema(description = "Access level granted by this configuration", example = "EDIT")
    private AccessType access;

    @Schema(description = "UUID of the Group this configuration belongs to", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID groupId;

    @Schema(description = "Indicates if the configuration is active", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean active;

}
