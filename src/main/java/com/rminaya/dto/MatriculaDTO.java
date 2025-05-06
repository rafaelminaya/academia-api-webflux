package com.rminaya.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDTO {

    private String id;

    private LocalDateTime fechaMatricula;

    @NotNull(message = "no puede estar vacio.")
    private EstudianteDTO estudiante;

    @NotNull(message = "no puede estar vacio.")
    private List<CursoDTO> cursos;

    @NotNull(message = "no puede estar vacio.")
    private Boolean estado;
}
