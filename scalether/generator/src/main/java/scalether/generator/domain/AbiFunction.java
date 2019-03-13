package scalether.generator.domain;

import scalether.generator.util.Hash;
import scalether.generator.util.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AbiFunction implements AbiItem {
    private String name;
    private AbiFunctionType type = AbiFunctionType.FUNCTION;
    private List<AbiComponent> inputs = Collections.emptyList();
    private List<AbiComponent> outputs = Collections.emptyList();
    private boolean payable = false;
    private boolean constant = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbiFunctionType getType() {
        return type;
    }

    public void setType(AbiFunctionType type) {
        this.type = type;
    }

    public List<AbiComponent> getInputs() {
        return inputs;
    }

    public void setInputs(List<AbiComponent> inputs) {
        this.inputs = inputs;
    }

    public List<AbiComponent> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<AbiComponent> outputs) {
        this.outputs = outputs;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public String getId() {
        return Hex.prefixed(Hash.sha3(toString().getBytes(StandardCharsets.ISO_8859_1))).substring(0, 10);
    }

    @Override
    public String toString() {
        return name + "(" + getInputs().stream().map(AbiComponent::getType).collect(Collectors.joining(",")) + ")";
    }
}
