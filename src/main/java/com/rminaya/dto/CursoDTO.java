package com.rminaya.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoDTO {

    private String id;

    @NotBlank(message = "no puede estar vacio.")
    @Size(min = 3, max = 60, message = "debe tener entre 3 y 255 caracteres.")
    private String nombreCurso;

    @NotBlank(message = "no puede estar vacio.")
    @Size(min = 3, max = 3, message = "debe tener exactamente 3 caracteres.")
    private String siglasCurso;

    @NotNull(message = "no puede estar vacio.")
    private Boolean estado;
}
