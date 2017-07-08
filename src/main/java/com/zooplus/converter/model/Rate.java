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
    private String base;
    private String currency;
    private String exchange;
    private String date;
    private User user;

    public Rate(){ }
    public Rate(final String currency, final String base, final String exchange, final String date, final User user){
        super();
        this.currency = currency;
        this.exchange = exchange;
        this.user = user;
        this.date = date;
        this.base = base;
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

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
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

}
