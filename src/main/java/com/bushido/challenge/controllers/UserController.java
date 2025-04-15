package com.bushido.challenge.controllers;

import com.bushido.challenge.dtos.AssignGroupsDTO;
import com.bushido.challenge.dtos.UserCreateDTO;
import com.bushido.challenge.dtos.UserDTO;
import com.bushido.challenge.dtos.UserUpdateDTO;
import com.bushido.challenge.services.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for creating, retrieving, updating, and deleting users and managing their group assignments.")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Registers a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., invalid email format, missing name)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "User with the provided email already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserDTO createdUser = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves the details of a specific active user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "UUID of the user to retrieve", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all active users.")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = UserDTO.class))) // Especifica array de UserDTO
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Update an existing user", description = "Updates the name and email of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Updated email already in use by another user", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "UUID of the user to update", required = true) @PathVariable UUID id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user", description = "Logically deletes a user by their ID (marks as inactive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully (marked as inactive)"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "UUID of the user to delete", required = true) @PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign groups to a user", description = "Assigns one or more existing groups to a specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Groups assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., empty group list in request body)", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User or one of the Groups not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/{userId}/groups")
    public ResponseEntity<Void> assignGroups(
            @Parameter(description = "UUID of the user", required = true) @PathVariable UUID userId,
            @Valid @RequestBody AssignGroupsDTO assignGroupsDTO) {
        userService.assignGroupsToUser(userId, assignGroupsDTO.getGroupIds());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove groups from a user", description = "Removes one or more groups from a specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Groups removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., empty group list in request body)", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{userId}/groups")
    public ResponseEntity<Void> removeGroups(
            @Parameter(description = "UUID of the user", required = true) @PathVariable UUID userId,
            @Valid @RequestBody AssignGroupsDTO assignGroupsDTO) {
        userService.removeGroupsFromUser(userId, assignGroupsDTO.getGroupIds());
        return ResponseEntity.noContent().build();
    }
}