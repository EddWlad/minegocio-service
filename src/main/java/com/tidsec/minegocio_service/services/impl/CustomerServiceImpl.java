package com.tidsec.minegocio_service.services.impl;

import com.tidsec.minegocio_service.entities.Customer;
import com.tidsec.minegocio_service.repositories.ICustomerRepository;
import com.tidsec.minegocio_service.repositories.IGenericRepository;
import com.tidsec.minegocio_service.services.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends GenericServiceImpl<Customer, UUID> implements ICustomerService {

    private final ICustomerRepository customerRepository;
    @Override
    protected IGenericRepository<Customer, UUID> getRepo() {
        return customerRepository;
    }
}
