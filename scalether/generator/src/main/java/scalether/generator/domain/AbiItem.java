package scalether.generator.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonSubTypes({
    @JsonSubTypes.Type(value = AbiFunction.class, name = "function"),
    @JsonSubTypes.Type(value = AbiFunction.class, name = "constructor"),
    @JsonSubTypes.Type(value = AbiFunction.class, name = "default"),
    @JsonSubTypes.Type(value = AbiEvent.class, name = "event")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = AbiFunction.class, visible = true, include = JsonTypeInfo.As.EXISTING_PROPERTY)
public interface AbiItem {
}
