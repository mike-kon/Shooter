function logging(mesages) {
    $('#logging').append(mesages);
    setTimeout(() => $('#logging').empty(), 5000);
}
