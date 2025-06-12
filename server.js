require ('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');
const db = require('./db/db.js');
const userRoute = require('./routes/userRoutes.js');
const internshipRoutes = require("./routes/internshipRoutes.js");
const applicationRoutes = require('./routes/applicationRoutes.js')


const app = express();

const PORT = process.env.PORT || 3000;

app.use(cors());

app.use(express.json())
const Internship = require('./models/InternshipModel.js');
app.get('/internships', async (req, res)=> {
    try{
        const internships = await Internship.find();
        res.json(internships);

    }catch (error) {
        res.status(500).json({error: 'Server error'});
    }
});

app.use("/api/user", userRoute);
app.use("/api/internships", internshipRoutes);
app.use("/api/applications", applicationRoutes);

db().then(() => {

    app.listen(PORT, () => {
        console.log(`Server is running on ${PORT}`);
    });
});




