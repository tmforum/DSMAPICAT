#!/bin/bash

set -e

usage() {
	nom=`basename $0`
	echo "+"
    echo "+ +  ${nom} [-n] Count"    
	echo "+ +  ${nom} [-c] Create list with default file"
	echo "+ +  ${nom} [-c -f file ] Create list with specified file"    
    echo "+ +  ${nom} [-d] Delete all"
    echo "+ +  ${nom} [-d -i id ] Delete single"    
	echo "+ +  ${nom} [-h] Help"  
    echo "+ +  query format: \"fields=x,y,...\"] attribute selection"
    echo "+ +  query format: \"key=value&...\"] attribute filtering"    
    echo "+ +  query format: \"fields=x,y,...&key=value&...\"] attribute selection and/or filtering"      
	echo "+"
	}

# HELP
if [ $# -eq 1 -a "$1" = -h ]; then usage; exit 2; fi

. commons/conf.sh
CONTEXT=DSProductCatalog/api/productspecification
. commons/curl.sh

# OPTIONS
errOption=0
OPTIND=1
while getopts "cndf:i:q:" option
do
	case $option in
		c)  CREATE=OK
            ;;
		n)  COUNT=OK
            ;;
        d)  DELETE=OK
            ;; 
        i)  ID="${OPTARG}"
            ;;          
        f)  FILE="${OPTARG}"
			;;
		\?) echo " option $OPTARG INVALIDE" >&2
			errOption=3
	esac
done

if [ $errOption == 3 ]; then usage >&2; exit $errOption; fi

# CREATE
if [ -n "$CREATE" ]; then
    if [ ! -n "$FILE" ]; then
        FILE=ps-list-large.json
    fi
    post "admin" $FILE
    exit 2
fi

# DELETE
if [ -n "$DELETE" ]; then
    if [ ! -n "$ID" ]; then
        echo "WARN: Delete all ? ctrl+c to break" >&2
        wait
    fi
    delete "admin/${ID}"
    exit 2
fi

# COUNT
if [ -n "$COUNT" ]; then
    get "admin/count"
    exit 2
fi

usage >&2


