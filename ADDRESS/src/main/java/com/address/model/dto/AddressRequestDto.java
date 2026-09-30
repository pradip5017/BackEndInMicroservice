package com.address.model.dto;

import com.address.model.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {

    private Long id;
    private String street;
    private Long pinCode;
    private String city;
    private String country;


    private AddressType addressType;


}
