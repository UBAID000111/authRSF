const express = require("express");
const router = express.Router();
const Internship = require("../models/InternshipModel");
const Application = require("../models/Application");
const multer = require("multer");
const cloudinary = require("cloudinary").v2;
const fs = require("fs");

// Cloudinary config
cloudinary.config({
  cloud_name: process.env.CLOUDINARY_NAME,
  api_key: process.env.CLOUDINARY_KEY,
  api_secret: process.env.CLOUDINARY_SECRET
});

const upload = multer({ dest: "uploads/" });

// Get all internships
router.get("/internships", async (req, res) => {
  const internships = await Internship.find();
  res.json(internships);
});

// Submit application
router.post("/apply", upload.single("resume"), async (req, res) => {
  try {
    const result = await cloudinary.uploader.upload(req.file.path, {
      resource_type: "raw"
    });
    fs.unlinkSync(req.file.path);

    const application = new Application({
      ...req.body,
      resumeUrl: result.secure_url
    });
    await application.save();

    res.json({ message: "Application submitted successfully!" });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

module.exports = router;