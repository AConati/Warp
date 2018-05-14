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
from collections import defaultdict

app = flask.Flask(__name__, static_folder='static', template_folder='templates')

def _fetch_all_rows_for_query(query):
    '''
    Returns a list of rows obtained from the NYPhil database by the specified SQL
    query. If the query fails for any reason, an empty list is returned.
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


@app.after_request
def set_headers(response):
    response.headers['Access-Control-Allow-Origin'] = '*'
    return response


@app.route('/')
def hello():
    return 'Do you even vim?'

@app.route('/pieces/')
def get_pieces():
    '''
    Returns the list of pieces in the database, in alphabetical order
    by name of piece or name of composer. Each piece is represented
    by a JSON dictionary with keys 'id' (int), 'name' (string), 
    'composer' (string), and 'url' (string). The value associated with 
    'url' is a URL you can use to retrieve this same piece in the 
    future.

    The particular url addresses:

        http://.../pieces/
        http://.../pieces/?sort=name
        http://.../pieces/?sort=composer
    '''
    query = '''SELECT id, name, composer FROM pieces ORDER BY '''

    sort_argument = flask.request.args.get('sort')
    if sort_argument == 'composer':
        query += 'composer'
    else:
        query += 'name'

    piece_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_pieces', piece_id=row[0], _external=True)
        piece = {'piece_id':row[0], 'piece_name':row[1], 'composer_name':row[2], 'url':url}
        piece_list.append(piece)

    return json.dumps(piece_list)


@app.route('/soloists/')
def get_soloists():
    '''
    Returns the list of soloists in the database, in alphabetical order 
    by name of the soloist or by the instrument played by soloist. Each
    soloist is represented by a JSON dictionary with keys 'id' (int), 
    'name' (string), 'instrument' (string), and 'url' (string). The 
    value associated with 'url' is a URL you can use to retrieve
    this same soloist in the future.

    The particular url addreses:

        http://.../soloists/
        http://.../soloists/?sort=name
        http://.../soloists/?sort=instrument
    '''
    query = '''SELECT id, name, instrument FROM soloists ORDER BY '''
    sort_argument = flask.request.args.get('sort')
    if sort_argument == 'instrument':
        query += 'instrument'
    else:
        query += 'name'

    soloist_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_soloists', soloist_id=row[0], _external=True)
        soloist = {'soloist_id':row[0], 'soloist_name':row[1], 'soloist_instrument':row[2], 'url':url}
        soloist_list.append(soloist)

    return json.dumps(soloist_list)

@app.route('/conductors')
def get_conductors():
    '''
    Returns the list of conductors in the database. A conductor
    will be represented by a JSON dictionary with keys "id" (int),
    "name" (int), and 'url' (string). The value associated with 
    'url' is a URL you can use to retrieve this same conductor
    in the future.
    '''
    query = 'SELECT id, name FROM conductors ORDER BY name'

    conductor_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_conductors', conductor_id=row[0], _external=True)
        conductor = {'conductor_id':row[0], 'conductor_name':row[1], 'url':url}
        conductor_list.append(conductor)
    return json.dumps(conductor_list)

@app.route('/instruments')
def get_instruments():
    '''
    Returns the list of instruments in the database. A instrument
    resource will be represented by a JSON dictionary with keys 'id' (int),
    'name' (string), and 'url' (string). The value associated with 'url'
    is a URL you can use to retrieve this same instrument in the future.
    '''
    query = 'SELECT id, name FROM instruments ORDER BY name'
    instrument_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_instruments', instrument_id=row[0], _external=True)
        instrument = {'instrument_id': row[0], 'instrument_name': row[1], 'url': url}
        instrument_list.append(instrument)

    return json.dumps(instrument_list)

@app.route('/venues/')
def get_venues():
    '''
    Returns the list of venues in the database. A venue resource
    will be represented by a JSON dictionary with keys 'id' (int), 
    'name' (string), and 'url' (string). The value associated with 
    'url' is a URL you can use to retrieve this same venue in the
    future.

        http://.../venues/
        http://.../venues/?sort=location
        http://.../venues/?sort=name
    '''
    query = '''SELECT id, name, location FROM venues ORDER BY '''

    sort_argument = flask.request.args.get('sort')
    if sort_argument == 'location':
        query += 'location'
    else:
       query += 'name'

    venue_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_venues', venue_id=row[0], _external=True)
        venue = {'venue_id': row[0], 'venue_name': row[1], 'venue_location': row[2], 'url': url}
        venue_list.append(venue)

    return json.dumps(venue_list)

@app.route('/locations')
def get_locations():
    '''
    Returns the list of locations in the database. A location
    resource will be represented by a JSON dictionary with keys 
    'id' (int), 'name' (int), and 'url' (string). The value 	
    associated with 'url' is a URL you can use to retrieve this
    same location in the future.
    '''
    query = 'SELECT id, name FROM locations ORDER BY name'
    location_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_locations', location_id=row[0], _external=True)
        location = {'location_id': row[0], 'location_name': row[1], 'url': url}
        location_list.append(location)

    return json.dumps(location_list)

@app.route('/dates')
def get_dates():
    query = 'SELECT id, date FROM performances ORDER BY date'
    date_list = []
    previous_date = ''
    for row in _fetch_all_rows_for_query(query):
        if row[1] != previous_date:
            url = flask.url_for('get_dates', date_id=row[0], _external=True)
            date = {'performance_id': row[0], 'date': row[1], 'url': url}
            date_list.append(date)
            previous_date = row[1]

    return json.dumps(date_list)

@app.route('/composers')
def get_composers():
    '''
    Returns the list of composers in the database. A composer
    resource will be represented by a JSON dictionary with keys 
    'id' (int), 'name' (int), and 'url' (string). The value 
    associated with 'url' is a URL you can use to retrieve this
    same composer in the future.
    '''
    query = 'SELECT id, name FROM composers ORDER BY name'

    composer_list = []
    for row in _fetch_all_rows_for_query(query):
        url = flask.url_for('get_composers', composer_id=row[0], _external=True)
        composer = {'composer_id': row[0], 'composer_name': row[1], 'url': url}
        composer_list.append(composer)

    return json.dumps(composer_list)

@app.route('/performances')
def get_performance():
    '''
    Returns the list of performances that match these (optional) GET parameters:

        start_date, String: reject any performance released earlier than this year
        end_date, String: reject any performance released later than this year
        conductor, int: reject any performance that did not feature this conductor
        venue, int: reject any performance that did not take place at this venue
        location, int: reject any performance that did not take place at this location*
        piece, int: reject any performance that did not feature this piece
        composer, int: reject any performance that does not feature this composer*
        soloist, int: reject any performance that did not feature this soloist
        instrument, int: reject any performance that did not feature this instrument*

    *For performances that include one of these parameters, it can coincide with its
    parent parameter, but it must be correct and will take precedence over

    If a GET parameter is absent, then any performance is treated as the following:

        start_date:
                Year: 1842
               Month: 12
                 Day: 07
        end_date: 
                Year: 2017
               Month: 07
                 Day: 07

    The dates will be auto-formatted in the following order:

                YYYY-MM-DD or YYYY/MM/DD

    Example URL:
                http://perlman.mathcs.carleton.edu:5122/performances?start_date=1929-12-12&end_date=1929-12-28
    '''		
    query = 'SELECT t1.id, t1.date, t1.venue_id, t1.conductor_id, t1.piece_id, t1.soloist_id, t2.name, t3.name, t4.name, t5.name FROM performances t1 INNER JOIN venues t2 ON t1.venue_id = t2.id INNER JOIN conductors t3 ON t1.conductor_id = t3.id INNER JOIN pieces t4 ON t1.piece_id = t4.id INNER JOIN soloists t5 ON t1.soloist_id = t5.id'
    rows = _fetch_all_rows_for_query(query)

    previous_id = 0
    performance_list = []
    current_performance_soloists = []
    add_current_performance = False
    start_date = flask.request.args.get('start_date', default = '1842-12-07')
    end_date = flask.request.args.get('end_date', default = '2017-07-07')
    conductor = flask.request.args.get('conductor', type = int)
    venue = flask.request.args.get('venue', type = int)
    location= flask.request.args.get('location', type = int)
    piece = flask.request.args.get('piece', type = int)
    composer = flask.request.args.get('composer', type = int)
    soloist = flask.request.args.get('soloist', type = int)
    instrument = flask.request.args.get('instrument', type = int)

    for row in rows:
        if row[0] != previous_id:
            add_current_performance = False
            previous_id = row[0]
            current_performance_soloists = []

        if add_current_performance:
            performance_list[len(performance_list) - 1]['soloist_name'].append(row[9])
            continue

        current_performance_soloists.append(row[9])

        if conductor is not None and conductor != row[3]:
            continue
        if venue is not None and venue != row[2]:
            continue
        if location is not None and location != row[2]:
            continue
        if piece is not None and piece != row[4]:
            continue
        if composer is not None and composer != row[4]:
            continue
        if soloist is not None and soloist != row[5]:
            continue
        if instrument is not None and instrument != row[5]:
            continue
        if row[1] < start_date:
            continue
        if row[1] > end_date:
            continue

        add_current_performance = True
        url = flask.url_for('get_performance', performance_id=row[0], _external=True)

        performance = {'performance_id': row[0], 'performance_date': row[1], 'venue_name': row[6], 'conductor_name': row[7], 'piece_name': row[8], 'soloist_name': current_performance_soloists, 'url': url}
        performance_list.append(performance)


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
        print('Usage: {0} hos tport'.format(sys.argv[0]))
        print('	Example: {0} perlman.mathcs.carleton.edu 5101'.format(sys.argv[0]))
        exit()

    host = sys.argv[1]
    port = int(sys.argv[2])
    app.run(host=host, port=port, debug=True)

