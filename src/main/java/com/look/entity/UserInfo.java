package com.look.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userinfo")
public class UserInfo {
    @Id
    private String userAccount;
    private String userName;
    private String userImg;
    private Integer coins;
    private Integer fans;

    @Transient
    private List<Course> recentCourse;
}
