package com.tidsec.minegocio_service.services.impl;

import com.tidsec.minegocio_service.dtos.AddressDTO;
import com.tidsec.minegocio_service.dtos.CustomerDTO;
import com.tidsec.minegocio_service.entities.Address;
import com.tidsec.minegocio_service.entities.Customer;
import com.tidsec.minegocio_service.exceptions.ModelNotFoundException;
import com.tidsec.minegocio_service.repositories.ICustomerRepository;
import com.tidsec.minegocio_service.repositories.IGenericRepository;
import com.tidsec.minegocio_service.services.ICustomerService;
import com.tidsec.minegocio_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends GenericServiceImpl<Customer, UUID> implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final MapperUtil mapperUtil;
    @Override
    protected IGenericRepository<Customer, UUID> getRepo() {
        return customerRepository;
    }

    @Override
    @Transactional
    public Customer saveCustomerWithMainAddress(CustomerDTO customerDTO) throws Exception {

        if (customerDTO.getIdCustomer() != null) {
            Optional<Customer> optionalCustomer = customerRepository.findById(customerDTO.getIdCustomer());

            if (optionalCustomer.isPresent()) {
                Customer existingCustomer = optionalCustomer.get();
                if (existingCustomer.getStatus() == 1 || existingCustomer.getStatus() == 2) {
                    throw new ModelNotFoundException("Customer already exist");
                }
            }
        }

        if (customerDTO.getAddress() == null || customerDTO.getAddress().stream().noneMatch(AddressDTO::isMainAddress)) {
            throw new IllegalArgumentException("\n" +
                    "There must be at least one main address.");
        }

        Customer customer = mapperUtil.map(customerDTO, Customer.class);

        if (customer.getAddressList() != null) {
            for (Address address : customer.getAddressList()) {
                address.setCustomer(customer);
            }
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByIdentificationNumber(String identificationNumber) throws Exception {
        return customerRepository.findByIdentificationNumber(identificationNumber);
    }

    @Override
    public Customer findByName(String name) throws Exception {
        return customerRepository.findByName(name);
    }
}
