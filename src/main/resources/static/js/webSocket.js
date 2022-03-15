let socket = new WebSocket("ws://localhost:8099/ws/chat");
socket.onopen = function (e) {
    console.log('open server!')
};

socket.onmessage = function (msg) {
    var data = msg.data;
    let toast = "<div class=\"position-fixed bottom-0 end-0 p-3\" style=\"z-index: 11\">" +
        "<div class=\"toast\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">";
    toast += "<div class='toast-header'><img src=\"...\" class=\"rounded me-2\" alt=\"...\"><strong class=\"me-auto\">🍙뭐먹을까요</strong><small><span th:text=\"${#dates.createNowForTimeZone('MM-dd HH:mm')}\"></span></small>";
    // toast += "<button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
    toast += "<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>";
    toast += "</div> <div class='toast-body'>" + data + "<audio autoplay controls style='display: none'> <source src=\"/sound/실로폰_효과음.wav\" type=\"audio/wav\"> </audio></div></div></div>";
    // toast += "</div> <div class='toast-body'>" + data + "</div></div></div>";
    // $("#msgStack").append(toast);   // msgStack div에 생성한 toast 추가
    document.getElementById('msgStack').innerHTML = toast;
    $(".toast").toast({"animation": true, "autohide": false});
    $('.toast').toast('show');
};

const send_btn = () =>{
    var target = "qwe"
    var type = "1";
    socket.send(target+","+type);
}

const customer_btn = (storeEmail) => { // 회원이 업체에게 주문을 할 때
    var target = storeEmail;
    var type = "1"; // 주문 접수
    socket.send(target+","+type);
}

const store_btn = (customerEmail) => {
    var target = customerEmail;
    var type = "2";
    socket.send(target+","+type);
}