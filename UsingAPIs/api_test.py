#!/usr/bin/env python3
'''
    api_test.py
    Ari Conati & Grant Lee, 09 April 2018

    An example for CS 257 Software Design. How to retrieve results
    from an HTTP-based API, parse the results (JSON in this case),
    and manage the potential errors.
'''

import subprocess
import sys
import argparse
import json
import urllib.request
import urllib.parse
import base64

def get_audio_analysis(song_id):
    '''
    Returns a detailed audio analysis for a single track
    identified by a unique Spotify ID.

    When the request is successful, the HTTP status code 
    200 OK will be returned. 

    This will return a JSON object that includes the beats, metadata
    bars, sections, segments, tatums, and track in the following 
    style:
	
	{"bars": [{"start": 251.98282, "duration": 0.29765, "confidence": 0.652}],"meta":{
	"analyzer_version:": "4.0.0", "platform": "Linux"...}}
    
    The parameter "song_id" is a long combination of capital letters,
    lowercase letters, and numbers that can be found at the end of 
    a song's url. For example the URL for the song "Sympathy for 
    the Devil" is https://open.spotify.com/track/1iDcKYNvo6gglrOG6lvnHL.
    The song's song id would be: 1iDcKYNvo6gglrOG6lvnHL.
	
    When the request is called incorrectly an error code and error object
    will most likely be returned.
    '''
    base_url = 'https://api.spotify.com/v1/tracks/{0}'
    url = base_url.format (song_id)
    url_request = urllib.request.Request(url)
    url_request.add_header('Authorization', ('Bearer' + getToken()))
    print(url_request)
    with urllib.request.Request(url_request) as response:
        data_from_server = response.read()
    string_from_server = data_from_server.decode('utf-8')
    track_analysis_list = json.loads(string_from_server)
    result_list = []

    for track_analysis_dictionary in track_analysis_list:
        song_track = song_analysis_dictionary['track']
        song_tempo = song_track['tempo']
        song_key = song_track['key']
        
        if type(song_id) != type(''):
            raise Exception('song_id has wrong type: "{0}"'.format(song_id))
        if type(song_tempo) != type(''):
            raise Exception('song_tempo has wrong type: "{0}"'.format(song_tempo))
        if type(song_key) != type(''):
            raise Exception('song_key has wrong type: "{0}"'.format(song_key))

        result_list.append({'Song ID':song_id, 'Tempo of Song':song_tempo, 'Key of Track':song_key})
    return result_list

def get_top_tracks (artist_id, country, token):
	
    base_url = 'https://api.spotify.com/v1/artists/{0}/top-tracks?country={1} -H "Authorization: Bearer {2}"'
    url = base_url.format(artist_id, country, token)
    track_list_string = subprocess.getoutput("GET " + url)
    
    #The dictionary returned uses false, true instead of False, True - change to correct version so Python can eval
    track_list_string = track_list_string.replace("false", "False")
    track_list_string = track_list_string.replace("true", "True")
    
    #Evaluate the output and store it as a dictionary variable
    track_list = eval(track_list_string)
    
    result_list = []
    track_list = track_list['tracks']
    for track_dictionary in track_list:
        name = track_dictionary['name']
        popularity = track_dictionary['popularity']
        result_list.append({'name':name, 'popularity':popularity})

    return result_list

def getToken():

    #Code for creating an base64 encoded 
    client_id = '4d6407fff77d422888b19cbe554c1a46'
    client_secret = '8b53b7d5ab2c4df5b3a4330840db39d1'
    client_info = client_id + ':' + client_secret
    client_info_bytes = client_info.encode('utf-8')
    client_info_string = base64.b64encode(client_info_bytes).decode('utf-8')

    authorization_value = 'Basic ' + client_info_string

    url = 'https://accounts.spotify.com/api/token'
    headers = {'Authorization': authorization_value}
    values = {'grant_type': 'client_credentials'}

    data = urllib.parse.urlencode(values)
    data = data.encode('utf-8')
    request = urllib.request.Request(url, data, headers)
    with urllib.request.urlopen(request) as response:
        the_result = response.read()
    list_of_auth = json.loads(the_result.decode('utf-8'))
    token = list_of_auth['access_token']
    return token



def main(args):

    if args.request == 'analyze':
        song_analysis = get_audio_analysis(args.id)
        for track_info in song_analysis:
            song_track = track_info['track']
            song_tempo = track_info['tempo']
            song_key = track_info['key']
            print('{0} [{1} {2}]'.format(song_track, song_tempo, song_key))

    elif args.request == 'top':
        tracks = get_top_tracks(args.id, args.country, args.token)
        for track in tracks:
            song_track = track['name']
            song_popularity = track['popularity']  
            print('{0} - popularity rating: {1}'.format(song_track, song_popularity))
    
if __name__ == '__main__':

    parser = argparse.ArgumentParser(description='Get song info from Spotify API')

    parser.add_argument('request',
                        metavar='request',
                        help='request a information about an artist or a song ("analyze" or "top")',
                        choices=['analyze', 'top'])

    parser.add_argument('id',
                        metavar='id',
                        help='the id of what information is being requested about')

    parser.add_argument('--country',
			required = 'top' in sys.argv,
		        help='the country of the market a particular artist\'s songs were popular in')

    args = parser.parse_args()                  

    main(args)
