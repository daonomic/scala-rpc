package scalether.generator.domain;

import java.util.List;

public class AbiComponent {
    private String name;
    private String type;
    private List<AbiComponent> components;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AbiComponent> getComponents() {
        return components;
    }

    public void setComponents(List<AbiComponent> components) {
        this.components = components;
    }
}
