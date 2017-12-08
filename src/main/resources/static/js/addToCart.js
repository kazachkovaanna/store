function addToCart() {
    var name =$(this).parent().children(".product-link").children(".card-title").text();
    console.log($(this).parent().children(".product-link"));
    console.log($(this).parent().children(".product-link").children(".cart-title"));
    $.ajax({
        url: "/cart",
        data: "name="+name,
        dataType: "json",
    }).done(function (response) {
        $("#cart").text("Корзина ("+response.ammount+")");
    });
}