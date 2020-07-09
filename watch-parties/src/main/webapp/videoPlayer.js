// Loads in the iFrame Player API code asynchronously
var tag = document.createElement('script');

tag.src = "https://www.youtube.com/iframe_api";

var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

// Create the player object
var player;

function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        height: '390',
        width: '640',
        videoId: 'QSQwZlRMVAM',
        events: {
        // Adds event listeners for the player and maps the function that will triggered
        'onReady': onPlayerReady,
        'onStateChange': onPlayerStateChange
        }
        });
}

// When the player state is changed, the API calls this function

function onPlayerStateChange(event) {
    var status = player.getPlayerState();

    $.ajax({
        url: 'sync',
        method: 'POST',
        data: {status : status},
        success    : function(resultText){
            $('#result').html(resultText);
            setTimeout(() => { console.log("Changed playback state"); }, 1000)
        },
        error : function(jqXHR, exception){
            console.log('Error occured!!');
        }
    });
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
        setTimeout(() => { console.log("Received change in playback state"); }, 1000)
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

// Functions for polling 
function shortPolling() {

    const textViewCount = document.getElementById('playback-status');

    // Short Polling
    setInterval(function() { 
        fetch('/sync').then(response => response.json()).then((updater) => {
            textViewCount.textContent = "Playback State: " + updater.status;
            updatePlayer(updater.status);
        });
    }, 500)
}

function longPolling() {

    const textViewCount = document.getElementById('playback-status');

    // Long Polling
     $.ajax({ 
        url: "sync",
        success: function(updater){
            textViewCount.textContent = "Playback State: " + updater.status;
            updatePlayer(updater.status);
        },
        error: function(err) {
            console.log("Error");
        },
        type: "GET", 
        dataType: "json", 
        complete: longPolling,
        timeout: 60000 // timeout every one minute
    });
}

// When the video player is ready, the API will call this function
function onPlayerReady(event) {
    longPolling();
}