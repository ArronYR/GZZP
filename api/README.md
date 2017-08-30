# 【接口】
--
## 响应参数说明
|响应参数|      类型       |说明|
|:-----:|:--------------:|:--:|
| result|object \|\| null|返回内容主体|
| code  |     number     |执行结构code|
|message|     string     |执行结构消息|

#### 响应示例

```json
{
	"result": {},
	"code": 1001,
	"message": "成功获取诗信息"
}
```

## 接口列表
* [1.信息相关接口](#1)
	* [1.1 根据`id`获取信息](#1.1) 
	* [1.2 获取信息集合](#1.2) 
	* [1.3 获取信息总数](#1.3)
* [2.用户相关接口](#2)
	* [2.1 注册](#2.1) 
	* [2.2 登录](#2.2) 
	* [2.3 收藏](#2.3)
	* [2.4 取消收藏](#2.4)
	* [2.5 收藏列表](#2.5)

## 接口详情
<span id="1"></span>
<span id="1.1"></span>
#### 1.1 根据`id`获取信息
- 请求URL
> [/api/message/{id}](#1.1)
 
- 请求方式
> GET

- 请求参数
> | 请求参数 | 参数类型 | 是否可选 | 默认值 |  参数说明  |
> |:-------:|:------:|:-------:|:-----:|:---------:|
> |   id    |   int  |   必填   |   0   |诗词id|

- 返回示例
> ```json
{
    "error": 0,
    "msg": "获取成功",
    "result": {
        "id": 1,
        "title": "标题",
        "color": "",
        "url": "",
        "content": "内容",
        "published_at": "2017-04-29",
        "type": "1",
        "type_text": "人事招考",
        "created_at": "2017-04-30 03:00:04"
    }
}
```

<span id="1.2"></span>
#### 1.2 获取信息集合 
- 请求URL
> [/api/messages](#1.2)
 
- 请求方式
> GET

- 请求参数
> | 请求参数 | 参数类型 | 是否可选 | 默认值 |  参数说明  |
> |:-------:|:------:|:-------:|:-----:|:---------:|
> |   page  |   int  |   可选   |   1   |页码|
> |   keyword  | string |   可选   | null |消息标题关键词|
> |   type  |   int  |   可选   |   1   |消息类型|

- 返回示例
> ```json
{
    "error": 0,
    "msg": "获取成功",
    "result": {
        "total": 4727,
        "per_page": 20,
        "current_page": 4,
        "last_page": 237,
        "next_page_url": "/api/messages?page=5",
        "prev_page_url": "/api/messages?page=3",
        "from": 61,
        "to": 80,
        "data": [
            {
                "id": 6510,
                "title": "标题",
                "type": "1",
                "url": "",
                "color": "",
                "published_at": "2017-08-28",
                "created_at": "2017-08-28 22:00:58",
                "updated_at": "2017-08-28 22:01:02",
                "type_text": "人事招考"
            }
        ]
    }
}
```

<span id="1.3"></span>
#### 1.3 获取信息总数 
- 请求URL
> [/api/messages/count](#1.3)
 
- 请求方式
> GET

- 请求参数
> | 请求参数 | 参数类型 | 是否可选 | 默认值 |  参数说明  |
> |:-------:|:------:|:-------:|:-----:|:---------:|
> |   type  |   int  |   可选   |   null   |消息类型|

- 返回示例
> ```json
{
    "error": 0,
    "msg": "获取成功",
    "result": 6582
}
```

<span id="2"></span>
<span id="2.1"></span>
#### 2.1 注册 
- 请求URL
> [/api/register](#2.1)
 
- 请求方式
> POST

- 请求参数
> | 请求参数 | 参数类型 | 是否可选 | 默认值 |  参数说明  |
> |:-------:|:------:|:-------:|:-----:|:---------:|
> |   email    |   int  |   必填   |   null   |邮箱|
> |   password  | string |   必填   | null |密码|
> |   nickname  |   int  |   必填   |   null   |昵称|

- 返回示例
> ```json
{
    "error": 0,
    "msg": "注册成功"
}
```

<span id="2.2"></span>
#### 2.2 登陆 
- 请求URL
> [/api/login](#2.2)
 
- 请求方式
> POST

- 请求参数
> | 请求参数 | 参数类型 | 是否可选 | 默认值 |  参数说明  |
> |:-------:|:------:|:-------:|:-----:|:---------:|
> |   email  |   int  |   必填   |   null   |邮箱|
> |   password  | string |   必填   | null |密码|

- 返回示例
> ```json
{
    "error": 0,
    "msg": "登陆成功",
    "result": {
        "id": 1,
        "name": "Arron",
        "email": "email@email.com",
        "token": "",
        "created_at": "2017-04-30 10:12:25",
        "updated_at": "2017-04-30 12:12:41"
    }
}
```

<span id="2.3"></span>
#### 2.3 收藏 
- 请求URL
> [/api/collection](#2.3)
 
- 请求方式
> POST

- 请求参数
> | 请求参数 | 参数类型 | 是否可选 | 默认值 |  参数说明  |
> |:-------:|:------:|:-------:|:-----:|:---------:|
> |   uid  |   int  |   必填   |   null   | 用户id |
> |   mid  |   int  |   必填   |   null   | 信息id |

- 返回示例
> ```json
{
    "error": 0,
    "msg": "收藏成功"
}
```

<span id="2.4"></span>
#### 2.4 取消收藏 
- 请求URL
> [/api/unfavorite](#2.4)
 
- 请求方式
> POST

- 请求参数
> | 请求参数 | 参数类型 | 是否可选 | 默认值 |  参数说明  |
> |:-------:|:------:|:-------:|:-----:|:---------:|
> |   id  |   int  |   必填   |   null   | 收藏id |

- 返回示例
> ```json
{
    "error": 0,
    "msg": "取消成功"
}
```

<span id="2.5"></span>
#### 2.5 收藏列表 
- 请求URL
> [/api/collections/{id}](#2.4)
 
- 请求方式
> GET

- 请求参数
> | 请求参数 | 参数类型 | 是否可选 | 默认值 |  参数说明  |
> |:-------:|:------:|:-------:|:-----:|:---------:|
> |   id  |   int  |   必填   |   null   | 用户id |

- 返回示例
> ```json
{
    "error": 0,
    "msg": "获取成功",
    "result": {
        "total": 1,
        "per_page": 15,
        "current_page": 1,
        "last_page": 1,
        "next_page_url": null,
        "prev_page_url": null,
        "from": 1,
        "to": 1,
        "data": [
            {
                "id": 25,
                "user_id": "1",
                "message_id": "4496",
                "created_at": "2017-07-25 12:02:56",
                "user": {
                    "id": 1,
                    "name": "Arron",
                    "email": "yangyun4814@qq.com",
                    "token": "",
                    "created_at": "2017-04-30 10:12:25",
                    "updated_at": "2017-04-30 12:12:41"
                },
                "message": {
                    "id": 4496,
                    "title": "标题",
                    "type": "1",
                    "url": "",
                    "color": "",
                    "published_at": "2017-07-24",
                    "created_at": "2017-07-24 22:00:35",
                    "updated_at": "2017-07-24 22:00:37",
                    "type_text": "人事招考"
                }
            }
        ]
    }
}
```