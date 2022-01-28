package com.look.entity;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userAccount;
    private Integer courseId;
    private String date;

    public History(String userAccount, Integer courseId) {
        this.userAccount = userAccount;
        this.courseId = courseId;
        this.date = DateUtil.now();
    }
}
