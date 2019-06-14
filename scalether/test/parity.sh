#!/usr/bin/env bash
rm -rf ~/.local/share/io.parity.ethereum
parity --chain ~/.ethereum/parity-dev.json --reseal-min-period 0 --unsafe-expose

