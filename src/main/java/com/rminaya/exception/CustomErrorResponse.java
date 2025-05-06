package com.rminaya.exception;

import java.time.LocalDateTime;
import java.util.List;

public record CustomErrorResponse(
        LocalDateTime datetime,
        String status,
        List<String> message
) {
}
