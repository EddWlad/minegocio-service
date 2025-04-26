package com.tidsec.minegocio_service.controllers;
import com.tidsec.minegocio_service.dtos.AddressDTO;
import com.tidsec.minegocio_service.dtos.CustomerDTO;
import com.tidsec.minegocio_service.entities.Address;
import com.tidsec.minegocio_service.entities.Customer;
import com.tidsec.minegocio_service.services.IAddressService;
import com.tidsec.minegocio_service.services.ICustomerService;
import com.tidsec.minegocio_service.utils.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;
    private final IAddressService addressService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() throws Exception{
        List<CustomerDTO> list = mapperUtil.mapList(customerService.findAll(), CustomerDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable("id") String id) throws Exception{
        CustomerDTO CustomerDTO = mapperUtil.map(customerService.findById(UUID.fromString(id)), CustomerDTO.class);
        return ResponseEntity.ok(CustomerDTO);
    }

    @GetMapping("/identification/{identificationNumber}")
    public ResponseEntity<CustomerDTO> findByIdentificationNumber(@PathVariable("identificationNumber") String identificationNumber) throws Exception{
        CustomerDTO customerDTO = mapperUtil.map(customerService.findByIdentificationNumber(identificationNumber), CustomerDTO.class);
        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CustomerDTO> findByName(@PathVariable("name") String name) throws Exception{
        CustomerDTO customerDTO = mapperUtil.map(customerService.findByName(name), CustomerDTO.class);
        return ResponseEntity.ok(customerDTO);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO) throws Exception{

        Customer savedCustomer = customerService.saveCustomerWithMainAddress(customerDTO);

        // Construimos la URI de respuesta
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomer.getIdCustomer())
                .toUri();

        // Retornamos el objeto creado y la URI
        return ResponseEntity.created(location).body(mapperUtil.map(savedCustomer, CustomerDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@Valid @PathVariable("id") String id, @RequestBody CustomerDTO CustomerDTO) throws Exception{
        Customer obj = customerService.update(mapperUtil.map(CustomerDTO, Customer.class), UUID.fromString(id));
        return ResponseEntity.ok(mapperUtil.map(obj, CustomerDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception{
        boolean deleted = customerService.delete(UUID.fromString(id));
        CustomerDTO customer = mapperUtil.map(customerService.findById(UUID.fromString(id)), CustomerDTO.class);

        if (deleted) {
            if (customer.getAddress() != null && !customer.getAddress().isEmpty()) {
                for (AddressDTO addressDTO : customer.getAddress()) {
                    Address obj = addressService.findById(addressDTO.getIdAddress());
                    obj.setStatus(0);
                    addressService.save(obj);
                }
            }
            return ResponseEntity.ok("Customer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer not found with ID: " + id);
        }
    }
}
