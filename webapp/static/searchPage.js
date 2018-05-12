


function initialize() {
    var simpleSearch = document.getElementById('simple_search');
    var performanceSearchList = document.getElementsByClassName('search_input');
    var performanceSearchButton = document.getElementsById(performance_search_button);
    var getParams = "";
    if(performanceSearchButton){ 
        for (var i = 0; i < performanceSearchList.length; i++) {
            if(performanceSearchList[i].value != "") {
                getParams += performanceSearchList[i].value;
            }
        }
        onSearchButtonClicked(getParams);
    }
                
    
    //for (var i = 0; var < performanceSearchList.length; i++) {
    //    if(performanceSearchList[i]) {
    //       var completionsList = getCompletions(performanceSearchList[i].value, performanceSearchList[i].getAttribute("name"));
    //    }
    //}
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
        return baseURL;
}

function onSearchButtonClicked(getParams) {
   var url = getBaseURL() + '/performances/' + getParams;
   console.log("HELLO");
}

/*
function getCompletions(searchString, field) {
    var url = getBaseURL() + '/' + field + '/';
    fetch(url, {method: 'get'});
    var dataList = []
    .then((response) => response.json())
    .then(function(dataMap) {
        dataList
    var autocompleter = new GenericAutocompleter(dataList, true);
    var completionsList = autocompleter.getCompletions(searchString);
    return completionsList;
}
*/
