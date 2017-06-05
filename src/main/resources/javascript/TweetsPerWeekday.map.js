function map() {
    var date = new Date(this.created_at);
    emit(date.getDay(), 1);
}