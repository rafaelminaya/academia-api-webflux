package com.rminaya.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest req) {
        Map<String, Object> generalError = getErrorAttributes(req, ErrorAttributeOptions.defaults());

        Throwable ex = getError(req);
        int statusCode = Integer.valueOf(String.valueOf(generalError.get("status")));

        CustomErrorResponse cer;

        switch (statusCode) {
            case 400, 422 -> {
                cer = new CustomErrorResponse(LocalDateTime.now(), "BAD REQUEST", this.getListMessage(ex));
            }
            case 404 -> {
                cer = new CustomErrorResponse(LocalDateTime.now(), "RESOURCE NOT FOUND", this.getListMessage(ex));
            }
            case 401 -> {
                cer = new CustomErrorResponse(LocalDateTime.now(), "RESOURCE NOT AUTHORIZED", this.getListMessage(ex));
            }
            case 403 -> {
                cer = new CustomErrorResponse(LocalDateTime.now(), "RESOURCE FORBIDDEN", this.getListMessage(ex));
            }
            case 500 ->
                    cer = new CustomErrorResponse(LocalDateTime.now(), "INTERNAL SERVER ERROR", this.getListMessage(ex));
            default -> cer = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), this.getListMessage(ex));
        }

        return ServerResponse.status(statusCode)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cer));
    }

    private List<String> getListMessage(Throwable ex) {
        List<String> mensajes = new ArrayList<>();

        if (ex instanceof WebExchangeBindException webEx) {
            // Validación de DTOs en controladores anotados
            webEx.getFieldErrors().forEach(error -> {
                String campo = error.getField();
                String mensaje = error.getDefaultMessage();
                mensajes.add("El campo '" + campo + "' " + mensaje);
            });

        } else if (ex instanceof ResponseStatusException rEx) {
            // Validación de DTOs de controladores en RouterFunction
            Pattern pattern = Pattern.compile("interpolatedMessage='([^']+)'[^}]*propertyPath=([^,}]+)");
            Matcher matcher = pattern.matcher(rEx.getMessage());

            while (matcher.find()) {
                String mensaje = matcher.group(1);
                String campo = matcher.group(2);

                mensajes.add("El campo '" + campo + "' " + mensaje);
            }
        } else {
            // Fallback: mensaje simple por defecto
            mensajes.add(ex.getMessage());
        }

        return mensajes;
    }
}
