package com.bushido.challenge.dtos;

import com.bushido.challenge.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data required to update an existing Group Configuration (rule name and access type). The group association cannot be changed.")
public class GroupConfigurationUpdateDTO {

    @Schema(description = "The updated name for the rule.",
            example = "User Profile Management",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Rule name cannot be blank")
    private String ruleName;

    @Schema(description = "The updated access level (CREATE, EDIT, or VIEW).",
            example = "VIEW",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Access type cannot be null")
    private AccessType access;
}
