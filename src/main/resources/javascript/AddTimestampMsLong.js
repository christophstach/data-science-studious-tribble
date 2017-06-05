db.tweetsBerlinGeo.find({
    timestamp_ms_long: { $exists: false }
}).forEach(function(doc) {
    doc.timestamp_ms_long = NumberLong(doc.timestamp_ms);
    db.tweetsBerlinGeo.save(doc);
});
