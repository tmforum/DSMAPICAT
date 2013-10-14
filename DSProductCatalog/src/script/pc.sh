#!/bin/bash

set -e

usage() {
	nom=`basename $0`
	echo "+"
	echo "+ +  ${nom} [-c] Create element with default file"
	echo "+ +  ${nom} [-c -f file ] Create element with specified file"
	echo "+ +  ${nom} [-p] Full update element with default file"
	echo "+ +  ${nom} [-p -f file ] Full update element with specified file"
    echo "+ +  ${nom} [-g] List all elements"
    echo "+ +  ${nom} [-g -q \"query\"] List all elements with attribute selection and/or attribute filtering"
    echo "+ +  ${nom} [-g -i id] Retrieve single element"
    echo "+ +  ${nom} [-g -i id -q \"query\"] Retrieve single element with attribute selection"
	echo "+ +  ${nom} [-h] Help"   
    echo "+ +  query format: \"fields=x,y,...\"] attribute selection"
    echo "+ +  query format: \"key=value&...\"] attribute filtering"    
    echo "+ +  query format: \"fields=x,y,...&key=value&...\"] attribute selection and/or filtering"  
	echo "+"
	}

# HELP
if [ $# -eq 1 -a "$1" = -h ]; then usage; exit 2; fi

. commons/conf.sh
CONTEXT=DSProductCatalog/api/productCategory
. commons/curl.sh

# OPTIONS
errOption=0
OPTIND=1
while getopts "cupgf:i:q:" option
do
	case $option in
		c)  CREATE=OK
            ;;
        p)  PUT=OK
            ;; 
        g)  GET=OK
			;;
        i)  ID="${OPTARG}"
            ;;
        q)  QUERY="${OPTARG}"
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
        FILE=create.json
    fi
    post "" $FILE
    exit 2
fi

# PUT
if [ -n "$PUT" ]; then
    if [ ! -n "$FILE" ]; then
        FILE=put.json
    fi
    if [ ! -n "$ID" ]; then
        echo "Please provide [-i id]" >&2
        exit 4
    fi    
    put "${ID}" $FILE
    exit 2
fi

# GET
if [ -n "$GET" ]; then
    if [ -n "$QUERY" ]; then
        QUERY="?$QUERY"
    fi
    get "${ID}${QUERY}"
    exit 2
fi

usage >&2


