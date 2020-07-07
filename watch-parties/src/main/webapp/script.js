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

// When the video player is ready, the API will call this function to start playing the video
function onPlayerReady(event) {
    event.target.playVideo();
}

// When the player state is changed, the API calls this function

function onPlayerStateChange(event) {
    console.log(player.getPlayerState());
}

// Functions that change the playback state of the player
function stopVideo() {
    player.stopVideo();
}

function pauseVideo() {
    player.pauseVideo();
}

// Function to load the title of the current video
function loadVideoInfo() {

    var titleElement = document.getElementById('video-title');
    titleElement.innerText = "Title - eggdog quarantine";
}