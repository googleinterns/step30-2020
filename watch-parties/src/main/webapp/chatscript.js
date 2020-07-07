
document.getElementById("sendButton").onclick = function() {addComment()}; // function triggered by the button
var formattedComment = "";

// Self explanitory. Reads a comment from the index.html file and 
// properly formats it.
function formatNewComment() {
    var comment = document.getElementById("submitted-comment").value;
    if (comment != "") {
        formattedComment += "<br /><li>" + comment + "</li><br />";
    }
}

// Just a servlet connector for now.
function lmao() {
    fetch('/chatmessages').then(response => response.json()).then((justSettingUp) => {
        console.log(justSettingUp);
    });
} 

// Uses AJAX and localStorage to add the comment to the page and
// store it, respectively.
function addComment() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      formatNewComment(); 
      localStorage.setItem("commentStorageKey", formattedComment); 
      document.getElementById("chatlog").innerHTML = localStorage.getItem("commentStorageKey");
    }
  };
  xhttp.open("GET", "ajax_test.txt", true);
  xhttp.send();
}
