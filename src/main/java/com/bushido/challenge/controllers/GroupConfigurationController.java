package com.bushido.challenge.controllers;

import com.bushido.challenge.dtos.GroupConfigurationCreateDTO;
import com.bushido.challenge.dtos.GroupConfigurationDTO;
import com.bushido.challenge.dtos.GroupConfigurationUpdateDTO;
import com.bushido.challenge.dtos.ModifyAccessDTO;
import com.bushido.challenge.services.GroupConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/configurations")
@RequiredArgsConstructor
@Validated
@Tag(name = "Group Configuration Management", description = "APIs for managing access rules (configurations) within groups.")
public class GroupConfigurationController {

    private final GroupConfigurationService groupConfigurationService;

    @Operation(summary = "Create a new group configuration", description = "Adds a new access rule (configuration) to a specific group.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Configuration created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupConfigurationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Parent Group not found for the provided groupId", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<GroupConfigurationDTO> createConfiguration(@Valid @RequestBody GroupConfigurationCreateDTO createDTO) {
        GroupConfigurationDTO createdConfig = groupConfigurationService.createConfiguration(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConfig);
    }

    @Operation(summary = "Get group configuration by ID", description = "Retrieves the details of a specific active group configuration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuration found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupConfigurationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Configuration not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GroupConfigurationDTO> getConfigurationById(
            @Parameter(description = "UUID of the configuration to retrieve", required = true, example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable UUID id) {
        GroupConfigurationDTO config = groupConfigurationService.getConfigurationById(id);
        return ResponseEntity.ok(config);
    }

    @Operation(summary = "Get all group configurations", description = "Retrieves a list of all active configurations, optionally filtered by group ID.")
    @ApiResponse(responseCode = "200", description = "List of configurations retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = GroupConfigurationDTO.class)))
    @GetMapping
    public ResponseEntity<List<GroupConfigurationDTO>> getAllConfigurations(
            @Parameter(description = "Optional UUID of the group to filter configurations for", required = false, example = "f1e2d3c4-b5a6-7890-1234-567890abcde")
            @RequestParam(required = false) UUID groupId) {
        List<GroupConfigurationDTO> configs;
        if (groupId != null) {
             configs = groupConfigurationService.getConfigurationsByGroupId(groupId);
        } else {
            configs = groupConfigurationService.getAllConfigurations();
        }
        return ResponseEntity.ok(configs);
    }

    @Operation(summary = "Update an existing configuration", description = "Updates the rule name and access type of an existing group configuration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuration updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupConfigurationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Configuration not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<GroupConfigurationDTO> updateConfiguration(
            @Parameter(description = "UUID of the configuration to update", required = true) @PathVariable UUID id,
            @Valid @RequestBody GroupConfigurationUpdateDTO updateDTO) {
        GroupConfigurationDTO updatedConfig = groupConfigurationService.updateConfiguration(id, updateDTO);
        return ResponseEntity.ok(updatedConfig);
    }

    @Operation(summary = "Delete a group configuration", description = "Logically deletes a configuration by its ID (marks as inactive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Configuration deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Configuration not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfiguration(
            @Parameter(description = "UUID of the configuration to delete", required = true) @PathVariable UUID id) {
        groupConfigurationService.deleteConfiguration(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modify access type of a configuration", description = "Updates only the access type (e.g., CREATE, EDIT, VIEW) of a specific configuration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access type updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupConfigurationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid access type provided", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Configuration not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{id}/access")
    public ResponseEntity<GroupConfigurationDTO> modifyAccess(
            @Parameter(description = "UUID of the configuration to modify", required = true) @PathVariable UUID id,
            @Valid @RequestBody ModifyAccessDTO modifyAccessDTO) {
         GroupConfigurationDTO updatedConfig = groupConfigurationService.modifyAccess(id, modifyAccessDTO.getAccess());
        return ResponseEntity.ok(updatedConfig);
    }
}