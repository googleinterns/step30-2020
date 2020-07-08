//Timer under construction
document.getElementById("chatlog").onload = function() {setInterval(window.alert("hi"), 5000)}; 
document.getElementById("sendButton").onclick = function() {addNewComment()}; 
var formattedComment = "";

function addNewComment() {
    var comment = document.getElementById("submitted-comment").value;
    if (comment != "") {
        formattedComment += "<br /><li>" + comment + "</li><br />";
        localStorage.setItem("commentStorageKey", formattedComment); 
    }
}

// Just a servlet connector for now.
function lmao() {
    fetch('/chatmessages').then(response => response.json()).then((justSettingUp) => {
        console.log(justSettingUp);
    });
} 

function loadComments() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      console.log("is this running?");
      document.getElementById("chatlog").innerHTML = localStorage.getItem("commentStorageKey");
    }
  };
  xhttp.open("GET", "ajax_test.txt", true);
  xhttp.send();
}

function addTestComment() {
    window.alert("hi");
    document.getElementById("chatlog").innerHTML = "<p>lol</p>";
}

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
    titleElement.innerText = "Title";
}
