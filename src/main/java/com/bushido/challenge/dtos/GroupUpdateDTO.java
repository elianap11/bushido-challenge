package com.bushido.challenge.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data required to update the name of an existing user group.")
public class GroupUpdateDTO {

    @Schema(description = "The new unique name for the group.",
            example = "Global Administrators",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Group name cannot be blank")
    private String name;
}