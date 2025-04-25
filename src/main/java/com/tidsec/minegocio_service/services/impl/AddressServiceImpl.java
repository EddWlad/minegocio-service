package com.tidsec.minegocio_service.services.impl;

import com.tidsec.minegocio_service.entities.Address;
import com.tidsec.minegocio_service.repositories.IAddressRepository;
import com.tidsec.minegocio_service.repositories.IGenericRepository;
import com.tidsec.minegocio_service.services.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl extends GenericServiceImpl<Address, UUID> implements IAddressService {

    private final IAddressRepository addressRepository;
    @Override
    protected IGenericRepository<Address, UUID> getRepo() {
        return addressRepository;
    }
}
