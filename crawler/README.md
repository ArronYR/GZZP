### Install

在目录下新增`config.json`文件，主要是数据库、极光推送等相关配置，内容如下：

```json
{
    // 数据库
    "host": "127.0.0.1",
    "user": "root",
    "password": "password",
    "database": "database",
    "charset": "utf8mb4",

    // 极光推送
    "jpush_appkey": "6fcda52aae3bd10e1037",
    "jpush_secret": "1d72fe2795de4c3ec216f"
}
```

在项目下执行:
```cmd
npm install

// 抓取人事招考信息
node app.js

// 抓取公司招聘信息
node recruit.js
```