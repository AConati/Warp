#!/bin/bash

kill $(lsof -t -i:5121)

kill $(lsof -t -i:5221)

kill $(lsof -t -i:5106)

kill $(lsof -t -i:5206)
