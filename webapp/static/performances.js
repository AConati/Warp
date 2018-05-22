/* performances.js
 * Ari Conati & Grant Lee
 * May 9 2018
 *
 * A little bit of Javascript showing one small example of AJAX
 * within the "NY Phil: Perfinder" sample for Carleton CS257,
 * Spring Term 2018.
 *
 */

var performances_url = '';
initialize();

function initialize() {

 
        performances_url = localStorage.getItem("url");
        var search_web = window.location.href;
        var search_query = search_web.substring(search_web.indexOf("21/") + 3);
        if(search_web.includes('?')) {
            var searchCriteria = document.getElementById('search_criteria');
            if(searchCriteria) {
                searchCriteria.innerHTML = localStorage.getItem("results_search_criteria");
            }
            get_Specific_Results(performances_url);
        } else {
            get_General_Results(search_query);
        }
    }

/*
    var soloists= document.getElementById('soloists_button');
    if (soloists) {
        soloists.onclick = onSoloistsButtonClicked;
    }
    var conductors = document.getElementById('conductors_button');
    if (conductors) {
        conductors.onclick = onConductorsButtonClicked;
    }
    var instruments = document.getElementById('instruments_button');
    if (instruments) {
        instruments.onclick = onInstrumentsButtonClicked;
    }
    var venues = document.getElementById('venues_button');
    if (venues) {
        venues.onclick = onVenuesButtonClicked;
    }
    var locations = document.getElementById('locations_button');
    if (locations) {
       locations.onclick = onLocationsButtonClicked;
    }
    var dates = document.getElementById('dates_button');
    if (dates) {
        dates.onclick = onDatesButtonClicked;
    }
    var composers = document.getElementById('composers_button');
    if (composers) {
        composers.onclick = onComposersButtonClicked;
    }
    var performances = document.getElementById('performances_button');
    if (performances) {
        performances.onclick = onPerformancesButtonClicked;
    }
    */
/*
 * @Return base url for api
*/

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

//Create Table with All Soloists
function onSoloistsButtonClicked() {
    var url = getBaseURL() + '/soloists/';

    // Send the request to the Books API /soloists/ endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(soloistList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Name</th><th>Instrument</th></tr>';
                for (var k = 0; k < soloistList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += soloistList[k]['soloist_id'] + '</td><td>'
                + soloistList[k]['soloist_name'] + '</td><td>'
                + soloistList[k]['soloist_instrument'] + '</td>';
                tableBody += '</tr>';
                }
                

                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}

//Function for Pieces
function onPiecesButtonClicked() {
    var url = getBaseURL() + '/pieces/';

    // Send the request to the Books API /pieces/ endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(pieceList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Name</th><th>Composer</th></tr>';
                for (var k = 0; k < pieceList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += pieceList[k]['piece_id'] + '</td><td>'
                + pieceList[k]['piece_name'] + '</td><td>'
                + pieceList[k]['composer_name'] + '</td>';
                tableBody += '</tr>';
                }
                

                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}

//Function for Conductors
function onConductorsButtonClicked() {
    var url = getBaseURL() + '/conductors';

    // Send the request to the Books API /conductor endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(conductorList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Name</th></tr>';
                for (var k = 0; k < conductorList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += conductorList[k]['conductor_id'] + '</td><td>'
                + conductorList[k]['conductor_name'] + '</td>';
                tableBody += '</tr>';
                }
                

                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}

//Function for Instruments
function onInstrumentsButtonClicked() {
    var url = getBaseURL() + '/instruments';

    // Send the request to the Books API /instruments endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(instrumentList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Name</th></tr>';
                for (var k = 0; k < instrumentList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += instrumentList[k]['instrument_id'] + '</td><td>'
                + instrumentList[k]['instrument_name'] + '</td>';
                tableBody += '</tr>';
                }
                

                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}

