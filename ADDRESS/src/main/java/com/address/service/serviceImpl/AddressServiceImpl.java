package com.address.service.serviceImpl;

import com.address.client.EmployeeClient;
import com.address.exception.ResourceNotFoundException;
import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;
import com.address.model.dto.AddressRequestDto;
import com.address.model.dto.EmployeeDto;
import com.address.model.entity.Address;
import com.address.repository.AddressRepository;
import com.address.service.AddressService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {


    Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    private final EmployeeClient employeeClient;


    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper, EmployeeClient employeeClient) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.employeeClient = employeeClient;

    }

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        //toodo : check if employee exist
        employeeClient.getByIdEmployee(addressRequest.getEmpId());
        List<Address> listToSave = this.saveAndUpdateAddressRequest(addressRequest);
        List<Address> savedAddress = addressRepository.saveAll(listToSave);

        return savedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> all = addressRepository.findAll();
        if (all.isEmpty()) {
            throw new ResourceNotFoundException("Not Access Found..!!");
        }
        return all.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();

    }

    @Override
    public AddressDto getByIdAddress(Long id) {

        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address id get With Id :" + id));
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAddressByEmpId(Long empId) {
        List<Address> addresses = addressRepository.findAllByEmpId(empId);
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }


    @Override
    public List<AddressDto> updateAddress(AddressRequest addressRequest, Long id) {

        employeeClient.getByIdEmployee(addressRequest.getEmpId());
        List<Address> addressByEmpId = addressRepository.findAllByEmpId((addressRequest.getEmpId()));
        if (addressByEmpId.isEmpty()) {
            log.info("No address found for employee id {}",
                    addressRequest.getEmpId()
            );

            log.info("Creating new address for employee id {}",
                    addressRequest.getEmpId()
            );
        }
        List<Address> listToUpdate = this.saveAndUpdateAddressRequest(addressRequest);
        List<Long> upcomingNotNullIds = listToUpdate.stream()
                .map(Address::getId)
                .filter(Objects::nonNull)
                .toList();
        List<Long> existingIds = addressByEmpId.stream()
                .map(Address::getId)
                .toList();
        List<Long> idsToDelete = existingIds.stream()
                .filter(existingId -> !upcomingNotNullIds.contains(existingId))
                .toList();
        if (!idsToDelete.isEmpty()) {
            addressRepository.deleteAllById(idsToDelete);
        }
        List<Address> updatedAddresses =
                addressRepository.saveAll(listToUpdate);
        return updatedAddresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }

    @Override
    public void deleteAddress(Long id) {

        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address delete id With Id :" + id));
        addressRepository.delete(address);

    }


    private List<Address> saveAndUpdateAddressRequest(AddressRequest addressRequest) {


        List<Address> listToSave = new ArrayList<>();

        for (AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()) {
            Address address = new Address();
            address.setId(addressRequestDto.getId() != null ? addressRequestDto.getId() : null);

            address.setStreet(addressRequestDto.getStreet());
            address.setPinCode(addressRequestDto.getPinCode());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setEmpId(addressRequest.getEmpId());

            listToSave.add(address);
        }
        return listToSave;

    }


}
