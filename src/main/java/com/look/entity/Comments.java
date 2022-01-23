package com.look.entity;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comments {
    @Id
    private Integer id;
    private String userAccount;
    private Integer courseId;
    private Integer star;
    private String context;
    private String date;
    private Integer hot;

    public Comments(String userAccount, Integer courseId, Integer star, String context) {
        this.userAccount = userAccount;
        this.courseId = courseId;
        this.star = star;
        this.context = context;
        this.date = DateUtil.now();
        this.hot = 0;
    }
}
