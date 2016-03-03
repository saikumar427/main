#!/bin/sh
/bin/grep $1 $2 | cut -d'#' -f2 | cut -d'?' -f1 | cut -d" " -f2 | sort |sort -r | uniq -c | sort -nrk1 | head -n $3
