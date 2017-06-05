db.tweetsBerlinGeo.find({
    created_at_date: { $exists: false }
}).forEach(function(doc) {
    doc.created_at_date = new Date(doc.created_at);
    db.tweetsBerlinGeoAlt.save(doc);
});
