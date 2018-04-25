# Phase 3: List of API Endpoints

### Authors: Ari Conati & Grant Lee

Our dataset is fairly large and we feel that we should try to begin with a smaller portion of the data and then finally adapt to a larger data set <br>

**Basic Data Format**:

| Date | Location | Venue | Composer | Conductor | Movement | Work Title | Soloist Instrument | Soloist Name |
|:--:|:------:|:---:|:------:|:-------:|:------:|:--------:|:----------------:|:----------:|
|1842-12-07T05:00:00Z|Manhattan, NY|Apollo Rooms|Beethoven,  Ludwig  van|Hill, Ureli Corelli|I. Allegro con brio|SYMPHONY NO. 5 IN C MINOR, OP.67|Cello|Boucher, Alfred|

**Relationship of Each Field**:


* Performance
	* Venue >---- Location (one-to-many:bidirectional)
	* Conductor
	* Date
	* Piece Performed >----<  Composer (many-to-many:bidirectional)
	* Soloist Name >---- Soloist Instrument (one-to-many:bidirectional)

------

### Searching for a specific performance:   
**Endpoint:**/performances?venue=\<venue_name\>&location=\<location_name\>&conductor=\<conductor_name\>&date_start=\<start_date\>&end_date=\<end_date\>&piece_performed=\<piece_performed\>&compose=\<compose\>&soloist=\<soloist\>&soloist_instrument=\<soloist_instrument\>   
**Summary:** This is the meat and bones of all the queries. For the following queries, this will be used to extract all performances that correspond to the given search term. It returns a list of performances that correspond to the specified filters\*. The response format will be a list of json dictionaries called performance which has only one key which is "id". There is then a *venue* dictionary in the *performance* dictionary which has the keys: "venue_id", "venue_name". There is then a *location* dictionary that is inside the *venue* dictionary with the keys: "location_id" and "location_name". There is another *conductor* dictionary in the *performance* dictionary with the keys: "conductor_id" and "conductor_name".There is another *piece_performed* dictionary which will be a dictionary with the keys: "piece_performed_id" and "piece_performed_name" and in the *piece_performed* dictionary there will be a *composer* dictionary which will have the keys: "composer_id" and "composer_name". Another included in the *performance* dictionary is the *soloist* dictionary which will have the keys: "soloist_id", "soloist_name", and in the *soloist* dictionary there will be *soloist_instrument* dictionary which will have the keys: "soloist_instrument_id" and "soloist_instrument_name". * \*When a given filter is not applied it will be treated as a wildcard. A date will be considered and processed based on the number of '/' or '-' included in the query. *    
**Response Format:**   
```{java}

performance:	
		[
			{"id": 27,
				"dates":  
					{"id": 18, "date_name": 10/29/70},
				"conductor": 
					{"id": 12, "conductor_name": "Herbert von Karajan"},
				"venue": 
					{"id": 13, "venue_name": "Musikverein", 
						"location": 
							{"id": 21, "location_name": "Vienna, Austria"}
					},
				 "piece_performed": 
					{"id": 12, "piece_performed_name": "Piano Concerto No. 20 in D minor, K.466", 
						"composer":
							{"id": 23, "composer_name": "Wolfgang Amadeus Mozart"}
					} 
				 "soloist":
					{"id": 25, "soloist_name": "Arthur Rubinstein", 
						"soloist_instrument":
							{"id": 1, "soloist_instrument": "piano"}
			}
		]
```

------


### Search for all pieces performed by NY Philharmonic:

**Endpoint:** /pieces  
**Summary:** Returns the complete list of pieces_performed contained in the database.   
**Response format:** A JSON list of piece dictionaries. Each piece dictionary will have
the keys "id", "title", and "composer". A typical response to a query will look like this:

[

    {"id": 1, "title": "Symphony No. 5 in C Minor, Op. 67",
        "composer": "Beethoven, Ludwig Van"},
    {"id": 2, "title": "Piano Quintet, D Minor, Op.74",
        "composer": "Hummel, Johann"},
    ...
]

------

### Search for all soloists who have performed with NY Philharmonic:

