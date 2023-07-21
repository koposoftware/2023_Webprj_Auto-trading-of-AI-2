package com.kopo.trading.user.entity;


import com.kopo.trading.core.entity.Transaction;
import com.kopo.trading.user.constants.Social;
import com.kopo.trading.util.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.List;

@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Column(nullable = true)
    private String password;

    private Double balance = 10000000.0;

    @Column(nullable = false)
    private Social loginType;

    @OneToMany
    private List<Transaction> transactions;

    public Member(String emailAddress, String password, Social loginType) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.loginType = loginType;
    }

    public Member(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.loginType = Social.NONE;
    }

    public Member() {

    }

    public Long getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Social getLoginType() {
        return loginType;
    }

    public Double getBalance() { return balance;}

    public void setBalance(Double balance) {this.balance = balance;}

}
