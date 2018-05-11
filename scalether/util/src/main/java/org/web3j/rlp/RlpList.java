package org.web3j.rlp;

import java.util.Arrays;
import java.util.List;

/**
 * RLP list type.
 */
public class RlpList implements RlpType {
    private final List<? extends RlpType> values;

    public RlpList(RlpType... values) {
        this.values = Arrays.asList(values);
    }

    public RlpList(List<? extends RlpType> values) {
        this.values = values;
    }

    public List<? extends RlpType> getValues() {
        return values;
    }
}
