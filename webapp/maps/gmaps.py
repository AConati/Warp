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
	
	{"bars": [{"start": 251.98282, "duration": 0.29765, "confidence":
        0.652}],"meta":{"analyzer_version:": "4.0.0", "platform": "Linux"...}}
    
    The parameter "song_id" is a long combination of capital letters,
    lowercase letters, and numbers that can be found at the end of 
    a song's url. For example the URL for the song "Sympathy for 
    the Devil" is https://open.spotify.com/track/1iDcKYNvo6gglrOG6lvnHL.
    The song's song id would be: 1iDcKYNvo6gglrOG6lvnHL.
	
    When the request is called incorrectly an error code and error object
    will most likely be returned.
    '''
    #Formatting the URL Request with Header
    base_url = 'https://api.spotify.com/v1/audio-analysis/{0}'
    song_id  = urllib.parse.quote(' '.join(str(x) for x in song_id).encode('utf-8'))
    url = base_url.format(song_id)
    request = urllib.request.Request(url)
    string_header_value3 = 'Bearer ' + getToken()
    request.add_header('Authorization', string_header_value3)
