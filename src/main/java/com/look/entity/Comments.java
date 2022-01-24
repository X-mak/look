package com.look.entity;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    @Transient
    private boolean own;

    public Comments(String userAccount, Integer courseId, Integer star, String context) {
        this.userAccount = userAccount;
        this.courseId = courseId;
        this.star = star;
        this.context = context;
        this.date = DateUtil.now();
        this.hot = 0;
    }

    public void setOwn(boolean own) {
        this.own = own;
    }
}
