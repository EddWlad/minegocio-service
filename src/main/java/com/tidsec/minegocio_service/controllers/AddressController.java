package com.tidsec.minegocio_service.controllers;
import com.tidsec.minegocio_service.dtos.AddressDTO;
import com.tidsec.minegocio_service.dtos.CustomerDTO;
import com.tidsec.minegocio_service.entities.Address;
import com.tidsec.minegocio_service.entities.Customer;
import com.tidsec.minegocio_service.services.IAddressService;
import com.tidsec.minegocio_service.services.ICustomerService;
import com.tidsec.minegocio_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;
    private final ICustomerService customerService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() throws Exception{
        List<AddressDTO> list = mapperUtil.mapList(addressService.findAll(), AddressDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable("id") String id) throws Exception{
        AddressDTO AddressDTO = mapperUtil.map(addressService.findById(UUID.fromString(id)), AddressDTO.class);
        return ResponseEntity.ok(AddressDTO);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> save(@RequestBody AddressDTO addressDTO) throws Exception{
        Customer customer = customerService.findById(addressDTO.getCustomer().getIdCustomer());

        if (addressDTO.isMainAddress()) {
            List<Address> addressList = customer.getAddressList();

            boolean alreadyHasMainAddress = addressList.stream()
                    .anyMatch(Address::isMainAddress);

            if (alreadyHasMainAddress) {
                throw new IllegalArgumentException("The customer already has a main address registered.");
            }
        }

        Address obj = addressService.save(mapperUtil.map(addressDTO, Address.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdAddress())
                .toUri();

        return ResponseEntity.created(location).body(mapperUtil.map(obj, AddressDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable("id") String id, @RequestBody AddressDTO AddressDTO) throws Exception{
        Address obj = addressService.update(mapperUtil.map(AddressDTO, Address.class), UUID.fromString(id));
        return ResponseEntity.ok(mapperUtil.map(obj, AddressDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) throws Exception{
        addressService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
