const mongoose = require('mongoose');
const { type } = require('os');
const userSchema3 = new mongoose.Schema({
InternshipType: {
    type: String
},
InternshipLocation: {
    type: String
    },
InternshipDuration: {
        type: String
        },
InternshipDescription: {
            type: String
            },
InternshipRequirements: {
                type: String
                }
},{timestamps:true});

module.exports =  mongoose.model("user3",userSchema3);