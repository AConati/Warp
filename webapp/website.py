#!/usr/bin/env python3
'''
    books_api.py
    Ari Conati & Grant Lee
    9 May 2018

    Simple Flask app used in the sample web app for
    CS 257, Spring 2018. This is the Flask app for the
    "NY Phil" API and website. The API offers
    JSON access to the data, while the website (at
    route '/') offers end-user browsing of the data.
'''
import sys
import flask

app = flask.Flask(__name__, static_folder='static', template_folder='templates')

@app.route('/') 
def get_main_page():
    ''' This is the only route intended for human users '''
    global api_port
    return flask.render_template('performers.html', api_port=api_port)

@app.route('/results')
def get_results_page():
    global api_port
    return flask.render_template('results.html', api_port=api_port)

@app.route('/search')
def get_search_page():
    global api_port
    return flask.render_template('searchPage.html', api_port=api_port)

@app.route('/composers')
def get_composers():
    global api_port
    return flask.render_template('results.html', api_port=api_port)

@app.route('/soloists')
def get_soloists():
    global api_port
    return flask.render_template('results.html', api_port=api_port)

@app.route('/conductors')
def get_conductors():
    global api_port
    return flask.render_template('results.html', api_port=api_port)

@app.route('/locations')
def get_locations():
    global api_port
    return flask.render_template('results.html', api_port=api_port)

@app.route('/venues')
def get_venues():
    global api_port
    return flask.render_template('results.html', api_port=api_port)

@app.route('/instruments')
def get_instruments():
    global api_port
    return flask.render_template('results.html', api_port=api_port)

@app.route('/pieces')
def get_pieces():
    global api_port
    return flask.render_template('results.html', api_port=api_port)

if __name__ == '__main__':
    if len(sys.argv) != 4:
        print('Usage: {0} host port api-port'.format(sys.argv[0]), file=sys.stderr)
        exit()

    host = sys.argv[1]
    port = sys.argv[2]
    api_port = sys.argv[3]
    app.run(host=host, port=port)


