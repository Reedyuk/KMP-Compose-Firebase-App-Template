/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onRequest} = require("firebase-functions/v2/https");
const {initializeApp} = require("firebase-admin/app");
const { getFirestore } = require("firebase-admin/firestore");
const request = require('request');
const seedData = require('./data.json');

let app = initializeApp();
const db = getFirestore(app);

exports.seed = onRequest(
    { timeoutSeconds: 1200 },
    async (req, resp) => {
        let artCollection = db.collection("art");
        for (let i in seedData) {
            artCollection.add(seedData[i]);
        }
        resp
            .status(200)
            .send("success");
    }
);
