function addToCart(click) {
    var name =$(this).parent().children(".productLink").children(".productName").text();
    $.ajax({
        url: "/cart",
        data: "name="+name,
        dataType: "json",
    }).done(function (response) {
        $("#cart").text("Корзина ("+response.ammount+")");
    });
}