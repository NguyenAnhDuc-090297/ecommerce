$(document).ready(function () {
    $(".btn-edit-modal").on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            type: 'GET',
            url: href,
            success: function (product) {
                $("#idEdit").val(product.id);
                $("#nameEdit").val(product.name);
                $("#descriptionEdit").val(product.description);
                $("#priceEdit").val(product.price);
                $("#modal-edit-product").modal("show");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        // $.get(href, function (product, status) {
        //     $("idEdit").val(product.id);
        //     $("nameEdit").val(product.name);
        //     $("descriptionEdit").val(product.description);
        //     $("priceEdit").val(product.price);
        // });
    });

    $("#btn-back-edit").on('click', function () {
        $("#modal-edit-product").modal("hide");
    });

    $("#btn-edit-product").on('click', function(event2) {
        event2.preventDefault();
        var form = $("#form-edit")[0];
        var data = new FormData(form);

        $.ajax({
            type: 'PUT',
            enctype: 'multipart/form-data',
            url: "/product/updateproduct",
            data: data,
            //http://api.jquery.com/jQuery.ajax/
            //http://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
            contentType: false,
            cache: false,
            processData: false,
            success: function () {
                alert("Update successful");
                location.reload();
            },
            error: function (e) {
                console.log("ERROR : ", e.toString());
            }
        });
    });
});