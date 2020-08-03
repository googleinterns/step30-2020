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
// TODO: Remove once room creation is complete 
function setHost(){
    host = true;
}
var yt_api_key = "[KEY GOES HERE]";
//Adds video id to YT Data API link
function getURL(yt_video_id){
    yt_snippet_endpoint = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + yt_video_id + "&key=" + yt_api_key;
    return yt_snippet_endpoint;
}
//Adds channel id to YT Data API link
function getAvatar(channel_id){
    return 'https://www.googleapis.com/youtube/v3/channels?part=snippet&id=' + channel_id + '&fields=items(id%2Csnippet%2Fthumbnails)&key=' + yt_api_key;
}

//Adds video thumbnail, title, and channel info to the queue.
function addVideo(n, video){
    var index;
    if(n === 1){
        index = "first";
    }
    else if(n === 2){
        index = "second";
    }
    else if(n === 3){
        index = "third";
    }
    document.getElementById(index + 'Thumbnail').src="https://img.youtube.com/vi/"+video+"/maxresdefault.jpg";
    var jqxhr = $.getJSON(getURL(video))
    .done(function(data) {
    document.getElementById(index + 'Title').innerHTML = data.items[0].snippet.title;
    document.getElementById(index + 'Channel').innerHTML = data.items[0].snippet.channelTitle;
    var channelId = data.items[0].snippet.channelId;
    var jqxhr2 = $.getJSON(getAvatar(channelId))
    .done(function(channelData) {
    document.getElementById(index + 'Avatar').src = channelData.items[0].snippet.thumbnails.high.url;
    });
    });
}
// When the player state is changed, the API calls this function
var count = 0;//position in queue
function onPlayerStateChange(event) {
    console.log(host);
    var status = player.getPlayerState();
    console.log(player.getPlayerState());
    if(player.getPlayerState() == YT.PlayerState.ENDED){
        const responsePromise = 
        fetch('video-data')
        .then(response => response.json())
        .then((videos) => {
            player.cueVideoById(videos[count], 0);
            player.playVideo();
            if(videos.length-count>1){
                addVideo(1, videos[count+1].videoId);
            }
            if(videos.length-count>2){
                addVideo(2, videos[count+2].videoId);
            }
            if(videos.length-count>3){
                addVideo(2, videos[count+3].videoId);
            }
            });
        count+=1;
    }
    if(host){
        $.ajax({
            url: 'sync',
            method: 'POST',
            data: {status : status, host: host, time: player.getCurrentTime()},
            success: function(resultText){
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

//Submits video id to AddVideoServlet and adds video data to the page
function queueVideo(){
    $.ajax({
        url:'/add-video',
        type:'post',
        data:{data:$('#urlInput').serialize()},
        success:function(){
            //console.log("queued")
        }
    });
    var url = document.getElementById('urlInput').value;
    var yt_video_id = url.substring(url.length-11);
    yt_snippet_endpoint = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + yt_video_id + "&key=" + yt_api_key;
    if($('#firstThumbnail').is(":hidden")){
        document.getElementById("firstThumbnail").style.display="block";
        document.getElementById("firstAvatar").style.display="block";
        addVideo(1, yt_video_id);
    }else if($('#secondThumbnail').is(":hidden")){
        document.getElementById("secondThumbnail").style.display="block";
        document.getElementById("secondAvatar").style.display="block";
        addVideo(2, yt_video_id);
    }else if($('#thirdThumbnail').is(":hidden")){
        document.getElementById("thirdThumbnail").style.display="block";
        document.getElementById("thirdAvatar").style.display="block";
        addVideo(3, yt_video_id);
    }
}
//Changes text of room title element
function getRoomData(){
    const responsePromise = 
    fetch('/room-data')   
    .then(response => response.json())
    .then((rooms) => {
    const commentsListElement = document.getElementById('room-title');
    commentsListElement.innerHTML = rooms[rooms.length-1].title;
    });
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
    getRoomData()
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