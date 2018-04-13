var url = "ws://localhost:9000/streamData";

var tweetSocket = new WebSocket(url);
tweetSocket.onmessage = function (event) {

console.log(event);
var data = event.data;
var tweet = document.createElement("p");
var text = document.createTextNode(data);
tweet.appendChild(text);
document.getElementById("streams" ).appendChild(tweet);
};
tweetSocket.onopen = function() {
tweetSocket.send("streaming");
};
