package com.address.controller;

import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;
import com.address.model.dto.AddressRequestDto;
import com.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {



    private  final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @PostMapping("/save")
     public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressRequest)
    {
         List<AddressDto> response = addressService.saveAddress(addressRequest);

         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<List<AddressDto>> updateArress(
            @RequestBody AddressRequest addressRequest,
            @PathVariable Long id) {
        List<AddressDto> response = addressService.updateAddress(addressRequest, id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @GetMapping("All")
    public ResponseEntity<List<AddressDto>> getAllAddress()
        {
            List<AddressDto>  response = addressService.getAllAddress();
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }


    @GetMapping("/getById/{id}")
    public ResponseEntity<AddressDto> getByIdAddress(@PathVariable Long id)
    {
        AddressDto response = addressService.getByIdAddress(id);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id)
    {
        addressService.deleteAddress(id);
        return  new ResponseEntity<>("Address Deleted Successfully...!!" ,HttpStatus.OK);
    }

    @GetMapping("/empId/{empId}")
    public ResponseEntity<List<AddressDto>> getAddressByEmpId(@PathVariable Long empId)
    {
        List<AddressDto> response = addressService.getAddressByEmpId(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
