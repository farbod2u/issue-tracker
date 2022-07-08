package com.pinguinag.issuetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_developer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldid")
    private Integer id;

    @Column(name = "fldname", nullable = false)
    @NotNull(message = "Developer Name must has values.")
    private String name;
}
