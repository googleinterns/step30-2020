// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
const ajaxPostRequestIntervalMS = 500;

function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        height: '390',
        width: '640',
        videoId: 'QSQwZlRMVAM',
        events: {
        // Adds event listeners for the player and maps the function that will triggered
        // See Events docs for player event listeners:
        // https://developers.google.com/youtube/iframe_api_reference#Events

        'onReady': onPlayerReady
        }
        });
}

// Function to set the host and start ajax post requests
// TODO: Remove once room creation is complete 
function setHost(){
    host = true;
    // Sends host player status information
    setInterval(hostPlayerStatus, ajaxPostRequestIntervalMS);
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
    // Compares host status to local player status. 
    // See Playback Status docs for values: 
    // https://developers.google.com/youtube/iframe_api_reference#Playback_status
    
    if(status != player.getPlayerState() ){
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
// TODO: Link to Data API to import information from current video
function loadVideoInfo() {
    var titleElement = document.getElementById('video-title');
    titleElement.innerText = "Title";
}

function longPolling() {
    // Long Polling
     $.ajax({ 
        url: "sync",
        success: function(updater){
            if(!host){
                updatePlayer(updater.status);
                checkDifference(updater.timeStamp);
            }
        },
        error: function(err) {
            console.log("Error in longPolling() occured!");
        },
        type: "GET", 
        dataType: "json", 
        complete: longPolling
    });
}

// When the video player is ready, the API will call this function
function onPlayerReady(event) {
    playVideo();    
    longPolling();
}

// Make sure the local player time  is within a second of the host's current time
function checkDifference(hostTimeStamp){
    if(Math.abs(player.getCurrentTime() - hostTimeStamp) > 1){
        player.seekTo(hostTimeStamp);
    }
}

// Function that sends the host's player status
function hostPlayerStatus() { 
    var status = player.getPlayerState();
    var timeStamp = player.getCurrentTime();
    
    $.ajax({
            url: 'sync',
            method: 'POST',
            data: {status : status, host: host, time: timeStamp},
            success: function(resultText){
                $('#result').html(resultText);
            },
            error : function(jqXHR, exception){
                console.log('Error in hostPlayerStatus() occured!');
            }
        });
}
