package com.kopo.trading.core.entity;

import com.kopo.trading.util.BaseTimeEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MachinePredict extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String code;

    @Column
    private String name;
    @Column
    private Double prevClose;
    @Column
    private Double close;
    @Column
    private Double predClose;

    @Column
    private Double diff;

    public MachinePredict(String code, String name, Double prevClose, Double close, Double predClose, Double diff) {
        this.code = code;
        this.name = name;
        this.prevClose = prevClose;
        this.close = close;
        this.predClose = predClose;
        this.diff = diff;
    }

    public MachinePredict() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(Double prevClose) {
        this.prevClose = prevClose;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getPredClose() {
        return predClose;
    }

    public void setPredClose(Double predClose) {
        this.predClose = predClose;
    }

    public Double getDiff() {
        return diff;
    }

    public void setDiff(Double diff) {
        this.diff = diff;
    }
}
