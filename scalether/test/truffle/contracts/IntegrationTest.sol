pragma experimental ABIEncoderV2;
pragma solidity ^0.4.21;

contract IntegrationTest {
    uint256 public state;

    function setState(uint256 _state) public {
        state = _state;
    }

    event SimpleEvent(string indexed topic, string value);
    event AddressEvent(address indexed topic, string value);
    event MixedEvent(address indexed topic, string value, address indexed test);
    event RateEvent(address token, uint256 value);
    event StringEvent(string str, uint256 value);

    event TestEvent(uint256 value1);
    event TestEvent(uint256 value1, uint256 value2);
    event TestEvent(uint256 value1, uint256 value2, uint256 value3);

    struct Rate {
        address token;
        uint256 value;
    }

    struct StructWithString {
        string str;
        uint256 value;
    }

    function checkStructsWithString(StructWithString[] structs) public {
        uint length = structs.length;
        for (uint i=0; i<length; i++) {
            emit StringEvent(structs[i].str, structs[i].value);
        }
    }

    function getStructWithString() constant public returns (StructWithString) {
        return StructWithString("", 0);
    }

    function getStructsWithString() constant public returns (StructWithString[1]) {
        StructWithString[1] memory result;
        result[0] = StructWithString("", 0);
        return result;
    }

    function setRates(Rate[] rates) public {
        uint length = rates.length;
        for (uint i=0; i<length; i++) {
            emit RateEvent(rates[i].token, rates[i].value);
        }
    }

    Rate rate;

    function setRate(Rate _rate) public {
        rate = _rate;
        emit RateEvent(_rate.token, _rate.value);
    }

    function getRate() public returns (Rate) {
        return rate;
    }

    function emitSimpleEvent(string topic, string value) public {
        emit SimpleEvent(topic, value);
    }

    function emitAddressEvent(address topic, string value) public {
        emit AddressEvent(topic, value);
    }

    function emitMixedEvent(address topic, string value, address test) public {
        emit MixedEvent(topic, value, test);
    }
}