package com.vinayak.secure_rest_api.repositories;

import com.vinayak.secure_rest_api.entities.Task;
import com.vinayak.secure_rest_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
