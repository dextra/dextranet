#!/bin/bash -e

if [ "x$GAE_PASSWORD" == "x" ]; then
	echo "export GAE_PASSWORD to descrypt files";
	exit 1;
fi

find . -name "*.crypted" | while read k; do 
	echo "decrypt: $k";
	openssl enc -aes-256-cbc -salt -in "$k" -out "$(echo "$k" | sed "s/\.crypted$//g")" -d -pass "pass:$GAE_PASSWORD"; 
done

