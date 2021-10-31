var ws = new WebSocket("ws://" + location.host + "/ws/app");


ws.onopen = function() {
    console.log("open connect socket client")
    var newDiv = document.createElement("div");
    newDiv.innerHTML = "WebSocket Opened";

    var holder = document.getElementById("holder");
    holder.appendChild(newDiv);
}

ws.onmessage = function(evt) {
    console.log('recieved Message  socket server!',evt.data);
    var newDiv = document.createElement("div");
    newDiv.innerHTML = "> " + evt.data;

    var holder = document.getElementById("holder");
    holder.appendChild(newDiv);
}

ws.onclose = function() {
    console.log('closed socket')
    var newDiv = document.createElement("div");
    newDiv.innerHTML = "WebSocked Closed. Refresh page to open new WebSocket.";

    var holder = document.getElementById("holder");
    holder.appendChild(newDiv);
}

ws.onerror = function() {
    console.log('xảy ra lỗi socket');
    var newDiv = document.createElement("div");
    newDiv.innerHTML = "WebSocked Error. Try refreshing the page.";

    var holder = document.getElementById("holder");
    holder.appendChild(newDiv);
}
