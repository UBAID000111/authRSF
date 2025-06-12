const mongoose = require("mongoose");

const applicationSchema = new mongoose.Schema({
  name: String,
  phone: String,
  skills: String,
  internshipTitle: String,
  areaOfInterest: String,
  resumeUrl: String
});

module.exports = mongoose.model("Application", applicationSchema);