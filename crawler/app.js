"use strict";

const eventproxy = require('eventproxy');
const install = require('superagent-charset');
const superagent = install(require('superagent'));
const cheerio = require('cheerio');
const escaper = require("true-html-escape");
const async = require('async');
const url = require('url');
const mysql = require('mysql');
const config = require('./config');
const JPush = require("./node_modules/jpush-sdk/lib/JPush/JPush.js");
const client = JPush.buildClient(config.jpush_appkey, config.jpush_secret);

require("date-utils");

const gzzpHost = 'www.163gz.com';
const gzzpUrl = 'http://www.163gz.com/gzzp8/zkxx/';
const maxConcurrency = 5;
// 得到一个 eventproxy 的实例
const ep = new eventproxy();
const connection = mysql.createConnection(config);
connection.connect();

var messages = [];
var no_content_messages = [];
var new_messages = 0;

/**
 * 插入一条消息
 * @param {消息体} message 
 */
var inset = function (message) {
    // 使用async控制函数同步执行
    async.series([
        function (callback) {
            connection.query('SELECT * FROM messages WHERE url = ' + mysql.escape(message.url), function (err, rows) {
                if (err) throw err;
                if (rows.length == 0) {
                    var query = connection.query('INSERT INTO messages SET ?', message, function (err, result) {
                        if (err) throw err;
                        new_messages += 1;
                    });
                    console.log(Date.today().toFormat('YYYY-MM-DD HH24:MI:SS') + ' [insert_query] ', query.sql);
                }
                callback(null);
            });
        },
        function (callback) {
            ep.emit('inset_data', message);
            callback(null);
        }
    ]);
};

/**
 * 更新消息，主要是更新消息的content
 * @param {消息体} message 
 */
var update = function (message) {
    // 使用async控制函数同步执行
    async.series([
        function (callback) {
            if (message) {
                var query = connection.query('UPDATE messages SET ? WHERE id = ' + mysql.escape(message.id), {
                    content: message.content
                }, function (err, result) {
                    if (err) throw err;
                    console.log(Date.today().toFormat('YYYY-MM-DD HH24:MI:SS') + ' [update_query] ');
                    callback(null);
                });
            } else {
                callback(null);
            }
        },
        function (callback) {
            ep.emit('update_data', message);
            callback(null);
        }
    ]);
};

/**
 * 获取需要更新消息内容content的消息集合
 */
var getNeedContentMessages = function () {
    connection.query("SELECT * FROM messages WHERE content is null OR content = ''", function (err, rows) {
        if (err) throw err;
        rows.forEach(function (row, index, rows) {
            let msgInfo = {
                id: row.id,
                url: row.url
            };
            no_content_messages.push(msgInfo);
        });
        // 命令 ep 重复监听 要获取内容的消息集合no_content_messages.length 次 `update_data` 事件再行动
        ep.after('update_data', no_content_messages.length, function (results) {
            console.log(Date.today().toFormat('YYYY-MM-DD HH24:MI:SS') + ' [update_data_end] ');
            // 关闭数据库连接
            connection.end();
            // 执行推送
            if (new_messages > 0) {
                push("哇，有新的人事招考信息来啦~~，共有" + new_messages + "条哦");
            }
        });
        fetchContents(no_content_messages);
    });
};

/**
 * 获取消息的内容content
 * @param {消息体} message 
 * @param {回调函数} callback 
 */
var fetchContent = function (message, callback) {
    superagent.get(message.url)
        .charset()
        .end(function (err, res) {
            if (err || typeof res.text == 'undefined') {
                return callback(err, null);
            }
            let $ = cheerio.load(res.text, {
                decodeEntities: false
            });
            let content = $('#zoom').children('table').remove();
            message.content = escaper.unescape($('#zoom').html()).replace(/(^\s*)|(\s*$)/g, "");
            callback(null, message);
        });
};

/**
 * 使用async并发控制消息获取
 * @param {所有要回去内容content的消息集合} messages 
 */
var fetchContents = function (messages) {
    async.mapLimit(messages, maxConcurrency, function (message, callback) {
        fetchContent(message, callback);
    }, function (err, result) {
        if (err) {
            return console.log(err);
        }
        result.forEach(function (message, idx, messages) {
            update(message);
        });
    });
};

/**
 * 推送消息
 * @param {消息内容} msg 
 */
function push(msg) {
    client.push().setPlatform(JPush.ALL)
        .setAudience(JPush.ALL)
        .setNotification('人事招考-通知', JPush.android(msg, null, 1))
        .send(function (err, res) {
            if (err) {
                console.log(err.message)
            } else {
                console.log('Sendno: ' + res.sendno)
                console.log('Msg_id: ' + res.msg_id)
            }
        });
}

/**
 * 入口方法
 */
superagent.get(gzzpUrl)
    .charset() // 设置编码，默认utf-8
    .end(function (err, res) {
        if (err || typeof res.text == 'undefined') {
            return console.log(err);
        }
        let $ = cheerio.load(res.text, {
            decodeEntities: false
        });
        $('span.style331 a').each(function (index, element) {
            let $element = $(element);
            let parseUrl = url.parse($element.attr('href'));
            // 只获取当前域名下的消息
            if (parseUrl.host == gzzpHost) {
                let fontColor = $element.children('font').attr('color');
                if (typeof fontColor == 'undefined') {
                    fontColor = "";
                }
                let title = escaper.unescape($element.text());

                let strArr = title.split('·');
                let time = strArr[0].replace(/(^\s*)|(\s*$)/g, "");
                let pureTitle = strArr[1].replace(/(^\s*)|(\s*$)/g, "");
                let href = $element.attr('href');

                let msgInfo = {
                    title: pureTitle,
                    url: href,
                    color: fontColor,
                    type: 1,
                    published_at: Date.today().toFormat('YYYY') + "-" + time
                };
                messages.push(msgInfo);
                // console.log(time, pureTitle, href, fontColor);
            }
        });
        console.log(Date.today().toFormat('YYYY-MM-DD HH24:MI:SS') + ' [message] ', messages.length);
        // 命令 ep 重复监听 messages.length 次 `inset_data` 事件再行动
        ep.after('inset_data', messages.length, function (results) {
            console.log(Date.today().toFormat('YYYY-MM-DD HH24:MI:SS') + ' [inset_data_end] ');
            getNeedContentMessages();
        });
        // 插入每一条消息
        messages.forEach(function (message, idx, messages) {
            inset(message);
        });
    });