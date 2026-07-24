package com.NMolina.to_do_list_TT.domain.port.out;

public interface PasswordEncoderPort {
    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}