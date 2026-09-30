package com.address.model.dto;

import com.address.model.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;
    private Long empId;
    private String street;
    private Long pinCode;
    private String city;
    private String country;


    @Enumerated(EnumType.STRING)
    private AddressType addressType;


}
