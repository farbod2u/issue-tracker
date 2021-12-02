package com.pinguinag.issuetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_bug")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bug {

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
    void defaultValues() {
        this.creationDate = LocalDateTime.now();
        if (this.priority == null)
            this.priority = PriorityType.Moinor;
        if (this.status == null)
            this.status = StatusType.New;
    }

    @ManyToOne
    @JoinColumn(name = "developerId")
    private Developer developer;

    @Enumerated(EnumType.STRING)
    private PriorityType priority;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    public static enum PriorityType {
        Critical,
        Major,
        Moinor
    }

    public static enum StatusType {
        New,
        Verified,
        Resolved
    }
}
