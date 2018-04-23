# Phase 3: List of API Endpoints

### Authors: Ari Conati & Grant Lee

Our dataset is fairly large and we feel that we should try to begin with a smaller portion of the data and then finally adapt to a larger data set <br>

* **endpoint:**/performance/\<composer_id\> <br>
**summary:** returns the performances that performed music by the composer <br>
**response format:** a JSON list of composer dictionaries. Each composer dictionary will have keys "performance_id", "date_of_performance", "location_of_performance", "birth_year", and "death_year". A typical response to a query of this type will look like this:

```{java}
	[
		{"performance_id": 27, "

