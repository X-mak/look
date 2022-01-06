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
@Table(name = "signrecord")
public class SignRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userAccount;
    private String date;

    public SignRecord(String userAccount) {
        this.userAccount = userAccount;
        this.date = DateUtil.today();
    }
}
