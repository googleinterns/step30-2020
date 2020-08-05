
// Loads in the iFrame Player API code asynchronously
var tag = document.createElement('script');

tag.src = "https://www.youtube.com/iframe_api";

var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

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
//Submits video id to AddVideoServlet
function queueVideo(){
    $.ajax({
        url:'/add-video',
        type:'post',
        data:{data:$('#urlInput').serialize()},
        success:function(){
            //console.log("queued")
        }
    });
}

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
var count = 0;//position in queue
function onPlayerStateChange(event) {
    console.log(player.getPlayerState());
    if(player.getPlayerState() == YT.PlayerState.ENDED){
        const responsePromise = 
        fetch('video-data')
        .then(response => response.json())
        .then((videos) => {
            player.cueVideoById(videos[count], 0);
            player.playVideo();
            authenticate(loadClient)
            console.log(execute(videos[count]))
            });
        count+=1;
    }
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
    getRoomData();
    var titleElement = document.getElementById('video-title');
    titleElement.innerText = "Title";
}
