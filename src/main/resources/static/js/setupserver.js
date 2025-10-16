function askServerType() {
    $.post("api/v1/readTypeServers", function (data) {
        const modalBody = $('#modalBody');
        modalBody.append("Выберите тип сервера: ");
        const $serverTypeSelect = $("<select></select>");
        $serverTypeSelect.prop("id", "serverTyperSelect");
        $serverTypeSelect.prop("name", "serverType");
        $.each(data, function (value, key) {
            $serverTypeSelect.append(
                new Option(key, value)
            );
        })
        modalBody.append($serverTypeSelect);
        showModal('newServer', function (data) {
            $("#setupServer").html(data);
        });
    })
}

function deleteServer() {
    const deletedId = $('select[name="savedServer"]').val();
    $.post("api/v1/deleteServer", {"serverId":deletedId}, function () {
        // todo обработать ошибку
        showSetupServers();
    });
}
