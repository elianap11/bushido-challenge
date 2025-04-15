package com.bushido.challenge.dtos;

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
@Schema(description = "Represents a User Group in the system (API response model)")
public class GroupDTO {

    @Schema(description = "Unique identifier of the Group (UUID)",
            example = "f1e2d3c4-b5a6-7890-1234-567890abcde",
            accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "The unique name of the group",
            example = "Administrators")
    private String name;

    @Schema(description = "Indicates if the group is active (used for logical delete)",
            example = "true",
            accessMode = Schema.AccessMode.READ_ONLY)
    private boolean active;
}
