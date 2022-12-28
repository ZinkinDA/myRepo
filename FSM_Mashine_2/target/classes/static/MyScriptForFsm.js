$(document).ready(function () {
    showFsm();
});

function showFsm() {
    $.get("/api/fsm", function (data) {
        console.log(data);
        let table = '<tr><td>' + data.value + '</td><td>' + data.statement + '</td></tr>';

        $('#fsm').html(table);
    });
}

function send_req_pay() {
    let id = document.getElementById('form_input_text').value;

    $.ajax({
        url : '/api/fsm/pay',
        dataType: 'json',
        contentType : 'application/json',
        type : 'POST',
        cache : false,
        data : JSON.stringify(id),
    });

    setTimeout(showFsm,500);
    setTimeout(showFsm,2100);
    setTimeout(showFsm,3600);
}

function send_req_collection() {
    let sum = document.getElementById('form_input_text_2').value;

    $.ajax({
        url : '/api/fsm/deduction',
        dataType: 'json',
        contentType : 'application/json',
        type : 'POST',
        cache : false,
        data : JSON.stringify(sum),
    });
    setTimeout(showFsm,500);
    setTimeout(showFsm,2100);
}