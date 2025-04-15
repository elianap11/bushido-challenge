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
@Schema(description = "Data required to create a new user group.")
public class GroupCreateDTO {

    @Schema(description = "The unique name for the new group.",
            example = "Content Moderators",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Group name cannot be blank")
    private String name;
}
