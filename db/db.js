const mongoose = require('mongoose');


const db = async () =>{
    try{

        await mongoose.connect(process.env.MONGO_URL);

        console.log("database is connected");
    } catch (error){

        console.log("db error",error);
    }
};

module.exports = db;  