package com.bushido.challenge.dtos;

import com.bushido.challenge.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data required to modify only the access type of a Group Configuration.")
public class ModifyAccessDTO {

    @Schema(description = "The new access level (CREATE, EDIT, or VIEW) to be set for the configuration.",
            example = "VIEW",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Access type cannot be null")
    private AccessType access;
}