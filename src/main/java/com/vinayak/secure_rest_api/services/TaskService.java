package com.vinayak.secure_rest_api.services;

import com.vinayak.secure_rest_api.dto.TaskDTO;
import com.vinayak.secure_rest_api.entities.Task;
import com.vinayak.secure_rest_api.entities.User;
import com.vinayak.secure_rest_api.execptions.ResourceNotFoundException;
import com.vinayak.secure_rest_api.repositories.TaskRepo;
import com.vinayak.secure_rest_api.repositories.UserRepositorie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepositorie userRepositorie;
    private final ModelMapper modelMapper;

    public TaskDTO createTask(TaskDTO taskDTO){
        User user = userRepositorie.findById(taskDTO.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + taskDTO.getUser_id()));

        Task task = modelMapper.map(taskDTO, Task.class);

        task.setUser(user);

        Task savedTask = taskRepo.save(task);

        return modelMapper.map(savedTask, TaskDTO.class);
    }

    public List<TaskDTO> getTasksByUser(Long id){
        Optional<User> user = userRepositorie.findById(id);

        return taskRepo.findByUser(user.orElse(null))
                .stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    public TaskDTO updateTask(TaskDTO taskDTO, Long id, String username){
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("USER ID NOT FOUND"));

        if(!task.getUser().getUsername().equals(username)){
            throw new RuntimeException("You are not the owner of this task");
        }

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());

        Task UpdatedTask = taskRepo.save(task);

        return modelMapper.map(UpdatedTask, TaskDTO.class);
    }

    public void deleteTask(Long id, String username, Collection<? extends GrantedAuthority> authorities) {

        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        boolean isOwner = task.getUser().getUsername().equals(username);
        boolean isAdmin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not allowed to delete this task");
        }

        taskRepo.delete(task);
    }
}
