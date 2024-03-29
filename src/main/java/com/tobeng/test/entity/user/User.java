package com.tobeng.test.entity.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @Column(name = "t_id")
    @GeneratedValue
    private Long id;

    @Column(name = "t_name")
    private String name;

    @Column(name = "t_age")
    private int age;

    @Column(name = "t_address")
    private String address;

    @Column(name = "t_pwd")
    private String pwd;
}
