const dotenv = require('dotenv/config.js');
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');
const db = require('./db/db.js');
const userRoute = require('./routes/userRoutes.js');

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

app.use("/api/user", userRoute)

db().then(() => {

    app.listen(PORT, () => {
        console.log(`Server is running on ${PORT}`);
    });
});




