/**
* @author Ari Conati, Grant Lee
*
* Implements functionality for the elements of searchPage.html. This includes 
* updating the list of options given by the datalist elements as text is 
* entered (predictive autocompleter) and redirecting the user to a new page 
* (results.html) which displays the performances matching the user input 
* in the datalist elements.
* 
*/



var composerDictionary;
var conductorDictionary;
var pieceDictionary;
var soloistDictionary;
var instrumentDictionary;
var venueDictionary;

initialize();

/**
* Initializes the elements in searchPage.html. Preloads the dicionaries
* so that the autocomplete function can list options for the datalist
* elements.
*/ 
function initialize() {
    localStorage.setItem("banana", "apple");
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
    // Query api and load dictionaries stored as Promise objects. This allows matching strings
    // to quickly be found by the autocomplete function without sending a new query each time
    // input changes.

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

/**
* @Return the base url for api queries
*/

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
        return baseURL;
}

/**
* Sends a query to the api to find matching performances based on the search criteria entered in the input
* fields. If search input does not match anything in the database the closest match is automatically selected.
* Redirects the user to a results.html document which displays the matching performances.
*/

function onSearchButtonClicked() {


    var performanceSearchList = document.getElementsByClassName('search_input');
    var startDateInputField = document.getElementById('start_date_input_field');
    var endDateInputField = document.getElementById('end_date_input_field');
    var getParams = "";
    var criteria = '<h3>Performances matching search criteria:</h>';

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

            //Look through datalist options to find the option with the matching id
            for (var j = 0; j < optionSearchList.length; j++) { 
            if(optionSearchList[j].value == performanceSearchList[i].value){
                    matchingId = optionSearchList[j].id;
                    var criterion = optionSearchList[j].className.substring(7, optionSearchList[j].className.length - 1);
                    criterion = criterion.substring(0,1).toUpperCase() + criterion.substring(1,criterion.length);
                    criteria += "<p>" + criterion + ": " + optionSearchList[j].value + "</p>";
                }
            }
            //If the search input does not match a field in the dictionary, automatically select the best match
            if(matchingId == '') {
                var bestMatch = document.getElementsByName("best_match_" + performanceSearchList[i].name + "s");
                if(bestMatch.length != 0) {
                    matchingId = bestMatch[0].id;
                    var criterion = bestMatch[0].className.substring(7, bestMatch[0].className.length - 1);
                    criterion = criterion.substring(0,1).toUpperCase() + criterion.substring(1,criterion.length);
                    criteria += "<p>" + criterion + ": " + bestMatch[0].value + "</p>";
                } else {
                    matchingId = -1;
                }          
            }
            getParams = getParams + matchingId + '&'; 
        }
    } 
    getParams = getParams.substring(0, getParams.length-1); //remove extraneous ampersand from query
    var url = getBaseURL() + '/performances?' + getParams;
    //Save information in local storage so it can be presented on another page
    localStorage.setItem("results_search_criteria", criteria);
    localStorage.setItem("url", url);

    location.href = "/results?";
}


/**
* Helper function for autocompleter which returns the dictionary
* containing the correct data based on the search field element.
* @Return the matching dictionary - stored as a Promise object returned from the api query
*/
    
  
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

/**
* Finds the best matching strings in the data based on the search input. Matches are considered
* to be strongest when the match occurs at the beginning of the string, second strongest if they
* occur just after the comma separator (eg. first name), and least strong if they match anywhere else
* in the word. Matches that are equally strong are presented alphabetically. The best 5 matches are
* added to the corresponding datalist element in the html.
*
* @Param id The id of the corresponding datalist in searchPage.html
* @Param element_type The type of element which is being searched for (eg. composers)
* @Param input The searchString which is compared to the data in the database.
*/
        
function autocomplete(id, element_type, input) {
    if(input == '')
        return;
    input = input.toLowerCase();

    // 3 separate arrays store matches based on how closely they match the search string
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

            // Set the variable tier based on how closely the dictionary element matches the search
            // string and assign it to the corresponding array

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
                firstMatches.push(element);  // Store the matching elements in pairs with the corresponding id
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
            if(numberOfMatches >= 5) { // Stop adding additional options if the number of matches exceeds 5
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

