
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
    if(performanceSearchButton) {
        performanceSearchButton.onclick = onSearchButtonClicked;
    }
    if(composerInputField) {
        composerInputField.addEventListener('input', function() { autocomplete("composer_input", "composer", composerInputField.value); });
    }

    if(conductorInputField) {
        conductorInputField.addEventListener('input', function() { autocomplete("conductor_input", "conductor", conductorInputField.value); });
    }
    if(pieceInputField) {
        pieceInputField.addEventListener('input', function() { autocomplete("piece_input", "piece", pieceInputField.value); });
    }
    if(soloistInputField) {
        soloistInputField.addEventListener('input', function() { autocomplete("soloist_input", "soloist", soloistInputField.value); });
    } 
    if(instrumentInputField) {
        instrumentInputField.addEventListener('input', function() { autocomplete("instrument_input", "instrument", instrumentInputField.value); });
    }
    if(venueInputField) {
        venueInputField.addEventListener('input', function() { autocomplete("venue_input", "venue", venueInputField.value); });
    }
        
        

}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + 'api_port';
        return baseURL;
}

function onSearchButtonClicked() {
    var performanceSearchList = document.getElementsByClassName('search_input');
    var getParams = "";
    for (var i = 0; i < performanceSearchList.length; i++) {
        if(performanceSearchList[i].value != "") {
            getParams += performanceSearchList[i].value;
        }
    }
    console.log(getParams);
    var url = getBaseURL() + '/performances/' + getParams;
    location.href = 'x.html'
/*    fetch(url, {method: 'get'});
        .then((response) => response.json())
        
        .then(function(performancesList) {
            var tableBody = '<tr><th>ID</th><th>Name</th><th>Composer</th></tr>';
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
*/

}
        
function autocomplete(id, element_type, input) {
    var firstMatches = new Array();
    var secondMatches = new Array();
    var thirdMatches = new Array();
    var options = ''
    firstMatches[0] = 'Shostakovich, Dmitri';
    firstMatches[1] = 'Brahms, Johannes';
    if(input.indexOf('C') == 0) {
        thirdMatches[0] = 'Chopin, Frederic';
    }
    var url = getBaseURL() + '/' + element_type;
/*    fetch(url,{method: 'get'});
        .then((response) => response.json())

        .then(function(elementList) {
            
            for(var k = 0; k < elementList.length(); k++) {
                var element = elementList['name'];
                var tier = setHierarchy(element, input);
                if(tier == -1)
                    continue;
                else if(tier == 1)
                    firsttMatches.push(element, input);
                else if(tier == 2)
                    secondMatches.push(element, input);
                else if(tier == 3)
                    thirdMatches.push(element, input);
            }
        })

    .catch(function(error) {
        console.log(error);
        });
*/    
    var numberOfMatches = 0;
    firstMatches.sort();
    for (var i = 0; i < firstMatches.length; i++){
        options += '<option value="' + firstMatches[i] + '">';
        numberOfMatches++;
        if(numberOfMatches >= 5){
            document.getElementById(id).innerHTML = options;
            return;
        }
    }
    
    secondMatches.sort();
    for (var i = 0; i < secondMatches.length; i++) {
        options += '<option value="' + secondMatches[i] + '">';
        numberOfMatches++;
        if(numberOfMatches >= 5){
            document.getElementById(id).innerHTML = options;
            return;
        }
    }

    thirdMatches.sort();
    for (var i = 0; i < thirdMatches.length; i++) {
        options += '<option value="' + thirdMatches[i] + '">';
        numberOfMatches++;
        if(numberOfMatches >= 5){
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
    } else if (element.indexOf(searchString) == element.indexOf(searchString) + 1) {
        return 2;
    } else {
        return 3;
    }


