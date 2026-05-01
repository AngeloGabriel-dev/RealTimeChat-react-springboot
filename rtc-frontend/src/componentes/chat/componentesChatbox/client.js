import SockJS from "sockjs-client"
import Stomp from "stompjs"


var stompClient = null
export function connect() {
    var socket = new SockJS('http://localhost:8080/ws')
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        console.log('to aqui')
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

export function sendMessage() {
    var messageContent = document.getElementById("message").value
    var message = {
        content: messageContent,
        sender: "User1",
        receiver: "User2"
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
}

function showMessage(message) {
    var chat = document.getElementById("chat");
    var newMessage = document.createElement("div");
    newMessage.appendChild(document.createTextNode(message.sender + ": " + message.content));
    chat.appendChild(newMessage);
}

window.onload = function() {
    connect();
};

