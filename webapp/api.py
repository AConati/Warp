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
import config
import psycopg2

app = flask.Flask(__name__, static_folder='static', template_folder='templates')

def _fetch_all_rows_for_query(query):
    '''
    Returns a list of rows obtained from the books database by the specified SQL
    query. If the query fails for any reason, an empty list is returned.

    Note that this is not necessarily the right error-handling choice. Would users
    of the API like to know the nature of the error? Do we as API implementors
    want to share that information? There are many considerations to balance.
    '''
    try:
        connection = psycopg2.connect(database=config.database, user=config.user, password=config.password)
    except Exception as e:
        print('Connection error:', e, file=sys.stderr)
        return []

    rows = []
    try:
        cursor = connection.cursor()
        cursor.execute(query)
        rows = cursor.fetchall() # This can be trouble if your query results are really big.
    except Exception as e:
        print('Error querying database:', e, file=sys.stderr)

    connection.close()
    return rows

@app.after_request
def set_headers(response):
    response.headers['Access-Control-Allow-Origin'] = '*'
    return response

@app.route('/')
def hello():
	return 'Do you even vim?'

@app.route('/pieces/')

@app.route('/soloists/')

@app.route('/conductors')
def get_conductors():
	"""
	Returns the list of conductors in the database. A conductor
	will be represented by a JSON dictionary with keys "id" (int),
	"name" (int), and 'url' (string). The value associated with 
	'url' is a URL you can use to retrieve this same conductor
	in the future.
	"""
	query = 'SELECT id, name FROM conductors ORDER BY name'
	conductor_list = []
	for row in _fetch_all_rows_for_query(query):
		url = flask.url_for('get_conductor_by_id', conductor_id=row[0], _external=True)
		conductor = {'conductor_id':row[0], 'name':row[1], 'url':url}
		return json.dumps(conductor)


@app.route('/instruments')

@app.route('/venues/')

@app.route('/locations')

@app.route('/dates')

@app.route('/composers')

@app.route('/performances/')

@app.route('/help')
def help():
    rule_list = []
    for rule in app.url_map.iter_rules():
        rule_text = rule.rule.replace('<', '&lt;').replace('>', '&gt;')
        rule_list.append(rule_text)
    return json.dumps(rule_list)










if __name__ == '__main__':
	if len(sys.argv) != 3:
		print('Usage: {0} hostport'.format(sys.argv[0]))
		print('	Example: {0} perlman.mathcs.carleton.edu 5101'.format(sys.argv[0]))
		exit()
	
	host = sys.argv[1]
	port = int(sys.argv[2])
	app.run(host=host, port=port, debug=True)

