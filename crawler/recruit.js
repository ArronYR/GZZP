"use strict";

const eventproxy = require('eventproxy');
const install = require('superagent-charset');
const superagent = install(require('superagent'));
const cheerio = require('cheerio');
const escaper = require("true-html-escape");
const moment = require('moment');
const __ = require('underscore');
const async = require('async');
const url = require('url');
const mysql = require('mysql');
const config = require('./config');
const JPush = require("./node_modules/jpush-sdk/lib/JPush/JPush.js");
const client = JPush.buildClient(config.jpush_appkey, config.jpush_secret);

const gzzpHost = 'www.163gz.com';
const maxConcurrency = 3;
// 得到一个 eventproxy 的实例
const ep = new eventproxy();
const connection = mysql.createConnection(config);
connection.connect();

var messages = [];
var no_content_messages = [];
var new_messages = 0;

const urls = [{
    type: 2,
    url: "http://www.163gz.com/gzzp8/gz-zp/"
}, {
    type: 3,
    url: "http://www.163gz.com/gzzp8/gyzp/"
}, {
    type: 4,
    url: "http://www.163gz.com/gzzp8/bj/"
}, {
    type: 5,
    url: "http://www.163gz.com/gzzp8/zyzp/"
}, {
    type: 6,
    url: "http://www.163gz.com/gzzp8/tr/"
}, {
    type: 7,
    url: "http://www.163gz.com/gzzp8/qdn/"
}, {
    type: 8,
    url: "http://www.163gz.com/gzzp8/qxn/"
}, {
    type: 9,
    url: "http://www.163gz.com/gzzp8/qn/"
}, {
    type: 10,
    url: "http://www.163gz.com/gzzp8/as/"
}, {
    type: 11,
    url: "http://www.163gz.com/gzzp8/lps/"
}];

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
                    console.log(moment().format('YYYY-MM-DD HH:mm:ss') + ' [insert_query] ', query.sql);
                }
                callback(null);
            });
        },
        function (callback) {
            ep.emit('insert_data', message);
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
                    console.log(moment().format('YYYY-MM-DD HH:mm:ss') + ' [update_query] ');
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
    connection.query("SELECT * FROM messages WHERE (content is null OR content = '') AND type in (2, 3, 4, 5, 6, 7, 8, 9, 10, 11) ORDER BY id DESC LIMIT 15", function (err, rows) {
        if (err) throw err;
        rows.forEach(function (row, index, rows) {
            let msgInfo = {
                id: row.id,
                url: row.url,
            };
            no_content_messages.push(msgInfo);
        });
        // 命令 ep 重复监听 要获取内容的消息集合no_content_messages.length 次 `update_data` 事件再行动
        ep.after('update_data', no_content_messages.length, function (results) {
            console.log(moment().format('YYYY-MM-DD HH:mm:ss') + ' [update_data_end] ');
            // 关闭数据库连接
            connection.end();
            // connection.destroy();
            // 执行推送
            if (new_messages) {
                push("哇，有新的职位招聘来啦~~，共有" + new_messages + "条哦");
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
        .on('error', function (error) {
            callback(null, message);
        })
        .end(function (err, res) {
            if (err || typeof res.text == 'undefined') {
                return callback(err, null);
            }
            let $ = cheerio.load(res.text, {
                decodeEntities: false
            });
            $('#zoom').children('table').remove();
            message.content = __.unescape($('#zoom').html().replace(/(^\s*)|(\s*$)/g, ""));
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
 * 分别抓取不同分类下的信息
 * @param {页面对象，type、url} obj 
 * @param {回调} callback 
 */
var fetchUrl = function (obj, callback) {
    superagent.get(obj.url)
        .charset() // 设置编码，默认utf-8
        .end(function (err, res) {
            if (err || typeof res.text == 'undefined') {
                return console.log(err);
            }
            let $ = cheerio.load(res.text, {
                decodeEntities: false
            });
            $('td.style334').parent('tr').each(function (index, element) {
                let $element = $(element);
                let $anchor = $element.children('td').eq(1).children('a');
                let parseUrl = url.parse($anchor.attr('href'));

                // 只获取当前域名下的消息
                if (parseUrl.host == gzzpHost) {
                    let fontColor = $anchor.children('font').attr('color');
                    if (typeof fontColor == 'undefined') {
                        fontColor = "";
                    }
                    let title = escaper.unescape($anchor.text());
                    let time = $element.children('td').eq(2).text().replace(/(^\s*)|(\s*$)/g, "").replace(/[\u4e00-\u9fa5]+/g, "-");
                    let href = $anchor.attr('href');

                    let msgInfo = {
                        title: title,
                        url: href,
                        color: fontColor,
                        type: obj.type,
                        published_at: time.substr(0, time.length - 1)
                    };
                    messages.push(msgInfo);
                }
            });
            callback(null, obj.url);
        });
};

/**
 * 推送消息
 * @param {消息内容} msg 
 */
function push(msg) {
    client.push().setPlatform(JPush.ALL)
        .setAudience(JPush.ALL)
        .setNotification('公司招聘-通知', JPush.android(msg, null, 1))
        .send(function (err, res) {
            if (err) {
                console.log(err.message)
            } else {
                console.log('Sendno: ' + res.sendno)
                console.log('Msg_id: ' + res.msg_id)
            }
        });
    // process.exit(0);
}

// 程序入口
async.mapLimit(urls, maxConcurrency, function (url, callback) {
    fetchUrl(url, callback);
}, function (err, result) {
    console.log(moment().format('YYYY-MM-DD HH:mm:ss') + ' [message] ', messages.length);
    // 命令 ep 重复监听 messages.length 次 `insert_data` 事件再行动
    ep.after('insert_data', messages.length, function (results) {
        console.log(moment().format('YYYY-MM-DD HH:mm:ss') + ' [insert_data_end] ');
        getNeedContentMessages();
    });
    // 插入每一条消息
    messages.forEach(function (message, idx, messages) {
        inset(message);
    });
});