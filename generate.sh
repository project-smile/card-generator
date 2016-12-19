#!/bin/bash
wkhtmltopdf --page-height 61mm --page-width 91mm -T 0 -B 0 -L 0 -R 0 --no-pdf-compression -d 600  card.html result.pdf
