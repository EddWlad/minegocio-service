package com.tidsec.minegocio_service.controllers;
import com.tidsec.minegocio_service.dtos.CustomerDTO;
import com.tidsec.minegocio_service.entities.Customer;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;
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

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CustomerDTO CustomerDTO) throws Exception{
        Customer obj = customerService.save(mapperUtil.map(CustomerDTO, Customer.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdCustomer())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable("id") String id, @RequestBody CustomerDTO CustomerDTO) throws Exception{
        Customer obj = customerService.update(mapperUtil.map(CustomerDTO, Customer.class), UUID.fromString(id));
        return ResponseEntity.ok(mapperUtil.map(obj, CustomerDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) throws Exception{
        customerService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
