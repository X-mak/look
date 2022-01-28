package com.look.entity;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publish")
public class Publish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userAccount;
    private Integer courseId;
    private String publishDate;
    @Transient
    private Course course;

    public Publish(String userAccount, Integer courseId) {
        this.userAccount = userAccount;
        this.courseId = courseId;
        this.publishDate = DateUtil.now();
    }
}
