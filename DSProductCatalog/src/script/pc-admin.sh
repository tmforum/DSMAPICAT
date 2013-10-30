#!/bin/bash

set -e

RESOURCE=admin/productCategory

usage() {
    nom=`basename $0`
    echo "+"
    echo "+ +  ${nom} [-n] count"
    echo "+ +  ${nom} [-c file ] post list with specified file"    
    echo "+ +  ${nom} [-d] delete all"
    echo "+ +  ${nom} [-d -i id ] delete single"    
    echo "+ +  ${nom} [-h] Help"  
    echo "+"
}

# HELP
if [ $# -eq 1 -a "$1" = -h ]; then usage; exit 2; fi

# OPTIONS
errOption=0
OPTIND=1
while getopts "ndc:i:q:" option
do
	case $option in
		c)  POST=OK
            FILE="${OPTARG}"
            ;;
		n)  COUNT=OK
            ;;
        d)  DELETE=OK
            ;; 
        i)  ID="${OPTARG}"
            ;;
		\?) echo " option $OPTARG INVALIDE" >&2
			errOption=3
	esac
done

if [ $errOption == 3 ]; then usage >&2; exit $errOption; fi

. commons/conf.sh
. commons/curl.sh

usage >&2


