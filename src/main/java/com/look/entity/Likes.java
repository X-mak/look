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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class Likes {
    @Id
    private Integer id;
    private Integer commentId;
    private String userAccount;
    private String date;

    public Likes(Integer commentId, String userAccount) {
        this.commentId = commentId;
        this.userAccount = userAccount;
        this.date = DateUtil.now();
    }
}
