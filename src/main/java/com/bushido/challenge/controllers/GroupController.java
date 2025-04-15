package com.bushido.challenge.controllers;

import com.bushido.challenge.dtos.GroupCreateDTO;
import com.bushido.challenge.dtos.GroupDTO;
import com.bushido.challenge.dtos.GroupUpdateDTO;
import com.bushido.challenge.services.GroupService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Tag(name = "Group Management", description = "APIs for managing user groups.")
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "Create a new group", description = "Creates a new user group.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group created successfully", content = @Content(schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input (e.g., missing name)", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Group with the provided name already exists", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupCreateDTO groupCreateDTO) {
        GroupDTO createdGroup = groupService.createGroup(groupCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @Operation(summary = "Get group by ID", description = "Retrieves the details of a specific active group.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group found", content = @Content(schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(
            @Parameter(description = "UUID of the group to retrieve", required = true) @PathVariable UUID id) {
        GroupDTO group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    @Operation(summary = "Get all groups", description = "Retrieves a list of all active groups.")
    @ApiResponse(responseCode = "200", description = "List of groups retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = GroupDTO.class)))
    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @Operation(summary = "Update an existing group", description = "Updates the name of an existing group.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group updated successfully", content = @Content(schema = @Schema(implementation = GroupDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input (e.g., missing name)", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Updated group name already in use by another group", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(
            @Parameter(description = "UUID of the group to update", required = true) @PathVariable UUID id,
            @Valid @RequestBody GroupUpdateDTO groupUpdateDTO) {
        GroupDTO updatedGroup = groupService.updateGroup(id, groupUpdateDTO);
        return ResponseEntity.ok(updatedGroup);
    }

    @Operation(summary = "Delete a group", description = "Logically deletes a group by its ID (marks as inactive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group deleted successfully (marked as inactive)"),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(
            @Parameter(description = "UUID of the group to delete", required = true) @PathVariable UUID id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}