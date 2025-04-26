package com.tidsec.minegocio_service.controllers;

import com.tidsec.minegocio_service.dtos.AddressDTO;
import com.tidsec.minegocio_service.dtos.CustomerDTO;
import com.tidsec.minegocio_service.entities.Customer;
import com.tidsec.minegocio_service.services.ICustomerService;
import com.tidsec.minegocio_service.utils.MapperUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Mock
    private ICustomerService customerService;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void testFindAllCustomers() throws Exception {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setIdCustomer(UUID.randomUUID());

        Customer customer2 = new Customer();
        customer2.setIdCustomer(UUID.randomUUID());

        List<Customer> customers = List.of(customer1, customer2);

        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .idCustomer(customer1.getIdCustomer())
                .name("Carlos")
                .build();

        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .idCustomer(customer2.getIdCustomer())
                .name("Ana")
                .build();

        List<CustomerDTO> customerDTOs = List.of(customerDTO1, customerDTO2);

        when(customerService.findAll()).thenReturn(customers);
        when(mapperUtil.mapList(customers, CustomerDTO.class)).thenReturn(customerDTOs);

        // Act
        ResponseEntity<List<CustomerDTO>> response = customerController.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Carlos", response.getBody().get(0).getName());
        assertEquals("Ana", response.getBody().get(1).getName());

        verify(customerService, times(1)).findAll();
    }

    @Test
    void testFindCustomerByName() throws Exception {
        // Arrange
        String name = "Carlos";
        Customer customer = new Customer();
        customer.setName(name);

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name(name)
                .build();

        when(customerService.findByName(name)).thenReturn(customer);
        when(mapperUtil.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        // Act
        ResponseEntity<CustomerDTO> response = customerController.findByName(name);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(name, response.getBody().getName());

        verify(customerService, times(1)).findByName(name);
    }

    @Test
    void testFindCustomerByIdentificationNumber() throws Exception {
        // Arrange
        String identificationNumber = "1234567890";

        Customer customer = new Customer();
        customer.setIdentificationNumber(identificationNumber);

        CustomerDTO customerDTO = CustomerDTO.builder()
                .identificationNumber(identificationNumber)
                .build();

        when(customerService.findByIdentificationNumber(identificationNumber)).thenReturn(customer);
        when(mapperUtil.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        // Act
        ResponseEntity<CustomerDTO> response = customerController.findByIdentificationNumber(identificationNumber);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(identificationNumber, response.getBody().getIdentificationNumber());

        verify(customerService, times(1)).findByIdentificationNumber(identificationNumber);
    }

    @Test
    void testFindCustomerById() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();

        // Creamos un Customer falso
        Customer customer = new Customer();
        customer.setIdCustomer(customerId);

        // También creamos un CustomerDTO con dirección(es)
        AddressDTO addressDTO = AddressDTO.builder()
                .idAddress(UUID.randomUUID())
                .address("Calle Falsa 123")
                .build();

        CustomerDTO customerDTO = CustomerDTO.builder()
                .idCustomer(customerId)
                .name("Carlos")
                .lastName("Gómez")
                .email("carlos.gomez@example.com")
                .address(List.of(addressDTO)).build();

        when(customerService.findById(customerId)).thenReturn(customer);
        when(mapperUtil.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        // Act
        ResponseEntity<CustomerDTO> response = customerController.findById(customerId.toString());

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(customerId, response.getBody().getIdCustomer());
        assertNotNull(response.getBody().getAddress());
        assertEquals(1, response.getBody().getAddress().size());
        assertEquals("Calle Falsa 123", response.getBody().getAddress().get(0).getAddress());

        verify(customerService, times(1)).findById(customerId);
    }

    @Test
    void testCreateCustomerSuccessfully() throws Exception {
        // Arrange
        CustomerDTO customerDTO = CustomerDTO.builder()
                .identificationType("Cédula")
                .identificationNumber("1234567890")
                .name("Juan")
                .lastName("Pérez")
                .email("juan.perez@example.com")
                .phoneNumber("0987654321")
                .status(1)
                .build();

        CustomerDTO mappedCustomerDTO = CustomerDTO.builder()
                .idCustomer(UUID.randomUUID())
                .identificationType("Cédula")
                .identificationNumber("1234567890")
                .name("Juan")
                .lastName("Pérez")
                .email("juan.perez@example.com")
                .phoneNumber("0987654321")
                .status(1)
                .build();

        Customer fakeCustomer = new Customer();
        fakeCustomer.setIdCustomer(mappedCustomerDTO.getIdCustomer());

        when(customerService.saveCustomerWithMainAddress(any(CustomerDTO.class)))
                .thenReturn(fakeCustomer);

        when(mapperUtil.map(any(), eq(CustomerDTO.class)))
                .thenReturn(mappedCustomerDTO);

        // Aquí simulamos un ServletRequest para no romper el test
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Act
        ResponseEntity<CustomerDTO> response = customerController.save(customerDTO);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mappedCustomerDTO.getIdentificationNumber(), response.getBody().getIdentificationNumber());

        verify(customerService, times(1)).saveCustomerWithMainAddress(any(CustomerDTO.class));
    }

    @Test
    void testUpdateCustomerSuccessfully() throws Exception {
        // Arrange
        CustomerDTO customerDTO = CustomerDTO.builder()
                .idCustomer(UUID.randomUUID())
                .identificationType("Cédula")
                .identificationNumber("9876543210")
                .name("Carlos")
                .lastName("Gómez")
                .email("carlos.gomez@example.com")
                .phoneNumber("0999988776")
                .status(1)
                .build();

        Customer updatedCustomer = new Customer();
        updatedCustomer.setIdCustomer(customerDTO.getIdCustomer());

        when(customerService.update(any(Customer.class), any(UUID.class)))
                .thenReturn(updatedCustomer);

        when(mapperUtil.map(any(), eq(Customer.class)))
                .thenReturn(updatedCustomer);

        when(mapperUtil.map(any(Customer.class), eq(CustomerDTO.class)))
                .thenReturn(customerDTO);


        // Simulamos el request para no romper el test
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Act
        ResponseEntity<CustomerDTO> response = customerController.update(customerDTO.getIdCustomer().toString(), customerDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(customerDTO.getIdentificationNumber(), response.getBody().getIdentificationNumber());

        verify(customerService, times(1)).update(any(Customer.class), any(UUID.class));

    }

    @Test
    void testDeleteCustomerSuccessfully() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();

        // Mock delete
        when(customerService.delete(customerId)).thenReturn(true);

        // Mock findById
        Customer fakeCustomer = new Customer();
        fakeCustomer.setIdCustomer(customerId);
        fakeCustomer.setAddressList(Collections.emptyList());
        when(customerService.findById(eq(customerId))).thenReturn(fakeCustomer);

        // Mock map
        CustomerDTO fakeCustomerDTO = CustomerDTO.builder()
                .idCustomer(customerId)
                .address(Collections.emptyList())
                .build();
        when(mapperUtil.map(any(), eq(CustomerDTO.class))).thenReturn(fakeCustomerDTO);

        // Act
        ResponseEntity<String> response = customerController.delete(customerId.toString());

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Customer deleted successfully", response.getBody());

        verify(customerService, times(1)).delete(customerId);
    }


    @Test
    void testDeleteCustomerNotFound() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();

        when(customerService.delete(customerId)).thenReturn(false);

        // Act
        ResponseEntity<String> response = customerController.delete(customerId.toString());

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Customer not found with ID: " + customerId.toString(), response.getBody());

        verify(customerService, times(1)).delete(customerId);
    }
}
