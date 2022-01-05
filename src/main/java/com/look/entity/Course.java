package com.look.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String courseName;
    private String courseVideo;
    private String courseImg;
    private int status;
}
