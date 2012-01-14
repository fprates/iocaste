#!/bin/bash
cp conf/sqltool.rc $HOME
mkdir transport
./startup.sh
./regendb.sh
