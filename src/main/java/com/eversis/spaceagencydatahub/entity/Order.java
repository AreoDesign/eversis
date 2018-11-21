package com.eversis.spaceagencydatahub.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "order_table") // Table name cannot be just "order" as ORDER is service word in any sql
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_login", nullable = false) // TODO: 2018-11-20 implement method getClientOrders
    private Customer customer;

    // TODO: 2018-11-20 add fields
    //STATUS
    //CREATION DATE
    //@OneToOne ORDER DETAILS

//
//    @Column(name = "user_name")
//    private String userName;

    // TODO: 2018-11-20 use this annotations for order details
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
}
