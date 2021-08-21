$(document).ready(function () {
    $("#btn-add-modal").on('click', function () {
        $("#modal-add-product").modal("show");
    });

    $("imageAdd").change(function () {
        showImageThumbnail(this);
    });

    $("#btn-back-add").on('click', function () {
        $("#modal-add-product").modal("hide");
    });

    $("#btn-add-product").on('click', function () {
        //event.preventDefault()

        var form = $("#form-add")[0];
        var data = new FormData(form);

        $("#btn-add-product").prop("disabled", true);

        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: "/product/createproduct",
            data: data,
            //http://api.jquery.com/jQuery.ajax/
            //http://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
            contentType: false,
            cache: false,
            processData: false,
            success: function () {
                form.reset();
                $("#btn-add-product").prop("disabled", false);
                location.reload();
            },
            error: function (e) {
                console.log(e.responseText);
                console.log("ERROR : ", e);
                $("#btnSubmit").prop("disabled", false);
                location.reload();
            }
        });

    });
});

function showImageThumbnail(fileInput) {
    file = fileInput.files[0];
    reader = new FileReader();

    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result);
    };

    reader.readAsDataURL(file);
}

