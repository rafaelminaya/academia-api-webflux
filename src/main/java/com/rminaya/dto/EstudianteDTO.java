package com.rminaya.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstudianteDTO {

    private String id;

    @NotBlank(message = "no puede estar vacio.")
    @Size(min = 3, max = 60, message = "debe tener entre 3 y 255 caracteres.")
    private String nombresEstudiante;

    @NotBlank(message = "no puede estar vacio.")
    @Size(min = 3, max = 60, message = "debe tener entre 3 y 255 caracteres.")
    private String apellidosEstudiante;

    @NotBlank(message = "no puede estar vacio.")
    @Size(min = 8, max = 8, message = "debe tener exactamente 8 caracteres.")
    private String dni;

    @NotNull(message = "no puede estar vacio.")
    @Positive(message = "debe ser mayor que 0")
    private Integer edad;
}
