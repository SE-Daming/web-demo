package com.ideu.demo.server08.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "user_feedback")
public class UserFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "feedback_type")
    private String feedbackType;

    @Column(name = "feedback_text")
    private String feedbackText;

    @Column(name = "feedback_time")
    private Timestamp feedbackTime;


}
