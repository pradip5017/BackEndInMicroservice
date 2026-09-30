package com.address.model.entity;

import com.address.model.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long empId;
    private String street;
    private Long pinCode;
    private String city;
    private String country;


    @Enumerated(EnumType.STRING)
    private AddressType addressType;

}