**Endpoint:** /soloists  
**Summary:** Returns the complete list of soloists contained in the database.  
**Response format:** A JSON list of soloist dictionaries. Each soloist dictionary will have
the keys "id", "name", and "instrument". A typical response to a query will look like this:

[

    {"id": 1, "name": "Scharfenberg, William",
        "instrument": "piano"},
    {"id": 2, "name": "Boucher, Alfred",
        "instrument": "cello"},
    ...
]

------

### Search for all conductors who have conducted NY Philharmonic:

**Endpoint:** /conductors  
**Summary:** Returns the complete list of conductors contained in the database.  
**Response format:** A JSON list of conductor dictionaries. Each conductor dictionary will have
the keys "id" and "name".


[

    {"id": 1, "name": "Hill, Ureli Corelli"},
    {"id": 2, "name": "Timm, Henry C."},

    ...
]

------

### Search for all instruments which have had solo parts in NY Philharmonic performances:

**Endpoint:** /soloist_instruments  
**Summary:** Returns the complete list of soloists in the database which play the given instrument.  
**Response format:** A JSON list of soloist dictionaries. Each soloist dictionary will have
the keys "id", "name", and "instrument". A typical response to a query will look like this:

[

    {"id": 1, "name": "Scharfenberg, William",
        "instrument": "piano"},
    {"id": 2, "name": "Boucher, Alfred",
        "instrument": "cello"},
    ...
]

------

### Search for all venues that the NY Philharmonic has performed at

**Endpoint:** /venues  
**Summary:** Returns the complete list of venues in the database.  
**Response format:** A JSON list of venue dictionaries. Each venue dictionary will have the keys
"id", "name", and "location". A typical response to a query will look like this:

[

    {"id": 1, "name": "Apollo Rooms",
        "location": "Manhattan, NY"},
    {"id": 2, "name": "Teatro Teresa Carreno",
        "location": "Caracas, Venezuela"},
    ...
]

------

### Search for all performences by location:

**Endpoint:** /locations  
**Summary:** Returns the complete list of locations in the database.  
**Response format:** A JSON list of location dictionaries. Each location dictionary will have the keys
"id" and "location". A typical response to a query will look like this:

[

    {"id": 1, "location": "Manhattan, NY"},
    {"id": 2, "location": "Caracas, Venezuela"},
    ...
]

------

### Search for all dates on which the NY Philharmonic performed

**Endpoint:** /dates  
**Summary:** Returns the complete list of dates in the database.  
**Response format:** A JSON list of date dictionaries. Each date dictionary will have the keys
"id" and "date". A typical response to a query will look like this:

[

    {"id": 1, "date": "1842-12-07"},
    {"id": 2, "date": "1843-02-18"}

]

------

### API endpoints which correspond to user stories:

* Person looks up a performances on a specific date (Date)
* Person looks up a performance by the specific piece performed (Piece)
* Person searches for all performances at a specific venue (Venue)
* Person searches for all performances that featured a specific solo instrument (Solo Instrument)
* Person searches for all performances that features a specific solo artist (Solo Artist)
* Person searches for all performances that features a specific conductor (Conductor)
* Person searches for all performances that took place at a specific location (Location)
* Person searches for all performances that features a specific of conductors (Conductors)
* *Person makes a search that uses multiple search terms from the list above...*

*All of these user stories by the endpoint 
/performances?venue=\<venue_name\>&location=\<location_name\>&conductor=\<conductor_name\>&date_start=\<start_date\>&end_date=\<end_date\>&piece_performed=\<piece_performed\>&compose=\<compose\>&soloist=\<soloist\>&soloist_instrument=\<soloist_instrument\>
 which returns performance objects which satisfy the get parameters of the query.*

* Person searches for all locations the NYP has performed at (location)
* Person searches for all venues the NYP has performed at (venue)
* Person searches for all pieces the NYP has performed (piece)
* Person searches for all dates the NYP has peformed on (date)
* Person searches for all soloists that have performed with NYP (soloist)
* Person searches for conductors that have conducted NYP (conductor)
* Person searches for all instruments that had solo parts (soloist instrument)

The requirements of these user stories are satisfied by the endpoints /locations, /venues, /pieces, /dates,
/soloists, /conductors, and /soloist_instruments, respectively.
