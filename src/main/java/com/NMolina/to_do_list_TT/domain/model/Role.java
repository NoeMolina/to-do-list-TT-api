package com.NMolina.to_do_list_TT.domain.model;

import java.util.Objects;

public class Role {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    private final Integer id;
    private final String code;
    private final String name;

    public Role(Integer id, String code, String name) {
        this.id = id;
        this.code = Objects.requireNonNull(code, "code no puede ser nulo");
        this.name = Objects.requireNonNull(name, "name no puede ser nulo");
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return ADMIN.equalsIgnoreCase(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Role other))
            return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}