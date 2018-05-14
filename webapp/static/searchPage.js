
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
    var instrumentInputField = document.getElementById("insturment_input_field");
    var venueInputField = document.getElementById("venue_input_field");

    loadDictionary("composers", composerDictionary);
    console.log(composerDictionary);
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
    getParams = getParams + startDateInputField.value + '&' + endDateInputField.value;
    for (var i = 0; i < performanceSearchList.length; i++) {
        if(performanceSearchList[i].value != "") {
            getParams += '&';
            getParams += performanceSearchList[i].value;
        }
    }
    console.log(getParams);
    var url = getBaseURL() + '/performances/' + getParams;
    location.href = 'results.html'
    fetch(url, {method: 'get'})
        .then((response) => response.json())
        
        .then(function(performancesList) {
            var tableBody = '';
            for (var k = 0; k < performancesList.length; k++) {
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

function loadDictionary(element_type) {
    var url = getBaseURL() + '/' + element_type;
    fetch(url,{method: 'get'})
        .then((response) => response.json())

        .then(async function(elementList) {
            return await elementList;
            }
        console.log(elementList);
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

    var firstMatches = new Array();
    var secondMatches = new Array();
    var thirdMatches = new Array();
    var options = ''
    var dictionary = getDictionary(element_type);
    var name = element_type.substring(0, element_type.length) + '_name';
            
    for(var k = 0; k < dictionary.length; k++) {
        var element = dictionary[k][name];
        var tier = setHierarchy(element, input);
        if(tier == -1)
            continue;
        else if(tier == 1) {
            firstMatches.push(element);
        }
        else if(tier == 2)
            secondMatches.push(element);
        else if(tier == 3)
            thirdMatches.push(element);
    }

    var numberOfMatches = 0;

    for (var i = 0; i < firstMatches.length; i++) {
        console.log(options);
        options += '<option value=\"' + firstMatches[i] + '\">';
        numberOfMatches++;
        if(numberOfMatches >= 5) {
            document.getElementById(id).innerHTML = options;
    return;
        }
    }

    for (var i = 0; i < secondMatches.length; i++) {
        options += '<option value=\"' + secondMatches[i] + '\">';
        numberOfMatches++;
        if(numberOfMatches >= 5){
            document.getElementById(id).innerHTML = options;
            return;
        }
    }

    for (var i = 0; i < thirdMatches.length; i++) {
        options += '<option value=\"' + thirdMatches[i] + '\">';
        numberOfMatches++;
        if(numberOfMatches >= 5) {
            document.getElementById(id).innerHTML = options;
            return;
        }
    }
    document.getElementById(id).innerHTML = options;
}

function setHierarchy(element, searchString) {
    if(!(element.includes(searchString))) {
        return -1;
    } else if (element.indexOf(searchString) == 0) {
        return 1;
    } else if (element.indexOf(searchString) == element.indexOf(',') + 1) {
        return 2;
    } else {
        return 3;
    }
}

