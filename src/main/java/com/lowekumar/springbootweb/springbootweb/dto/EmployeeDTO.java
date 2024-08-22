package com.lowekumar.springbootweb.springbootweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lowekumar.springbootweb.springbootweb.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotNull(message = "Required Field")
    private String name;

    @Email(message ="Enter valid email only")
    private String email;

//    @NotEmpty()
    private Integer age;

//    @Size(min = 2, max=10, message = "role name should not exceed 10 character")
    @NotNull(message = "Role of employee can't ne null")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of employee can be either ADMIN or USER")
    @EmployeeRoleValidation
    private String role;// validation we have done using our own custom annotations

    private Double salary;
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    private boolean isActive;

   }
