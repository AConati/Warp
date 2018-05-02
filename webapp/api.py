#!/usr/bin/env python3
"""
	api.py
	Ari Conati & Grant Lee
	2 May 2018
	
	An api for retrieving data from 
	PSQL database full of NY Phil Data
"""
import sys
import flask
import json

app = flask.Flask(__name__)

@app.route('/')
def hello():
	return 'Do you even vim?'

@app.route('/pieces/')

@app.route('/soloists/')

@app.route('/conductors')

@app.route('/instruments')

@app.route('/venues/')

@app.route('/locations')

@app.route('/dates')

@app.route('/composers')

@app.route('/performances/')












if __name__ == '__main__':
	if len(sys.argv) != 3:
		print('Usage: {0} hostport'.format(sys.argv[0]))
		print('	Example: {0} perlman.mathcs.carleton.edu 5101'.format(sys.argv[0]))
		exit()
	
	host = sys.argv[1]
	port = int(sys.argv[2])
	app.run(host=host, port=port, debug=True)

