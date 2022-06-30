package ru.wpz.lessons.lesson1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="students")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;
}
