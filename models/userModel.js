const mongoose = require('mongoose');
const { type } = require('os');
const userSchema = new mongoose.Schema({
    username:{
        type: String,
        required: true,
        unique: true
    },
    
    password: {
        type: String,
        required: true
    },
},{timestamps:true});

module.exports =  mongoose.model("user",userSchema);