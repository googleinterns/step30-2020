//const fs = require('fs');
//import {fs} from 'fs';
///var comments = ["<br />"];
document.getElementById("plzwork").onclick = function() {addComment()};
var formattedComment = "";
var allComments = []; //"<br /><li>didnt work</li><br />";

function formatNewComment() {

    var comment = document.getElementById("submitted-comment").value;
    if (comment != "") {
        formattedComment += "<br /><li>" + comment + "</li><br />";
    }
}

function lmao() {
    fetch('/chatmessages').then(response => response.json()).then((justSettingUp) => {
        console.log(justSettingUp);
    });
} 

function addComment() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      //clearCommments("chatlog");

      formatNewComment(); 
      //allComments.push(formattedComment);
      localStorage.setItem("comment-section", formattedComment); 
      //clearCommments("chatlog");
      document.getElementById("chatlog").innerHTML = localStorage.getItem("comment-section");
      //clearCommments("chatlog");
      //document.getElementById("chatlog").innerHTML =
      //this.responseText;
    }
  };
  xhttp.open("GET", "ajax_test.txt", true);
  xhttp.send();
}

function clearCommments(elementID) {
    document.getElementById(elementID).innerHTML = "";
    localStorage.clear();
}