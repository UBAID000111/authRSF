const mongoose = require('mongoose');
const { type } = require('os');
const userSchema2 = new mongoose.Schema({
name: {
    type: String
},

query: {
    type: String
},

description: {
    type: String
},
},{timestamps: true});

module.exports =  mongoose.model("user2",userSchema2);