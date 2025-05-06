package com.rminaya.config;

import com.rminaya.dto.CursoDTO;
import com.rminaya.dto.EstudianteDTO;
import com.rminaya.dto.MatriculaDTO;
import com.rminaya.model.CursoDocument;
import com.rminaya.model.EstudianteDocument;
import com.rminaya.model.MatriculaDocument;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean(name = "defaultMapper")
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean(name = "estudianteMapper")
    public ModelMapper mapperEstudiante() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.createTypeMap(EstudianteDocument.class, EstudianteDTO.class)
                .addMapping(EstudianteDocument::getNombres, (dtoDestino, objectNombres) -> dtoDestino.setNombresEstudiante((String) objectNombres))
                .addMapping(EstudianteDocument::getApellidos, (dtoDestino, objectApellidos) -> dtoDestino.setApellidosEstudiante((String) objectApellidos));

        mapper.createTypeMap(EstudianteDTO.class, EstudianteDocument.class)
                .addMapping(EstudianteDTO::getNombresEstudiante, (dtoDestino, objectNombresEstudiante) -> dtoDestino.setNombres((String) objectNombresEstudiante))
                .addMapping(EstudianteDTO::getApellidosEstudiante, (dtoDestino, objectApellidosEstudiante) -> dtoDestino.setApellidos((String) objectApellidosEstudiante));

        return mapper;
    }

    @Bean(name = "cursoMapper")
    public ModelMapper mapperCurso() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.createTypeMap(CursoDocument.class, CursoDTO.class)
                .addMapping(CursoDocument::getNombre, (dtoDestino, objectNombre) -> dtoDestino.setNombreCurso((String) objectNombre))
                .addMapping(CursoDocument::getSiglas, (dtoDestino, objectSiglas) -> dtoDestino.setSiglasCurso((String) objectSiglas));

        mapper.createTypeMap(CursoDTO.class, CursoDocument.class)
                .addMapping(CursoDTO::getNombreCurso, (dtoDestino, objectNombreCurso) -> dtoDestino.setNombre((String) objectNombreCurso))
                .addMapping(CursoDTO::getSiglasCurso, (dtoDestino, objectSiglasCurso) -> dtoDestino.setSiglas((String) objectSiglasCurso));

        return mapper;
    }

    @Bean(name = "matriculaMapper")
    public ModelMapper mapperMatricula() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.createTypeMap(MatriculaDocument.class, MatriculaDTO.class)
                .addMapping(document -> document.getEstudiante().getNombres(), (dtoDestino, objectEstudianteNombres) -> dtoDestino.getEstudiante().setNombresEstudiante((String) objectEstudianteNombres))
                .addMapping(document -> document.getEstudiante().getApellidos(), (dtoDestino, objectEstudianteApellidos) -> dtoDestino.getEstudiante().setApellidosEstudiante((String) objectEstudianteApellidos));

        mapper.createTypeMap(MatriculaDTO.class, MatriculaDocument.class)
                .addMapping(dto -> dto.getEstudiante().getNombresEstudiante(), (dtoDestino, objectEstudianteNombres) -> dtoDestino.getEstudiante().setNombres((String) objectEstudianteNombres))
                .addMapping(dto -> dto.getEstudiante().getApellidosEstudiante(), (dtoDestino, objectEstudianteApellidos) -> dtoDestino.getEstudiante().setApellidos((String) objectEstudianteApellidos));

        return mapper;
    }
}
