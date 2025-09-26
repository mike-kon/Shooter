function showModal(jspName, success_func) {
    $('#modalBody').prop("jspname", jspName);
    $('#modalBody').prop("success", () => success_func);
    $('#modalBack').show()
    $('#modalWin').show()
    $('#modalBlock').show()
}

function hideModal() {
    $('#modalBody').empty();
    $('#modalBody').prop("jspname", null);
    $('#modalBody').prop("success", null);
    $('#modalBack').hide();
    $('#modalWin').hide();
    $('#modalBlock').hide();
}

function runModal(elem) {
    const jsp = '/api/v1/' + $('#modalBody').prop("jspname");
    const pars = $('#modalBody [name]').serialize();
    const success_func =  $('#modalBody').prop("success");
    $.post(jsp, pars)
        .done(function(data) {
            if (success_func == undefined || success_func == null ) {
                alert(data);
            } else {
                success_func(data);
            }
            hideModal();
        })
        .fail(function(xhr) {
            const message = 'Ошибка при вызове ' +
                xhr.responseJSON.path + ":" +
                xhr.responseJSON.status + ' ' + xhr.responseJSON.error;
            logging(message);
            console.log(xhr.responseJSON.status, xhr.responseJSON.error);
        })
}