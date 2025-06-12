const express = require('express');
const multer = require('multer');
const { storage } = require('../config/storage');
const Application = require('../models/Application');

const upload = multer({ dest: 'uploads/' });
const router = express.Router();

router.post('/apply', upload.single('resume'), async (req, res) => {
  try {
    const { name, phone, email, internshipTitle, skills, areaOfInterest, resumeUrl } = req.body;
    console.log(req.body);
    console.log(req.file);
    

    const newApplication = new Application({
      name, phone, email, internshipTitle, skills, areaOfInterest, resumeUrl
    });

    await newApplication.save();
    res.json({success: true, message: 'Application submitted successfully'});
  } catch (error) {
    res.status(500).json({ message: 'Error submitting application' });
  }
});

module.exports = router;