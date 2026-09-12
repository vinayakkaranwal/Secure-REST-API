package com.vinayak.secure_rest_api.controllers;

import com.vinayak.secure_rest_api.dto.TaskDTO;
import com.vinayak.secure_rest_api.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Validated
public class TaskController {

    private  final TaskService taskService;

    @GetMapping("/{id}")
    public List<TaskDTO> getTasks(
            @PathVariable
            @Min(value = 1, message = "User ID must be greater than or equal to 1")Long id){
        return taskService.getTasksByUser(id);
    }

    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO){

        return taskService.createTask(taskDTO);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@Valid @RequestBody TaskDTO taskDTO,
                              @PathVariable
                              @Min(value = 1, message = "User ID must be greater than or equal to 1")Long id,
                              Principal principal){

        return taskService.updateTask(taskDTO, id, principal.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteTask(
            @PathVariable
            @Min(value = 1, message = "User ID must be greater than or equal to 1") Long id,
            Authentication authentication){

        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        taskService.deleteTask(id, username, authorities);
        return "Task deleted successfully.";
    }


}
