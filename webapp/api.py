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
        print('Connection error:', e, file == sys.stderr)
        return []

    rows = []
    try:
        cursor = connection.cursor()
        cursor.execute(query)
        rows = cursor.fetchall() # This can be trouble if your query results are really big.
    except Exception as e:
        print('Error querying database:', e, file==sys.stderr)

    connection.close()
    return rows

'''
@app.after_request
def set_headers(response):
    response.headers['Access-Control-Allow-Origin'] = '*'
    return response
'''

@app.route('/')
def hello():
	return 'Do you even vim?'

@app.route('/pieces/')
def get_pieces():
    '''
    '''
    query = 'SELECT * FROM pieces ORDER by composer,id'
    piece_list = []
	for row in _fetch_all_rows_for_query(query):
        piece = {'piece_id': row0[0], 'title':row[1], 'title':row[2], 'url':url}
        piece_list.append(piece)
        
    return json.dumps(piece_list)

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
		url = flask.url_for('get_conductors', conductor_id=row[0], _external=True)
		conductor = {'conductor_id':row[0], 'name':row[1], 'url':url}
		conductor_list.append(conductor)
	return json.dumps(conductor_list)

@app.route('/instruments')
def get_instruments():
    '''
    '''
    query = 'SELECT * FROM instruments ORDER BY name'
    instrument_list = []
    for row in fetch_all_rows_for_query(query):
        url = flask.url_for('get_instruments', instrument_id=row[0], _external=True)
        instrument = {'instrument_id': row[0], 'instrument_name': row[1], 'url': url}
        instrument_list.append(instrument)
    
    return json.dumps(instrument_list)

@app.route('/venues/')
def get_venues():
    '''
    '''
    query = 'SELECT * FROM venues'
    venue_list = []
    for row in fetch_all_rows_for_query(query):
    url = flask.url_for('get_venues', venue_id=row[0], _external=True)
        venue = {'venue_id': row[0], 'venue_name': row[1], 'location': row[2], 'url': url}
        venue_list.append(venue)

    return json.dumps(venue_list)
    
@app.route('/locations')
def get_locations():
    '''
    '''
    query = 'SELECT * FROM locations ORDER BY name'
    location_list = []
    for row in fetch_all_rows_for_query(query):
    url = flask.url_for('get_locations', location_id=row[0], _external=True)
        location = {'location_id': row[0], 'location_name': row[1], 'url': url}
        location_list.append(location)

    return json.dumps(location_list)

@app.route('/dates')

@app.route('/composers')
def get_composers():
    '''
    '''
    query = 'SELECT * FROM composers ORDER BY name'
    composer_list = []
    for row in fetch_all_rows_for_query(query):
    url = flask.url_for('get_composers', composer_id=row[0], _external=True)
        composer = {'composer_id': row[0], 'composer_name': row[1], 'url': url}
        composer_list.append(composer)

    return json.dumps(composer_list)

@app.route('/performances/')
def get_performances():
    '''
    '''
    url = flask.url_for('get_performances', performance_id=row[0], _external=True)
    query = 'SELECT * FROM performances ORDER by date, id'
    performance_list = []
    previous_date = ''
    for row in fetch_all_rows_for_query(query):
        if row[1] != previous_date:
            soloist_list = []
            performance_list.append({'performance_id': row[0], 'date': row[1], 'venue_id': row[2], 'conductor_id': row[3], 'piece_id': row[4], 'soloists': soloist_list})
        else:
            last_index = len(performance_list) - 1
            performance_list[last_index]['soloists'].append(row[5])

    return json.dumps(performance_list)


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

