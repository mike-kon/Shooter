const baseUrl = "/api/v1";

function setButtonEnabled(id) {
    $('button').each(function() {
        if (this.id === id) {
            $(this).prop('disabled', true);
        } else {
            $(this).prop('disabled', false);
        }
    });
}

function ShowShooter() {
    setButtonEnabled('btnShooter');
    $.get("shooter", function(data) {
        $('#canvas').html(data);
    });
}

function ShowSavers() {

}

function ShowSetupServers() {
    setButtonEnabled('btnServers');
    $.get("setupservers", function(data) {
        $('#canvas').html(data);
    });
}

function ShowAbout() {
    $('#canvas').load('about.html');
    setButtonEnabled('btnAbout');
}

function getJsp(url, name, elem) {
    const actualUrl = baseUrl + url;
    $.post(actualUrl, name, function(data) {
        $(elem).html(data);
    });

}