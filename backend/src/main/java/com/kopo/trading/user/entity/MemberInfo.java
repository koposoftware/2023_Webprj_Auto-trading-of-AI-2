package com.kopo.trading.user.entity;

import com.kopo.trading.util.BaseTimeEntity;

import javax.persistence.*;

@Entity
public class MemberInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nickname;

    @OneToOne(cascade = CascadeType.ALL)
    private Member member;

    public MemberInfo(String nickname, Member member) {
        this.nickname = nickname;
        this.member = member;
    }

    public MemberInfo() {

    }

    public String getNickname() {
        return nickname;
    }
}
