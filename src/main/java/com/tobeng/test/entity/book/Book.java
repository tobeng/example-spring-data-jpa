package com.tobeng.test.entity.book;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "b_id")
    @GeneratedValue
    private Long id;

    @Column(name = "b_name")
    private String name;
}
