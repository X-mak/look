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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String courseName;
    private String courseVideo;
    private String courseImg;
    private Integer status;
    private Integer clicks;
    private Integer cost;
    @Transient
    private CourseClass courseClass;
    @Transient
    private UserInfo userInfo;
    @Transient
    private Double ranks;
    @Transient
    private String userName;
    @Transient
    private String publishDate;
    @Transient
    private String age;

    public Course(String courseName, String courseVideo, String courseImg) {
        this.courseName = courseName;
        this.courseVideo = courseVideo;
        this.courseImg = courseImg;
    }

    public void setRanks(Double ranks) {
        this.ranks = ranks;
    }

}
