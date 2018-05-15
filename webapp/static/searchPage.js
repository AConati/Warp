
var composerDictionary;
var conductorDictionary;
var pieceDictionary;
var soloistDictionary;
var instrumentDictionary;
var venueDictionary;

initialize();

function initialize() {
    var simpleSearch = document.getElementById('simple_search');
    var performanceSearchButton = document.getElementById("performance_search_button");
    var composerInputField = document.getElementById("composer_input_field");
    var conductorInputField = document.getElementById("conductor_input_field");
    var pieceInputField = document.getElementById("piece_input_field");
    var soloistInputField = document.getElementById("soloist_input_field");
    var instrumentInputField = document.getElementById("instrument_input_field");
    var venueInputField = document.getElementById("venue_input_field");
    var element_type = "composers";
    var getJSON;

    let loadDictionary = function(element_type) {
        var url = getBaseURL() + '/' + element_type;
        var elementList;
        return fetch(url,{method: 'get'}).then(response => {return response.json()})
    }

    composerDictionary = loadDictionary("composers");

    conductorDictionary = loadDictionary("conductors");
    pieceDictionary = loadDictionary("pieces");
    soloistDictionary = loadDictionary("soloists");
    instrumentDictionary = loadDictionary("instruments");
    venueDictionary = loadDictionary("venues");
    
    if(performanceSearchButton) {
        performanceSearchButton.onclick = onSearchButtonClicked;
    }
    if(composerInputField) {
        composerInputField.addEventListener('input', function() { autocomplete("composer_input", "composers", composerInputField.value); });
    }

    if(conductorInputField) {
        conductorInputField.addEventListener('input', function() { autocomplete("conductor_input", "conductors", conductorInputField.value); });
    }
    if(pieceInputField) {
        pieceInputField.addEventListener('input', function() { autocomplete("piece_input", "pieces", pieceInputField.value); });
    }
    if(soloistInputField) {
        soloistInputField.addEventListener('input', function() { autocomplete("soloist_input", "soloists", soloistInputField.value); });
    } 
    if(instrumentInputField) {
        instrumentInputField.addEventListener('input', function() { autocomplete("instrument_input", "instruments", instrumentInputField.value); });
    }
    if(venueInputField) {
        venueInputField.addEventListener('input', function() { autocomplete("venue_input", "venues", venueInputField.value); });
    }
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
        return baseURL;
}

function onSearchButtonClicked() {
    var performanceSearchList = document.getElementsByClassName('search_input');
    var startDateInputField = document.getElementById('start_date_input_field');
    var endDateInputField = document.getElementById('end_date_input_field');
    var getParams = "";
    if(startDateInputField.value != ''){
        getParams += 'start_date=';
        getParams += startDateInputField.value;
        getParams += '&';
    }
    if(endDateInputField.value != ''){
        getParams += 'end_date=';
        getParams += endDateInputField.value;
        getParams += '&';
    }

    for (var i = 0; i < performanceSearchList.length; i++) {
        if(performanceSearchList[i].value != "") {
            getParams = getParams + performanceSearchList[i].name + '=';
            var matchingId = '';
            var optionSearchList = document.getElementsByClassName("option_" + performanceSearchList[i].name + "s");
            for (var j = 0; j < optionSearchList.length; j++) {
                if(optionSearchList[j].value == performanceSearchList[i].value){
                    matchingId = optionSearchList[j].id;
                }
            }
            if(matchingId == '') {
                console.log("best_match_" + performanceSearchList[i].name + "s");
                var bestMatch = document.getElementsByName("best_match_" + performanceSearchList[i].name + "s");
                if(bestMatch.length != 0) {
                    matchingId = bestMatch[0].id;
                } else {
                    matchingId = -1;
                }          
            }
            getParams = getParams + matchingId + '&';
        }
    }   
    getParams = getParams.substring(0, getParams.length-1);
    var url = getBaseURL() + '/performances?' + getParams;
    console.log(url);
//    location.href = 'results.html'
    fetch(url, {method: 'get'})
        .then((response) => response.json())
        
        .then(function(performanceList) {
            var tableBody = '';
            for (var k = 0; k < performanceList.length; k++) {
                tableBody += '<tr>';
                tableBody += '<td>';
                tableBody += performanceList[k]['date'] + '</td><td>'
                + performanceList[k]['venue'] + '</td><td>'
                + performanceList[k]['composer'] + '</td><td>'
                + performanceList[k]['conductor'] + '</td><td>'
                + performanceList[k]['piece'] +'</td><td>'
                + performanceList[k]['soloists'] + '</td>';
                tableBody += '</tr>';
                }

                var resultsTableElement = document.getElementById('results_table');
                if (resultsTableElement) {
                    resultsTableElement.innerHTML = tableBody;
                }
            })


    .catch(function(error) {
        console.log(error);
        });


}



    
   
