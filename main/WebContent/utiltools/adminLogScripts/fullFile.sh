#!/bin/sh
/bin/grep $1 $2 | cut -d$3 -f$4 | sort | uniq -c | sort -r |$5 -n $6
