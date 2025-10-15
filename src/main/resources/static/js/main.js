const version = "v1"
const baseRequestUrl = "/api/";
const baseMvcUrl = "/mvc/";

function getRequestUrl(url) {
    return baseRequestUrl + version + "/" + url;
}

function getMvcUrl(url) {
    return baseMvcUrl + version + "/" + url;
}

function setButtonEnabled(id) {
    $('button').each(function () {
        if (this.id === id) {
            $(this).prop('disabled', true);
        } else {
            $(this).prop('disabled', false);
        }
    });
}

function ShowShooter() {
    setButtonEnabled('btnShooter');
    $.get("shooter", function (data) {
        $('#canvas').html(data);
    });
}

function ShowSavers() {

}

function ShowSetupServers() {
    setButtonEnabled('btnServers');
    $.post(getMvcUrl("setupservers"), function (data) {
        $('#canvas').html(data);
    });
}

function ShowAbout() {
    $('#canvas').load('about.html');
    setButtonEnabled('btnAbout');
}

function getJsp(url, params, elem) {
    const actualUrl = getRequestUrl(url);
    $.post(actualUrl, params, function (data) {
        $(elem).html(data);
    });
}

function showTest() {
    $.post(getRequestUrl("sandbox"), {}, function (data) {
        $('#canvas').html(data);
    });
}