package com.bushido.challenge.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a User in the system (API response model)")
public class UserDTO {

    @Schema(description = "Unique identifier of the User (UUID)", example = "123e4567-e89b-12d3-a456-426614174000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "User's full name", example = "Alice Smith")
    private String name;

    @Schema(description = "User's unique email address", example = "alice.smith@example.com")
    private String email;

    @Schema(description = "Indicates if the user account is active", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean active;

    @Schema(description = "Set of UUIDs of the groups the user belongs to", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<UUID> groupIds;
}