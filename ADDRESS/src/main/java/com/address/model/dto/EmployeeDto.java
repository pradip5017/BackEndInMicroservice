package com.address.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {


    private Long id;

    private String empName;
    private String empEmail;
    private String empCode;
    private String companyName;



}
