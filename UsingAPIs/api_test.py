#!/usr/bin/env python3
'''
        irint(args.country)
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
    data_from_server = urllib.request.urlopen(request).read()
    string_from_server = data_from_server.decode('utf-8')
    track_analysis_dict = json.loads(string_from_server)
    result_list = []

    #Retrieving the Requested from API's Return Dictionary (must be in str)
    song_tempo = str(track_analysis_dict['track']['tempo'])
    song_key = str(track_analysis_dict['track']['key'])

    if type(song_id) != type(''):
        raise Exception('song_id has wrong type: "{0}"'.format(song_id))
    if type(song_tempo) != type(''):
        raise Exception('song_tempo has wrong type: "{0}"'.format(song_tempo))
    if type(song_key) != type(''):
        raise Exception('song_key has wrong type: "{0}"'.format(song_key))

    result_list.append({'song_id': song_id, 'song_tempo':song_tempo, 'song_key':song_key})
    return result_list

def get_top_tracks (artist_id, country, token):
	
    base_url = 'https://api.spotify.com/v1/artists/{0}/top-tracks?country={1} -H "Authorization: Bearer {2}"'
    url = base_url.format(artist_id, country, token)
    track_list_string = subprocess.getoutput("GET " + url) 
    track_list = json.loads(track_list_string)
    result_list = []
    track_list = track_list['tracks']
    for track_dictionary in track_list:
        name = track_dictionary['name']
        popularity = track_dictionary['popularity']
        album_name = track_dictionary['album']['name']
        result_list.append({'name':name, 'popularity':popularity, 'album':album_name})

    return result_list

def search_id(term, category):

    base_url = 'https://api.spotify.com/v1/search?q={0}&type={1}{2}'
    #Getting spaces and additional characters into the correct format for url
    term = urllib.parse.quote(' '.join(str(x) for x in term).encode('utf-8'))
    category = urllib.parse.quote(','.join(str(x) for x in category).encode('utf-8'))
    limit ='&limit=5'
    url = base_url.format(term, category,limit)
    request = urllib.request.Request(url)
    string_header_value1 = 'application/json'
    string_header_value2 = 'application/json'
    string_header_value3 = 'Bearer ' + getToken()
    request.add_header('Accept', string_header_value1)
    request.add_header('Content-Type', string_header_value2)
    request.add_header('Authorization', string_header_value3)
    data_from_server = urllib.request.urlopen(request).read()
    string_from_server = data_from_server.decode('utf-8')
    results = json.loads(string_from_server)
    result_list = []

    #Parse out the correct values from the dictionary
    list_of_tracks = results['tracks']
    track_of_list = list_of_tracks['items']
    
    for trackPackages in track_of_list:
        album = trackPackages['album']
        artists = album['artists']
        album_name = album['name']
        album_release_date = album['release_date']
        track_name = trackPackages['name']
        popularity = trackPackages['popularity']
        track_id = trackPackages['id']

        for artist_Dictionary in artists:
            artist_name = artist_Dictionary['name']
            artist_id = artist_Dictionary['id']
            result_list.append({'track_id': track_id, 'track_name': track_name,
                'track_release_date': album_release_date,'artist_name': artist_name,
                'artist_id': artist_id, 'album_name': album_name, 'popularity': popularity})
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
        song_list = get_audio_analysis(args.id)
        for track_dictionary in song_list:
            song_track = track_dictionary['song_id']
            song_tempo = track_dictionary['song_tempo']
            song_key = track_dictionary['song_key']
            print('Song tempo: {1}\nSong key: {2}'.format(song_track, song_tempo, song_key))

    elif args.request == 'top':
        tracks = get_top_tracks(args.id[0], args.id[1], getToken())
        for track in tracks:
            song_track = track['name']
            song_popularity = track['popularity']
            song_album = track['album']
            print('Track: {0}\nAlbum: {1}\nSpotify popularity rating: {2}\n'.format(song_track, song_album, song_popularity))

    elif args.request == 'search':
        search_result = search_id(args.id, args.category) 
        for track_info in search_result:
            album_name = track_info['album_name']
            popularity = track_info['popularity']
            song_name = track_info['track_name']
            song_id = track_info['track_id']
            song_release_date = track_info['track_release_date']
            artist_name = track_info['artist_name']
            artist_id = track_info['artist_id']
            print('{0}. From the album {1} by {2} that was released in {3}:\n    Song Name: {4}\n    SongID: {5}\n    ArtistID: {6}\n'.format(popularity, album_name, artist_name, song_release_date, song_name, song_id, artist_id))

if __name__ == '__main__':

    parser = argparse.ArgumentParser(description='Get information from the Spotify API about tracks, artists, and more!')

    request = parser.add_argument('request',
                        help='request a information about an artist or a song ("analyze","top", or "search")',
                        choices=['analyze', 'top', 'search'])

    parser.add_argument('id',
                        nargs = '*',
                        help='the id of what information is being requested about')

    requiredIdentify =  parser.add_argument_group('flags required for search request')
    
    requiredTop = parser.add_argument_group('flags for top request')

    requiredIdentify.add_argument('-cat','--category', 
                        metavar = 'CATEGORIES',
                        dest = 'category',
                        nargs = '+',
                        help = 'the medium of the content that is being identified',
                        choices = ['album', 'artist', 'playlist', 'track'])

    requiredTop.add_argument('country',
		        metavar= 'COUNTRYID',
                        nargs = '?',
                        help='the market a particular artist\'s songs were popular in')

    args = parser.parse_args()

    requiredNamed = parser.parse_args()

    main(args)
