# Phase 3: List of API Endpoints

### Authors: Ari Conati & Grant Lee

Our dataset is fairly large and we feel that we should try to begin with a smaller portion of the data and then finally adapt to a larger data set <br>

**Basic Data Format**:

| Date | Location | Venue | Composer | Conductor | Movement | Work Title | Soloist Instrument | Soloist Name |
|:--:|:------:|:---:|:------:|:-------:|:------:|:--------:|:----------------:|:----------:|
|1842-12-07T05:00:00Z|Manhattan, NY|Apollo Rooms|Beethoven,  Ludwig  van|Hill, Ureli Corelli|I. Allegro con brio|SYMPHONY NO. 5 IN C MINOR, OP.67|Cello|Boucher, Alfred|

**Relationship of Each Field**:


* Performance
	* Venue >---- Location
	* Conductor
	* Date
	* Work Title >----  Composer
		* Movement
	* Soloist Name-Soloist Instrument

------

### Searching for a specific performance:


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

### Search for all performances by location:

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
