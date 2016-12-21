#!/bin/bash

# first cleanup the ouput dir
sh cleanup.sh

# then generate cards
node generate.js

# and copy assets
cp -r assets generated
cp -r root-assets/* generated
