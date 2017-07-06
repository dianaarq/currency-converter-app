package com.zooplus.converter.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by dianaarq on 04/07/2017.
 */
@Entity
@Table(name = "rate")
public class Rate {
    private Long id;
    String currency;
    String exchange;
    Timestamp timestamp;
    String date;
    User user;

    public Rate(){}
    public Rate(String currency, String exchange, Timestamp timestamp, String date, User user){
        super();
        this.currency = currency;
        this.exchange = exchange;
        this.user = user;
        this.date = date;
        this.timestamp = timestamp;
    }
    @ManyToOne
    @JoinColumn(name = "user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


}
