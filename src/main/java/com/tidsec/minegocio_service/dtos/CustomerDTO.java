package com.tidsec.minegocio_service.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^(RUC|Cédula)$", message = "Identification type must be 'RUC' or 'Cédula'.")
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

    @JsonManagedReference
    private List<AddressDTO> address;
}
