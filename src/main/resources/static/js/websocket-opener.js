/*khi bao bien va get cac element HTML*/
var usernamePage = document.querySelector('#username-page'); // trả về element khớp vs tham số trong hàm đầu tiên (tham số có thể là class,id,tag)
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');


/*connect socket server from socket client*/
var ws = new WebSocket("ws://" + location.host + "/ws/app");


ws.onopen = function () {
    console.log("open connect socket client")
    var newDiv = document.createElement("div");
    newDiv.innerHTML = "WebSocket Opened";

    var holder = document.getElementById("holder");
    holder.appendChild(newDiv);
}

ws.onmessage = function (event) {
    console.debug("WebSocket message received:", event);
};

/*ws.onclose = function () {
    console.log('closed socket')
}*/

ws.onerror = function () {
    console.log('error socket');
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

/*funtion mới*/
function connect(event) {
    username = document.querySelector('#name').value.trim();
    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        console.log(username);
    }
    connectingElement.textContent = username;
    event.preventDefault();
}

function onConnected() {
    console.log('connected!');
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


// function onError(error) {
//     connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
//     connectingElement.style.color = 'red';
// }

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    console.log("input:", messageContent);
    if (messageContent) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        console.log("JSon Message:", JSON.stringify(chatMessage));
        ws.send(JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
    console.log("sssss:",chatMessage);
    onMessageReceived(chatMessage);
}

function onMessageReceived(payload) {
    var message = JSON.parse(JSON.stringify(payload));

    var messageElement = document.createElement('li');

    if (message.type === 'JOIN') {//JOIN
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else if (message.type === 'CHAT') {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        //avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

/*handle event*/
usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
