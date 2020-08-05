document.getElementById("sendButton").addEventListener("click", addNewComment);
var timer = window.setInterval(loadComments, 1000); //check comment updates every second

// retrieves comments from the backend, loads using AJAX
function loadComments() {
  var xhttp;
  xhttp = new XMLHttpRequest();
  xhttp.addEventListener('load', function() {
    if (this.status == 200) {
      document.getElementById("actual-comments").innerHTML = this.responseText;
    }
  });
  xhttp.open("GET", "/chatstorage", true);
  xhttp.send();
} 

//stores comments into the backend using AJAX
function addNewComment() {
    var comment = document.getElementById("submitted-comment").value;

    if (comment != "") {
        $.ajax({
            url: '/chatstorer',
            method: 'POST',
            data: {
                "submitted-comment" : comment
            },
            success : function(resultText){
                console.log("it worked");
            },
            error : function(jqXHR, exception){
                console.log('Errror occured');
            }
        });
    }
    document.getElementById("submitted-comment").value = "";
}
