#!/bin/sh
/bin/grep $1 $2 |tail -n $3 | cut -d$4 -f$5 | sort | uniq -c | sort -r |$6 -n $7

