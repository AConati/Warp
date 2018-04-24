# Phase 3: List of API Endpoints

### Authors: Ari Conati & Grant Lee

Our dataset is fairly large and we feel that we should try to begin with a smaller portion of the data and then finally adapt to a larger data set <br>

**Basic Data Format**:
|Date|Location|Venue|Composer|Conductor|Movement|Work Title|Soloist Instrument|Soloist Name|
|:--:|:------:|:---:|:------:|:-------:|:------:|:--------:|:----------------:|:----------:|
|1842-12-07T05:00:00Z|Manhattan, NY|Apollo Rooms|Beethoven,  Ludwig  van|Hill, Ureli Corelli|SYMPHONY NO. 5 IN C MINOR, OP.67|Cello|Boucher, Alfred|

**Relationship of Each Field**:


* Performance
	* Venue : Location
	* Conductor
	* Date
	* Work Title : Composer
		* Movement
	* Soloist Name : Soloist Instrument

------

#### Search for a particular composer:
**Endpoint:**/performance/\<composer_id\>\ 
**Summary:** Returns the performances that performed music by the composer\
**Response Format:** A JSON list of composer dictionaries. Each composer dictionary will have the following keys: 'performance_id', 'name', and 'works'. Where 'works' corresponds to list of *work dictionaries*. Each work dictionary will have keys 'title' and 'movements'. Where 'movements' corresponds to a list of 'movement'(s). A typical response to a query of this type will look like this:

```{java}
		{"id": 27, "name": "Ludwig van Beethoven",
			"works": [
				{"id": 18, "title": "Symphony Number 5 in C minor Op.67", 
					"movements" [
						{"id": 21, "name": "I. Allegro con brio"}, 
						{"id": 22, "name": "II. Andante con moto"},
						{"id": 23, "name": "III. Scherzo. Allegro"},
						{"id": 24, "name": "IV. Allegro"}]
				}
				...
					]
		}
```

------

#### Search for all composers:
**Endpoint:**/performance/composers 
**Summary:** Returns all composers in the data base
**Response Format:** A JSON list of composer dictionaries. Each composer dictionary will have the following keys: 'id', 'name', and 'works'. Where 'works' corresponds to list of *work dictionaries*. Each work dictionary will have keys 'title' and 'movements'. Where 'movements' corresponds to a list of 'movement'(s). A typical response to a query of this type will look like this:

```{java}
		{"id": 27, "name": "Ludwig van Beethoven",
			"works": [
				{"id": 18, "title": "Symphony Number 5 in C minor Op.67", 
					"movements" [
						{"id": 21, "name": "I. Allegro con brio"}, 
						{"id": 22, "name": "II. Andante con moto"},
						{"id": 23, "name": "III. Scherzo. Allegro"},
						{"id": 24, "name": "IV. Allegro"}]
				}
				...
					]
		}
```



