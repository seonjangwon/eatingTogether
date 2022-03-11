let socket = new WebSocket("ws://localhost:8099/ws/chat");
socket.onopen = function (e) {
    console.log('open server!')
};

socket.onmessage = function (msg) {
    var data = msg.data;
    let toast = "<div class=\"toast\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">";
    toast += "<div class='toast-header'><img src=\"...\" class=\"rounded me-2\" alt=\"...\"><strong class=\"me-auto\">Bootstrap</strong>";
    toast += "<button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
    toast += "<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>";
    toast += "</div> <div class='toast-body'>" + data + "</div></div>";
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