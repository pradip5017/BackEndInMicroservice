package com.address.service;

import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;
import com.address.model.entity.Address;

import java.util.List;

public interface AddressService {

    List<AddressDto> saveAddress(AddressRequest addressRequest);

    List<AddressDto> getAllAddress();

    AddressDto getByIdAddress(Long id);

    List<AddressDto> getAddressByEmpId(Long empId);

    List<AddressDto> updateAddress(AddressRequest addressRequest, Long id);

    void deleteAddress(Long id);



}