function getDictionary(element_type) {
    switch(element_type) {
        case "composers":
            return composerDictionary;
        
        case "conductors":
            return conductorDictionary;
        
        case "pieces":
            return pieceDictionary;

        case "soloists":
            return soloistDictionary;

        case "instruments":
            return instrumentDictionary;

        case "venues":
            return venueDictionary;
    } 
    return false;
}
        
function autocomplete(id, element_type, input) {
    if(input == '')
        return;
    input = input.toLowerCase();
    var firstMatches = new Array();
    var secondMatches = new Array();
    var thirdMatches = new Array();
    var options = ''
    var dictionaryPromise = getDictionary(element_type);
    dictionaryPromise.then(function(dictionary) {
        var name = element_type.substring(0, element_type.length - 1) + '_name';
        var idString = element_type.substring(0, element_type.length - 1) + '_id';
        for(var k = 0; k < dictionary.length; k++) {
            var element = dictionary[k][name];
            var elementId = dictionary[k][idString];
            var tier;
            if(!(element.toLowerCase().includes(input))) {
                tier = -1;
            } else if (element.toLowerCase().indexOf(input) == 0) {
                tier = 1;
            } else if (element.toLowerCase().indexOf(input) == element.indexOf(', ') + 1) {
                tier = 2;
            } else {
                tier = 3;
            }

            if(tier == -1)
                continue;
            else if(tier == 1) {
                firstMatches.push(element);
                firstMatches.push(elementId);
            }
            else if(tier == 2){
                secondMatches.push(element);
                secondMatches.push(elementId);
            }
            else if(tier == 3){
                thirdMatches.push(element);
                thirdMatches.push(elementId);
            }
        }

        var numberOfMatches = 0;

        for (var i = 0; i < firstMatches.length; i+=2) {
            if(numberOfMatches == 0){
                options += '<option value =\"' + firstMatches[i] + '\" id=\"' + firstMatches[i+1] + '\" name=\"best_match_' + element_type + '\" class=\"option_' + element_type + '\">';
            } else {
                options += '<option value=\"' + firstMatches[i] + '\" id=\"' + firstMatches[i+1] + '\" class=\"option_' + element_type + '\">';
            }
            numberOfMatches++;
            if(numberOfMatches >= 5) {
                console.log(options);
                document.getElementById(id).innerHTML = options;
                return;
            }
        }

        for (var i = 0; i < secondMatches.length; i+=2) {
            options += '<option value=\"' + secondMatches[i] + '\" id=\"' + secondMatches[i+1] + '\" class=\"option_' + element_type + '\">';
            numberOfMatches++;
            if(numberOfMatches >= 5){
                document.getElementById(id).innerHTML = options;
                return;
            }
        }

        for (var i = 0; i < thirdMatches.length; i+=2) {
            options += '<option value=\"' + thirdMatches[i] + '\" id=\"' + thirdMatches[i+1] + '\" class=\"option_' + element_type + '\">';
            numberOfMatches++;
            if(numberOfMatches >= 5) {
                document.getElementById(id).innerHTML = options;
                return;
            }
        }
        document.getElementById(id).innerHTML = options;
    });
}

