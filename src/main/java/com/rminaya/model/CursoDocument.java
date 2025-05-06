package com.rminaya.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "cursos")
public class CursoDocument {

    @EqualsAndHashCode.Include
    @Id
    private String id;
    private String nombre;
    private String siglas;
    private Boolean estado;
}
