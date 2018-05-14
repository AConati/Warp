#!/bin/bash

kill $(lsof -t -i:5121)

kill $(lsof -t -i:5122)
