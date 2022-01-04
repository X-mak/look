package com.look.entity;

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
@Table(name = "publish")
public class Publish {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String userAccount;
    private int courseId;
    private Date publishDate;
}
