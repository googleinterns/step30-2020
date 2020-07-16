// Loads in the iFrame Player API code asynchronously
var tag = document.createElement('script');

tag.src = "https://www.youtube.com/iframe_api";

var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

// Create the player object
var player;

// Create variable to keep track of host
var host = false;

// Create constant variables for timeout functions
const longPollingTimeOutMS = 60000; 
const timeOutMS = 1000;

function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        height: '390',
        width: '640',
        videoId: 'QSQwZlRMVAM',
        events: {
        // Adds event listeners for the player and maps the function that will triggered
        // See Events docs for player event listeners:
        // https://developers.google.com/youtube/iframe_api_reference#Events

        'onReady': onPlayerReady,
        'onStateChange': onPlayerStateChange
        }
        });
}

// Function to set the host 
function setHost(){
    host = true;
}

// When the player state is changed, the API calls this function
function onPlayerStateChange(event) {
    console.log(host);
    var status = player.getPlayerState();

    if(host == true){
        $.ajax({
            url: 'sync',
            method: 'POST',
            data: {status : status},
            success    : function(resultText){
                $('#result').html(resultText);
                setTimeout(() => { console.log("Changed playback state"); }, timeOutMS);
            },
            error : function(jqXHR, exception){
                console.log('Error occured');
            }
        });
    }
}

// Functions that change the playback state of the player
function stopVideo() {
    player.stopVideo();
}

function pauseVideo() {
    player.pauseVideo();
}

function playVideo() {
    player.playVideo();
}

// Function that contains the logic for changing the playback state
function updatePlayer(status){
    if(status != player.getPlayerState() ){
        setTimeout(() => { console.log("Received change in playback state"); }, timeOutMS);
        if(status == 1){
            playVideo();
        } else if (status == 2){
            pauseVideo();
        } else if (status == -1){
            playVideo();
        }
    }
}

// Function to load the title of the current video
function loadVideoInfo() {
    var titleElement = document.getElementById('video-title');
    titleElement.innerText = "Title";
}

function longPolling() {
    // Long Polling
     $.ajax({ 
        url: "sync",
        success: function(updater){
            if(host == false){
                updatePlayer(updater.status);
            }
        },
        error: function(err) {
            console.log("Error");
        },
        type: "GET", 
        dataType: "json", 
        complete: longPolling,
        timeout: longPollingTimeOutMS // timeout after one minute
    });
}

// When the video player is ready, the API will call this function
function onPlayerReady(event) {
    longPolling();
}