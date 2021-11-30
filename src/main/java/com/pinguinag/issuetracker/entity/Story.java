package com.pinguinag.issuetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_story")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @Column(nullable = false)
    @NotNull(message = "Story Title must has values.")
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @PrePersist
    void defaultValues(){
        //this.creationDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "developerId")
    private Developer developer;

    @Enumerated(EnumType.STRING)
    private EstimatedPointType estimatedPoint;

    public static enum EstimatedPointType {
        New,
        Estimated,
        Completed
    }
}