//Function for Venues
function onVenuesButtonClicked() {
    var url = getBaseURL() + '/venues/';

    // Send the request to the Books API /venues/ endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(venueList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Name</th><th>Location</th></tr>';
                for (var k = 0; k < venueList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += venueList[k]['venue_id'] + '</td><td>'
                + venueList[k]['venue_name'] + '</td><td>';
                tableBody += venueList[k]['venue_location'] + '</td>'
                tableBody += '</tr>';
                }
                

                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}

//Function for Locations
function onLocationsButtonClicked() {
    var url = getBaseURL() + '/locations';

    // Send the request to the Books API /locations endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(locationList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Name</th></tr>';
                for (var k = 0; k < locationList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += locationList[k]['location_id'] + '</td><td>'
                + locationList[k]['location_name'] + '</td>';
                tableBody += '</tr>';
                }
                

                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}

//Function for Dates
function onDatesButtonClicked() {
    var url = getBaseURL() + '/dates';

    // Send the request to the Books API /dates endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(dateList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Date</th></tr>';
                for (var k = 0; k < dateList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += dateList[k]['performance_id'] + '</td><td>'
                + dateList[k]['date'] + '</td>';
                tableBody += '</tr>';
                }
                
                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}

//Function for Composers
function onComposersButtonClicked() {
    var url = getBaseURL() + '/composers';

    // Send the request to the Books API /composers endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(composerList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Name</th></tr>';
                for (var k = 0; k < composerList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += composerList[k]['composer_id'] + '</td><td>'
                + composerList[k]['composer_name'] + '</td>';
                tableBody += '</tr>';
                }
                
                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(rror);
            });
}

