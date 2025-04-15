package com.bushido.challenge.dtos;

import com.bushido.challenge.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data required to create a new Group Configuration (access rule)")
public class GroupConfigurationCreateDTO {

    @Schema(description = "Name of the rule this configuration applies to (e.g., a feature or section name)",
            example = "User Management",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Rule name cannot be blank")
    private String ruleName;

    @Schema(description = "Access level to be granted by this configuration (CREATE, EDIT, or VIEW)",
            example = "EDIT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Access type cannot be null")
    private AccessType access;

    @Schema(description = "UUID of the Group to which this configuration will belong",
            example = "f1e2d3c4-b5a6-7890-1234-567890abcde",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Group ID cannot be null")
    private UUID groupId;
}
