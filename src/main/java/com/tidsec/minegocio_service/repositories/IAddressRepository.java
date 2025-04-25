package com.tidsec.minegocio_service.repositories;

import com.tidsec.minegocio_service.entities.Address;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAddressRepository extends IGenericRepository<Address, UUID> {

}
