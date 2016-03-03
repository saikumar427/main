#!/bin/sh
#grep "$1:$2:$3[$4-$5]" $6 |cut -d' ' -f3|sort| uniq -c |sort -r
grep "$1:$2:$3[$4-$5]" $6 |cut -d' ' -f3|sort| uniq -c |sort -r
