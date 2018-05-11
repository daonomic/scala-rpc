package scalether.generator.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AbiFunctionType {
    FUNCTION("function"),
    CONSTRUCTOR("constructor"),
    FALLBACK("fallback");

    @JsonValue
    private final String id;

    AbiFunctionType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
