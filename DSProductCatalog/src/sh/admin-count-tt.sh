#!/bin/bash

set -e

. commons/http.sh

NOLOG=true

info_section "1/1 - Count"
get "admin/count"
