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

### Search for all performances by location:


------