//Function for Performances
function onPerformancesButtonClicked() {
    var url = getBaseURL() + '/performances';

    // Send the request to the Books API /performances endpoint
    fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        // Once you have your list of author dictionaries, use it to build
        // an HTML table displaying the author names and lifespan.
        .then(function(performanceList) {
                // Build the table body.
                var tableBody = '<tr><th>ID</th><th>Date</th><th>Venue</th><th>Conductor</th><th>Piece</th><th>Soloists</th></tr>';
                for (var k = 0; k < performanceList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += performanceList[k]['performance_id'] + '</td><td>'
                + performanceList[k]['performance_date'] + '</td><td>' 
                + performanceList[k]['venue_name'] + '</td><td>'
                + performanceList[k]['conductor_name'] + '</td><td>'
                + performanceList[k]['piece_name'] + '</td><td>' 
                + performanceList[k]['soloist_name'] + '</td>';
                tableBody += '</tr>';
                }
                
                // Put the table body we just built inside the table that's already on the page.
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}
/*
 * Creates a table with all elements of a given type. (eg. composers)
 *
 * @Param query_type The type of the element to be printed.
*/

function get_General_Results(query_type) {

    if(query_type === "composers") {
        var url = getBaseURL() + '/composers';
    
        // Send the request to the Books API /composers endpoint
        fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())

        .then(function(composerList) {
            // Build the table body.
            var tableBody = '<tr><th>ID</th><th>Name</th></tr>';
            for (var k = 0; k < composerList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += composerList[k]['composer_id'] + '</td><td>'
                + composerList[k]['composer_name'] + '</td>';
                tableBody += '</tr>';
            }

            // Put the table body we just built inside the table that's already on the page.
            var resultsTableElement = document.getElementById('results_table');
            if (resultsTableElement) {
                resultsTableElement.innerHTML = tableBody;
            }
        })
    }
    
    else if(query_type === "conductors") {

        var url = getBaseURL() + '/conductors';

        fetch(url, {method: 'get'})

        // When the results come back, transform them from JSON string into
        // a Javascript object (in this case, a list of author dictionaries).
        .then((response) => response.json())
        .then(function(conductorList) {
            // Build the table body.
            var tableBody = '<tr><th>ID</th><th>Name</th></tr>';
            for (var k = 0; k < conductorList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += conductorList[k]['conductor_id'] + '</td><td>'
                + conductorList[k]['conductor_name'] + '</td>';
                tableBody += '</tr>'; }


            // Put the table body we just built inside the table that's already on the page.
            var resultsTableElement = document.getElementById('results_table');
            if (resultsTableElement) {
                resultsTableElement.innerHTML = tableBody;
            }
        })
    }
    
    else if(query_type === "soloists") {
        
        var url = getBaseURL() + '/soloists/';

        fetch(url, {method: 'get'})

        .then((response) => response.json())
        .then(function(soloistList) {
            // Build the table body.
            var tableBody = '<tr><th>ID</th><th>Name</th><th>Instrument</th></tr>';
            for (var k = 0; k < soloistList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += soloistList[k]['soloist_id'] + '</td><td>'
                + soloistList[k]['soloist_name'] + '</td><td>'
                + soloistList[k]['soloist_instrument'] + '</td>';
                tableBody += '</tr>';
            }


            // Put the table body we just built inside the table that's already on the page.
            var resultsTableElement = document.getElementById('results_table');
            if (resultsTableElement) {
                resultsTableElement.innerHTML = tableBody;
            }
        })
    }

    else if(query_type === "instruments") {

        var url = getBaseURL() + '/instruments';

        fetch(url, {method: 'get'})

            .then((response) => response.json())

            .then(function(instrumentList) {
                    // Build the table body.
                    var tableBody = '<tr><th>ID</th><th>Name</th></tr>';
                    for (var k = 0; k < instrumentList.length; k++) {
                    tableBody += '<tr>';
                    tableBody += '<td>';
                    tableBody += instrumentList[k]['instrument_id'] + '</td><td>'
                    + instrumentList[k]['instrument_name'] + '</td>';
                    tableBody += '</tr>';
                    }

                    var resultsTableElement = document.getElementById('results_table');
                    if (resultsTableElement) {
                        resultsTableElement.innerHTML = tableBody;
                    }
            })
    }

    else if(query_type === "pieces") {
        
        var url = getBaseURL() + '/pieces/';
        
            fetch(url, {method: 'get'})
        
                .then((response) => response.json())
        
                .then(function(pieceList) {
                        var tableBody = '<tr><th>ID</th><th>Name</th><th>Composer</th></tr>';
                        for (var k = 0; k < pieceList.length; k++) {
                        tableBody += '<tr>';
                        tableBody += '<td>';
                        tableBody += pieceList[k]['piece_id'] + '</td><td>'
                        + pieceList[k]['piece_name'] + '</td><td>'
                        + pieceList[k]['composer_name'] + '</td>';
                        tableBody += '</tr>';
                        }
                        
                        var resultsTableElement = document.getElementById('results_table');
                        if (resultsTableElement) {
                            resultsTableElement.innerHTML = tableBody;
                        }
                })
    }
}


/*
 * Prints out all performances matching the get parameters specified in the url.
 *
 * @Param performances_url The url which corresponds to an api query to the function
 * get_performances in api.py
 *
*/

function get_Specific_Results(performances_url) {

    fetch(performances_url, {method: 'get'})

        .then((response) => response.json())

        .then(function(performanceList) {
                var tableBody = '<tr><th>ID</th><th>Date</th><th>Venue</th><th>Conductor</th><th>Piece</th><th>Soloists</th></tr>';
                for (var k = 0; k < performanceList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += performanceList[k]['performance_id'] + '</td><td>'
                + performanceList[k]['performance_date'] + '</td><td>' 
                + performanceList[k]['venue_name'] + '</td><td>'
                + performanceList[k]['conductor_name'] + '</td><td>'
                + performanceList[k]['piece_name'] + '</td><td>' 
                + performanceList[k]['soloist_name'] + '</td>';
                tableBody += '</tr>';
                }
                
                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
        })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
            console.log(error);
            });
}
