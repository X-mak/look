package com.look.entity;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "buy")
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userAccount;
    private Integer courseId;
    private String date;
    @Transient
    private Course course;
    public Buy(String userAccount, Integer courseId) {
        this.userAccount = userAccount;
        this.courseId = courseId;
        this.date = DateUtil.now();
    }
}
