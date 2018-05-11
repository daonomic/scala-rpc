package scalether.generator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TruffleContract {
    @JsonProperty("contractName")
    private String name;
    @JsonProperty("abi")
    private List<AbiItem> abi;
    @JsonProperty("bytecode")
    private String bin;

    public TruffleContract(String name, List<AbiItem> abi, String bin) {
        this.name = name;
        this.abi = abi;
        this.bin = bin;
    }

    public TruffleContract() {
    }

    public boolean isAbstract() {
        return bin == null || bin.length() <= 2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbiItem> getAbi() {
        return abi;
    }

    public void setAbi(List<AbiItem> abi) {
        this.abi = abi;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }
}
