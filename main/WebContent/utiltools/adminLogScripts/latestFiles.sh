#!/bin/sh
tail -n $1 $2 | /bin/grep $3 | cut -d$4 -f$5 | sort |  uniq -c | sort -r | $6 -n $7
