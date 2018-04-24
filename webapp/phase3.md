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

### Search for all performances by location:




------
