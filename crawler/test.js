var async = require('async');
require("date-utils");

var JPush = require("./node_modules/jpush-sdk/lib/JPush/JPush.js");
var client = JPush.buildClient('6fcda52aa00e363bd10e1037', '1d72fe2795f8bde4c3ec216f');

function seriesFunc() {
    async.series([
            function (callback) {
                console.log("text", 1);
                callback(null, 1);
            },
            function (callback) {
                console.log("text", 2);
                callback('error', 2);
            },
            function (callback) {
                console.log("text", 3);
                callback(null, 3);
            }
        ],
        function (error, result) {
            if (error) {
                console.log("error: ", error, "msg: ", result);
            } else {
                console.log("方法执行完毕" + result);
            }
        }
    );
}
// seriesFunc();

function date() {
    console.log(Date.today().toFormat('HH'));
}
date();

function push() {
    client.push().setPlatform(JPush.ALL)
        .setAudience(JPush.ALL)
        .setNotification('Hi, JPush', JPush.android('', null, 1))
        .send(function (err, res) {
            if (err) {
                console.log(err.message)
            } else {
                console.log('Sendno: ' + res.sendno)
                console.log('Msg_id: ' + res.msg_id)
            }
        });
}

// push();