package com.rminaya.config;

import com.rminaya.handler.CursoHandler;
import com.rminaya.handler.EstudianteHandler;
import com.rminaya.handler.MatriculaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(CursoHandler cursoHandler, EstudianteHandler estudianteHandler, MatriculaHandler matriculaHandler) {
        // RUTAS CURSOS
        return route(GET("/v2/cursos"), cursoHandler::findAll)
                .andRoute(GET("/v2/cursos/{id}"), cursoHandler::findById)
                .andRoute(POST("/v2/cursos"), cursoHandler::save)
                .andRoute(PUT("/v2/cursos/{id}"), cursoHandler::update)
                .andRoute(DELETE("/v2/cursos/{id}"), cursoHandler::delete)
                // RUTAS ESTUDIANTES
                .andRoute(GET("/v2/estudiantes"), estudianteHandler::findAll)
                .andRoute(GET("/v2/estudiantes/por-edad-asc"), estudianteHandler::findAllByOrderByEdadAsc)
                .andRoute(GET("/v2/estudiantes/por-edad-desc"), estudianteHandler::findAllByOrderByEdadDesc)
                .andRoute(GET("/v2/estudiantes/{id}"), estudianteHandler::findById)
                .andRoute(POST("/v2/estudiantes"), estudianteHandler::save)
                .andRoute(PUT("/v2/estudiantes/{id}"), estudianteHandler::update)
                .andRoute(DELETE("/v2/estudiantes/{id}"), estudianteHandler::delete)
                // RUTAS MATRICULAS
                .andRoute(POST("/v2/matriculas"), matriculaHandler::save);
    }
}
