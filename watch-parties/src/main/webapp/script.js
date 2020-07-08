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
