//const fs = require('fs');
import {fs} from 'fs';
///var comments = ["<br />"];

function addComment() {

    var comment = document.getElementById("submitted-comment");
    if (comment != "") {
        var formattedComment = "<br /><li>" + comment + "</li><br />";

        
        fs.writeFile('ajax_test.txt', formattedComment, (error) => { 
      
        // In case of a error throw err exception. 
        if (error) throw err; 
        }); 
        
    }
}

function lmao() {
    fetch('/chatmessages').then(response => response.json()).then((justSettingUp) => {
        console.log(justSettingUp);
    });
}

function addSampleComment() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {

      addComment();  

      document.getElementById("chatlog").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "ajax_test.txt", true);
  xhttp.send();
}