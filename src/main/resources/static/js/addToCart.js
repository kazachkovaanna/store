function addToCart(click) {
    var name =$(this).parent().children(".productLink").children(".productName").text();
    $.ajax({
        url: "/cart",
        data: "name="+name,
        dataType: "json",
    }).done(function (response) {
        console.log("cart:"+response.cart);
        $("#cartValue").text(response.cart);
    });
}