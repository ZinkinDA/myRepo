$(document).ready(function () {
    showProduct();
});

function showProduct() {
    $.get("/api/fsm/product", function (data) {
        console.log(data);
        let table;
        for(let i=0;i<data.length;i++) {
            table += '<tr><td>' + data[i].id + '</td><td>' +data[i].name + '</td><td>' + data[i].price + '</td><td>' +
                data[i].description +'</td></tr>';
        }
        $('#productPanelBody').html(table);
    });
}

function addProduct(){
    let name = document.getElementById('productAddInputName').value;
    let price = document.getElementById('productAddInputPrice').value
    let desc = document.getElementById('productAddInputDesc').value;
    setTimeout(showProduct,500);
    $.ajax({
        url : '/api/fsm/product/add',
        dataType: 'json',
        contentType : 'application/json',
        type : 'POST',
        cache : false,
        data : JSON.stringify({'name':name,'price':price,'description':desc}),
    });
}