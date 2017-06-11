const async = require('async');
const config = require('./config');
const JPush = require("./node_modules/jpush-sdk/lib/JPush/JPush.js");
const client = JPush.buildClient(config.jpush_appkey, config.jpush_secret);

require("date-utils");

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
    console.log(Date.today().toFormat('YYYY'));
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