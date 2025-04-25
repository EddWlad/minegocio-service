package com.tidsec.minegocio_service.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerDTO {
    @EqualsAndHashCode.Include
    private UUID idCustomer;

    @NotBlank(message = "Identification type is required")
    private String identificationType;

    @NotBlank(message = "Identification number is required")
    private String identificationNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private Integer status = 1;

    private List<AddressDTO> address;
}
