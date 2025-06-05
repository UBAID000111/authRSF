const { Signup, Login , Feedback, Internship, getAllInternships} = require('../controller/userController.js');
const Router = require('express').Router;

const route = Router();

route.post("/signup",Signup)
route.post("/login",Login)
route.post("/feedback",Feedback)
route.post("/internship",Internship)
route.post("/internships",getAllInternships)

module.exports = route; 