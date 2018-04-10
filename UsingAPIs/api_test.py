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

def get_root_words(word, language):
    '''
    Returns a list of root words for the specified word in the
    specified language. The root words are represented as
    dictionaries of the form
    
       {'root':root_word, 'partofspeech':part_of_speech}

    For example, the results for get_root_words('thought', 'eng')
    would be:

       [{'root':'thought', 'partofspeech':'noun'},
        {'root':'think', 'partofspeech':'verb'}]

    The language parameter must be a 3-letter ISO language code
    (e.g. 'eng', 'fra', 'deu', 'spa', etc.).

    Raises exceptions on network connection errors and on data
    format errors.
    '''
    base_url = 'http://developer.ultralingua.com/api/stems/{0}/{1}'
    url = base_url.format(language, word)
    data_from_server = urllib.request.urlopen(url).read()
    string_from_server = data_from_server.decode('utf-8')
    root_word_list = json.loads(string_from_server)
    result_list = []
    for root_word_dictionary in root_word_list:
        root = root_word_dictionary['text']
        part_of_speech = root_word_dictionary['partofspeech']
        part_of_speech_category = part_of_speech['partofspeechcategory']
        if type(root) != type(''):
            raise Exception('root has wrong type: "{0}"'.format(root))
        if type(part_of_speech_category) != type(''):
            raise Exception('part of speech has wrong type: "{0}"'.format(part_of_speech))
        result_list.append({'root':root, 'partofspeech':part_of_speech_category})
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
    if args.action == 'root':
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
    # When I use argparse to parse my command line, I usually
    # put the argparse setup here in the global code, and then
    # call a function called main to do the actual work of
    # the program.
    parser = argparse.ArgumentParser(description='Get word info from the Ultralingua API')

    parser.add_argument('action',
                        metavar='action',
                        help='action to perform on the word ("root" or "conjugate")',
                        choices=['root', 'conjugate'])

    parser.add_argument('language',
                        metavar='language',
                        help='the language as a 3-character ISO code',
                        choices=['eng', 'fra', 'spa', 'deu', 'ita', 'por'])

    parser.add_argument('word', help='the word you want to act on')

    args = parser.parse_args()
    main(args)
