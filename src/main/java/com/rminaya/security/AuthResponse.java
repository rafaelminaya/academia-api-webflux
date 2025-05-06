package com.rminaya.security;

import java.util.Date;

public record AuthResponse (String token, Date expiration) {
}
