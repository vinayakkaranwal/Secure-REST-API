package com.vinayak.secure_rest_api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Optional;

@Entity
@Data
@Table(name = "Tasks")
public class Task extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    private String description;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
