//Timer under construction
//document.getElementById("chatlog").onload = function() {setInterval(window.alert("hi"), 5000)}; 
document.getElementById("sendButton").onclick = function() {addNewComment()}; 
var formattedComment = "";

function addNewComment() {
    var comment = document.getElementById("submitted-comment").value;
    if (comment != "") {
        formattedComment += "<br /><li>" + comment + "</li><br />";
        localStorage.setItem("commentStorageKey", formattedComment); 
        document.getElementById("chatlog").innerHTML = localStorage.getItem("commentStorageKey");
        document.getElementById("submitted-comment").value = "";
    }

    /*$.ajax({
        url: '/chatstorage',
        method: 'POST',
        data: {
            console . log(comments);
            console.log("it worked");
        },
        success    : function(resultText){
            //$('#result').html(resultText);
            //setTimeout(() => { console.log("it worked"); }, timeOutMS);
            console.log("it worked");
        },
        error : function(jqXHR, exception){
            console.log('Errror occured');
        }
    });*/

}



// Just a servlet connector for now.
function lmao() {
    fetch('/chatmessages').then(response => response.json()).then((justSettingUp) => {
        console.log(justSettingUp);
    });
} 