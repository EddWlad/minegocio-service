package com.tidsec.minegocio_service.repositories;

import com.tidsec.minegocio_service.entities.Customer;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICustomerRepository extends IGenericRepository<Customer, UUID>{
    Customer findByName(String name);
    Customer findByIdentificationNumber(String identificationNumber);
}
