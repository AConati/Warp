#!/usr/bin/env python3
'''
    api_test.py
    Ari Conati & Grant Lee, 09 April 2018

    An example for CS 257 Software Design. How to retrieve results
    from an HTTP-based API, parse the results (JSON in this case),
    and manage the potential errors.
'''

import sys
import argparse
import json
import urllib.request

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
    data_from_server = urllib.request.urlopen(url).read()
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

def get_top_tracks (artist_id, country):
	
    base_url = 'https://api.spotify.com/v1/artists/{0}/top-tracks?country={1}'
    url = base_url.format(artist_id, country)

    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    track_list = json.loads(string_from_server)

    result_list = []
    for track_dictionary in track_list
        name = track_dictionary['name']
        popularity = track_dictionary['popularity']
	
        result_list.append({'name':name, 'popularity':popularity})

    return result_list

def main(args):
    if args.action == 'analyze':
        root_words = get_root_words(args.word, args.language)
        for root_word in root_words:
            root = root_word['root']
            part_of_speech = root_word['partofspeech']
            print('{0} [{1}]'.format(root, part_of_speech))

    elif args.action == 'conjugate':
        conjugations = get_conjugations(args.word, args.language)
        for conjugation in conjugations:
            text = conjugation['text']
            tense = conjugation['tense']
            person = conjugation['person']
            number = conjugation['number']
            print('{0} [{1} {2} {3}]'.format(text, tense, person, number))
    
if __name__ == '__main__':

    parser = argparse.ArgumentParser(description='Get song info from Spotify API')

    parser.add_argument('request',
                        metavar='request',
                        help='request a information about an artist or a song ("analyze" or "get_top_songs")',
                        choices=['analyze', 'conjugate'])

    parser.add_argument('language',
                        metavar='language',
                        help='the language as a 3-character ISO code',
                        choices=['eng', 'fra', 'spa', 'deu', 'ita', 'por'])

    parser.add_argument('word', help='the word you want to act on')

    args = parser.parse_args()
    main(args)