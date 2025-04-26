package com.tidsec.minegocio_service.dtos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AddressDTO {
    @EqualsAndHashCode.Include
    private UUID idAddress;

    @NotBlank(message = "Province is required")
    private String province;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    private boolean mainAddress = false;

    private Integer status = 1;

    @JsonBackReference
    private CustomerDTO customer;

}
