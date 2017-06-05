function map() {
    var date = new Date(this.created_at);
    emit(date.getHours(), 1);
}