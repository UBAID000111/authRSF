const userModel = require('../models/userModel.js');
const bcrypt = require('bcrypt');
const feedbackModel = require('../models/feedbackModel.js');
const InternshipModel = require('../models/InternshipModel.js');

// LOGIN FUNCTION
const Login = async (req, res) => {
    const { username, password } = req.body;

    try {
        const user = await userModel.findOne({ username });

        if (!user) {
            return res.send({
                message: "User not found",
                success: false,
            });
        }

        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.send({
                message: "Invalid credentials",
                success: false,
            });
        }

        return res.send({
            message: "Login successful",
            success: true,
        });

    } catch (error) {
        console.log(error);
        return res.send({ message: error.message, success: false });
    }
};

//SIGNUP FUNCTION
const Signup = async (req, res) => {
    const { username, password } = req.body;
    try {
        if (!username || !password) {
            return res.send({
                message: "Please fill in all fields",
                success: false,
            });
        }
        const checkexistuser = await userModel.findOne({ username: username });
        if (checkexistuser) {
            return res.send({
                message: "Username already exists", success: false
            })
        }
        const salt = await bcrypt.genSalt(10)
        const hashpassword = await bcrypt.hash(password, salt);
        console.log(hashpassword)
        const newuser = new userModel({
            username,
            password: hashpassword
        })
        await newuser.save();
        return res.send({
            message: "User created successfully", newuser, success: true
        })
    } catch (error) {
        console.log(error);
        return res.send({ message: error.message, success: false })
    }
};

//FEEDBACK FUNCTION
const Feedback = async (req, res) => {
    const { name, query, description } = req.body;
    try {
        if (!name || !query || !description) {
            return res.send({
                message: "Please fill in all fields",
                success: false
            });
        }
        const feedback = new feedbackModel({ name, query, description });
        await feedback.save();

        return res.send({
            message: "Feedback submitted successfully",
            success: true,
            data: feedback

        });
    } catch (error) {
        console.log(error);
        return res.send({ message: error.message, success: false });
    }
};

//Internship Function

const Internship = async (req, res) => {
    const { InternshipType, InternshipLocation, InternshipDuration, InternshipDescription, InternshipRequirements } = req.body;
    try {
        if (!InternshipType || !InternshipLocation || !InternshipDuration || !InternshipDescription || !InternshipRequirements) {
            return res.send({
                message: "Please fill in all fields",
                success: false
            });
        }
        const internship = new InternshipModel({ InternshipType, InternshipLocation, InternshipDuration, InternshipDescription, InternshipRequirements });
        await internship.save();

        return res.send({
            message: "Internship created successfully",
            success: true,
            data: internship

        });
    } catch (error) {
        console.log(error);
        return res.send({ message: error.message, success: false });
    }
};

//GET ALL INTERNSHIP
const getAllInternships = async (req, res) => {
    try{
        const internships = await InternshipModel.find();
        res.status(200).json(internships);
    }catch (error){
        res.status(500).json({error: error.message});
    }
};

module.exports = { Login, Signup, Feedback, Internship, getAllInternships };