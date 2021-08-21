$(document).ready(function () {
    $(".btn-delete-modal").on('click', function(e) {
        e.preventDefault();

        var href = $(this).attr('href');

        $("#modal-confirm-delete").modal("show");

        $("#btn-back-delete").on('click', function () {
            $("#modal-edit-product").modal("hide");
        });

        $("#btn-delete-product").on('click', function() {
            $.ajax({
                type: 'DELETE',
                url: href,
                success: function () {
                    $("#modal-confirm-delete").modal("hide");
                    alert("Delete product successful");
                    location.reload();
                },
                error: function (e) {
                    console.log("ERROR : ", e.toString());
                }
            });
        })
    });


});