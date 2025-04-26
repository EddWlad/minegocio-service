package com.tidsec.minegocio_service.services;

import com.tidsec.minegocio_service.dtos.CustomerDTO;
import com.tidsec.minegocio_service.entities.Customer;

import java.util.UUID;

public interface ICustomerService extends IGenericService<Customer, UUID>{
    Customer saveCustomerWithMainAddress(CustomerDTO customerDTO) throws Exception;

    Customer findByIdentificationNumber(String identificationNumber) throws Exception;

    Customer findByName(String name) throws Exception;
}
