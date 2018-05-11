#!/usr/bin/env bash
DIR_NAME=/tmp/bitcoinddir
if [ -d $DIR_NAME ]; then
	rm -rf $DIR_NAME
fi
mkdir $DIR_NAME
bitcoind -regtest -datadir=$DIR_NAME -server -rest -rpcuser=user -rpcpassword=pass