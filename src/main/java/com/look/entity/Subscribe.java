package com.look.entity;

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
@Table(name = "subscribe")
public class Subscribe {
    @Id
    private Integer id;
    private String mainAccount;
    private String followAccount;


    public Subscribe(String mainAccount, String followAccount) {
        this.mainAccount = mainAccount;
        this.followAccount = followAccount;
    }
}
