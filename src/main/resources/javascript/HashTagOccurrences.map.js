function map() {

    for (var i = 0; i < this.entities.hashtags.length; i++) {
        emit(this.entities.hashtags[i].text, 1);
    }
}