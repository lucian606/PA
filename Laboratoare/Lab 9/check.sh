#!/usr/bin/env bash

NUM_TASKS=3
TASKS=( task-1 task-2 task-3 )
NUM_TESTS=( 10 10 10 )

TIMEOUT_CPP=1
TIMEOUT_JAVA=7

source ./tests/base_check.sh
