#!/bin/sh
/bin/grep $1 $2 | sort -r -t'#' -k6 | head -n $3
