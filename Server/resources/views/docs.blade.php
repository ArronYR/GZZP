<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>{{ config('app.name', 'Laravel') }}</title>
  <style type="text/css">
    body {
      font-family: Helvetica, arial, sans-serif;
      font-size: 14px;
      line-height: 1.6;
      padding-top: 10px;
      padding-bottom: 10px;
      background-color: white;
      padding: 30px;
    }

    body>*:first-child {
      margin-top: 0 !important;
    }

    body>*:last-child {
      margin-bottom: 0 !important;
    }

    a {
      color: #4183C4;
    }

    a.absent {
      color: #cc0000;
    }

    a.anchor {
      display: block;
      padding-left: 30px;
      margin-left: -30px;
      cursor: pointer;
      position: absolute;
      top: 0;
      left: 0;
      bottom: 0;
    }

    h1,
    h2,
    h3,
    h4,
    h5,
    h6 {
      margin: 20px 0 10px;
      padding: 0;
      font-weight: bold;
      -webkit-font-smoothing: antialiased;
      cursor: text;
      position: relative;
    }

    h1:hover a.anchor,
    h2:hover a.anchor,
    h3:hover a.anchor,
    h4:hover a.anchor,
    h5:hover a.anchor,
    h6:hover a.anchor {
      background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA09pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoMTMuMCAyMDEyMDMwNS5tLjQxNSAyMDEyLzAzLzA1OjIxOjAwOjAwKSAgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OUM2NjlDQjI4ODBGMTFFMTg1ODlEODNERDJBRjUwQTQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OUM2NjlDQjM4ODBGMTFFMTg1ODlEODNERDJBRjUwQTQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo5QzY2OUNCMDg4MEYxMUUxODU4OUQ4M0REMkFGNTBBNCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5QzY2OUNCMTg4MEYxMUUxODU4OUQ4M0REMkFGNTBBNCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PsQhXeAAAABfSURBVHjaYvz//z8DJYCRUgMYQAbAMBQIAvEqkBQWXI6sHqwHiwG70TTBxGaiWwjCTGgOUgJiF1J8wMRAIUA34B4Q76HUBelAfJYSA0CuMIEaRP8wGIkGMA54bgQIMACAmkXJi0hKJQAAAABJRU5ErkJggg==) no-repeat 10px center;
      text-decoration: none;
    }

    h1 tt,
    h1 code {
      font-size: inherit;
    }

    h2 tt,
    h2 code {
      font-size: inherit;
    }

    h3 tt,
    h3 code {
      font-size: inherit;
    }

    h4 tt,
    h4 code {
      font-size: inherit;
    }

    h5 tt,
    h5 code {
      font-size: inherit;
    }

    h6 tt,
    h6 code {
      font-size: inherit;
    }

    h1 {
      font-size: 28px;
      color: black;
    }

    h2 {
      font-size: 24px;
      border-bottom: 1px solid #cccccc;
      color: black;
    }

    h3 {
      font-size: 18px;
    }

    h4 {
      font-size: 16px;
    }

    h5 {
      font-size: 14px;
    }

    h6 {
      color: #777777;
      font-size: 14px;
    }

    p,
    blockquote,
    ul,
    ol,
    dl,
    li,
    table,
    pre {
      margin: 15px 0;
    }

    hr {
      background: transparent url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAECAYAAACtBE5DAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBNYWNpbnRvc2giIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OENDRjNBN0E2NTZBMTFFMEI3QjRBODM4NzJDMjlGNDgiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OENDRjNBN0I2NTZBMTFFMEI3QjRBODM4NzJDMjlGNDgiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4Q0NGM0E3ODY1NkExMUUwQjdCNEE4Mzg3MkMyOUY0OCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4Q0NGM0E3OTY1NkExMUUwQjdCNEE4Mzg3MkMyOUY0OCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PqqezsUAAAAfSURBVHjaYmRABcYwBiM2QSA4y4hNEKYDQxAEAAIMAHNGAzhkPOlYAAAAAElFTkSuQmCC) repeat-x 0 0;
      border: 0 none;
      color: #cccccc;
      height: 4px;
      padding: 0;
    }

    body>h2:first-child {
      margin-top: 0;
      padding-top: 0;
    }

    body>h1:first-child {
      margin-top: 0;
      padding-top: 0;
    }

    body>h1:first-child+h2 {
      margin-top: 0;
      padding-top: 0;
    }

    body>h3:first-child,
    body>h4:first-child,
    body>h5:first-child,
    body>h6:first-child {
      margin-top: 0;
      padding-top: 0;
    }

    a:first-child h1,
    a:first-child h2,
    a:first-child h3,
    a:first-child h4,
    a:first-child h5,
    a:first-child h6 {
      margin-top: 0;
      padding-top: 0;
    }

    h1 p,
    h2 p,
    h3 p,
    h4 p,
    h5 p,
    h6 p {
      margin-top: 0;
    }

    li p.first {
      display: inline-block;
    }

    li {
      margin: 0;
    }

    ul,
    ol {
      padding-left: 30px;
    }

    ul :first-child,
    ol :first-child {
      margin-top: 0;
    }

    dl {
      padding: 0;
    }

    dl dt {
      font-size: 14px;
      font-weight: bold;
      font-style: italic;
      padding: 0;
      margin: 15px 0 5px;
    }

    dl dt:first-child {
      padding: 0;
    }

    dl dt> :first-child {
      margin-top: 0;
    }

    dl dt> :last-child {
      margin-bottom: 0;
    }

    dl dd {
      margin: 0 0 15px;
      padding: 0 15px;
    }

    dl dd> :first-child {
      margin-top: 0;
    }

    dl dd> :last-child {
      margin-bottom: 0;
    }

    blockquote {
      border-left: 4px solid #dddddd;
      padding: 0 15px;
      color: #777777;
    }

    blockquote> :first-child {
      margin-top: 0;
    }

    blockquote> :last-child {
      margin-bottom: 0;
    }

    table {
      padding: 0;
      border-collapse: collapse;
    }

    table tr {
      border-top: 1px solid #cccccc;
      background-color: white;
      margin: 0;
      padding: 0;
    }

    table tr:nth-child(2n) {
      background-color: #f8f8f8;
    }

    table tr th {
      font-weight: bold;
      border: 1px solid #cccccc;
      margin: 0;
      padding: 6px 13px;
    }

    table tr td {
      border: 1px solid #cccccc;
      margin: 0;
      padding: 6px 13px;
    }

    table tr th :first-child,
    table tr td :first-child {
      margin-top: 0;
    }

    table tr th :last-child,
    table tr td :last-child {
      margin-bottom: 0;
    }

    img {
      max-width: 100%;
    }

    span.frame {
      display: block;
      overflow: hidden;
    }

    span.frame>span {
      border: 1px solid #dddddd;
      display: block;
      float: left;
      overflow: hidden;
      margin: 13px 0 0;
      padding: 7px;
      width: auto;
    }

    span.frame span img {
      display: block;
      float: left;
    }

    span.frame span span {
      clear: both;
      color: #333333;
      display: block;
      padding: 5px 0 0;
    }

    span.align-center {
      display: block;
      overflow: hidden;
      clear: both;
    }

    span.align-center>span {
      display: block;
      overflow: hidden;
      margin: 13px auto 0;
      text-align: center;
    }

    span.align-center span img {
      margin: 0 auto;
      text-align: center;
    }

    span.align-right {
      display: block;
      overflow: hidden;
      clear: both;
    }

    span.align-right>span {
      display: block;
      overflow: hidden;
      margin: 13px 0 0;
      text-align: right;
    }

    span.align-right span img {
      margin: 0;
      text-align: right;
    }

    span.float-left {
      display: block;
      margin-right: 13px;
      overflow: hidden;
      float: left;
    }

    span.float-left span {
      margin: 13px 0 0;
    }

    span.float-right {
      display: block;
      margin-left: 13px;
      overflow: hidden;
      float: right;
    }

    span.float-right>span {
      display: block;
      overflow: hidden;
      margin: 13px auto 0;
      text-align: right;
    }

    code,
    tt {
      margin: 0 2px;
      padding: 0 5px;
      white-space: nowrap;
      border: 1px solid #eaeaea;
      background-color: #f8f8f8;
      border-radius: 3px;
    }

    pre code {
      margin: 0;
      padding: 0;
      white-space: pre;
      border: none;
      background: transparent;
    }

    .highlight pre {
      background-color: #f8f8f8;
      border: 1px solid #cccccc;
      font-size: 13px;
      line-height: 19px;
      overflow: auto;
      padding: 6px 10px;
      border-radius: 3px;
    }

    pre {
      background-color: #f8f8f8;
      border: 1px solid #cccccc;
      font-size: 13px;
      line-height: 19px;
      overflow: auto;
      padding: 6px 10px;
      border-radius: 3px;
    }

    pre code,
    pre tt {
      background-color: transparent;
      border: none;
    }

    sup {
      font-size: 0.83em;
      vertical-align: super;
      line-height: 0;
    }

    * {
      -webkit-print-color-adjust: exact;
    }

    @media screen and (min-width: 914px) {
      body {
        width: 854px;
        margin: 0 auto;
      }
    }

    @media print {
      table,
      pre {
        page-break-inside: avoid;
      }
      pre {
        word-wrap: break-word;
      }
    }
  </style>
</head>

<body>
  <h1 id="toc_0">【接口】</h1>

  <h2 id="toc_1"></h2>

  <h2 id="toc_2">响应参数说明</h2>

  <table>
    <thead>
      <tr>
        <th style="text-align: center">响应参数</th>
        <th style="text-align: center">类型</th>
        <th style="text-align: center">说明</th>
      </tr>
    </thead>

    <tbody>
      <tr>
        <td style="text-align: center">result</td>
        <td style="text-align: center">object || null</td>
        <td style="text-align: center">返回内容主体</td>
      </tr>
      <tr>
        <td style="text-align: center">code</td>
        <td style="text-align: center">number</td>
        <td style="text-align: center">执行结构code</td>
      </tr>
      <tr>
        <td style="text-align: center">msg</td>
        <td style="text-align: center">string</td>
        <td style="text-align: center">执行结构消息</td>
      </tr>
    </tbody>
  </table>

  <h4 id="toc_3">响应示例</h4>

  <pre><code class="language-javascript">{
    &quot;result&quot;: {},
    &quot;code&quot;: 1001,
    &quot;message&quot;: &quot;成功获取信息&quot;
}</code></pre>

  <h2 id="toc_4">接口列表</h2>

  <ul>
    <li><a href="#1">1.信息相关接口</a>

      <ul>
        <li><a href="#1.1">1.1 根据<code>id</code>获取信息</a> </li>
        <li><a href="#1.2">1.2 获取信息集合</a> </li>
        <li><a href="#1.3">1.3 获取信息总数</a></li>
      </ul>
    </li>
    <li><a href="#2">2.用户相关接口</a>

      <ul>
        <li><a href="#2.1">2.1 注册</a> </li>
        <li><a href="#2.2">2.2 登录</a> </li>
        <li><a href="#2.3">2.3 收藏</a></li>
        <li><a href="#2.4">2.4 取消收藏</a></li>
        <li><a href="#2.5">2.5 收藏列表</a></li>
      </ul>
    </li>
  </ul>

  <h2 id="toc_5">接口详情</h2>

  <p><span id="1"></span>
    <span id="1.1"></span></p>

  <h4 id="toc_6">1.1 根据<code>id</code>获取信息</h4>

  <ul>
    <li>
      <p>请求URL</p>

      <blockquote>
        <p><a href="#1.1">/api/message/{id}</a></p>
      </blockquote>
    </li>
    <li>
      <p>请求方式</p>

      <blockquote>
        <p>GET</p>
      </blockquote>
    </li>
    <li>
      <p>请求参数</p>

      <blockquote>
        <table>
          <thead>
            <tr>
              <th style="text-align: center">请求参数</th>
              <th style="text-align: center">参数类型</th>
              <th style="text-align: center">是否可选</th>
              <th style="text-align: center">默认值</th>
              <th style="text-align: center">参数说明</th>
            </tr>
          </thead>

          <tbody>
            <tr>
              <td style="text-align: center">id</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">0</td>
              <td style="text-align: center">诗词id</td>
            </tr>
          </tbody>
        </table>
      </blockquote>
    </li>
    <li>
      <p>返回示例</p>

      <blockquote>
        <pre><code class="language-javascript">{
    &quot;error&quot;: 0,
    &quot;msg&quot;: &quot;获取成功&quot;,
    &quot;result&quot;: {
        &quot;id&quot;: 1,
        &quot;title&quot;: &quot;标题&quot;,
        &quot;color&quot;: &quot;&quot;,
        &quot;url&quot;: &quot;&quot;,
        &quot;content&quot;: &quot;内容&quot;,
        &quot;published_at&quot;: &quot;2017-04-29&quot;,
        &quot;type&quot;: &quot;1&quot;,
        &quot;type_text&quot;: &quot;人事招考&quot;,
        &quot;created_at&quot;: &quot;2017-04-30 03:00:04&quot;
    }
}</code></pre>
      </blockquote>
    </li>
  </ul>

  <p><span id="1.2"></span></p>

  <h4 id="toc_7">1.2 获取信息集合</h4>

  <ul>
    <li>
      <p>请求URL</p>

      <blockquote>
        <p><a href="#1.2">/api/messages</a></p>
      </blockquote>
    </li>
    <li>
      <p>请求方式</p>

      <blockquote>
        <p>GET</p>
      </blockquote>
    </li>
    <li>
      <p>请求参数</p>

      <blockquote>
        <table>
          <thead>
            <tr>
              <th style="text-align: center">请求参数</th>
              <th style="text-align: center">参数类型</th>
              <th style="text-align: center">是否可选</th>
              <th style="text-align: center">默认值</th>
              <th style="text-align: center">参数说明</th>
            </tr>
          </thead>

          <tbody>
            <tr>
              <td style="text-align: center">page</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">可选</td>
              <td style="text-align: center">1</td>
              <td style="text-align: center">页码</td>
            </tr>
            <tr>
              <td style="text-align: center">keyword</td>
              <td style="text-align: center">string</td>
              <td style="text-align: center">可选</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">消息标题关键词</td>
            </tr>
            <tr>
              <td style="text-align: center">type</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">可选</td>
              <td style="text-align: center">1</td>
              <td style="text-align: center">消息类型</td>
            </tr>
          </tbody>
        </table>
      </blockquote>
    </li>
    <li>
      <p>返回示例</p>

      <blockquote>
        <pre><code class="language-javascript">{
    &quot;error&quot;: 0,
    &quot;msg&quot;: &quot;获取成功&quot;,
    &quot;result&quot;: {
        &quot;total&quot;: 4727,
        &quot;per_page&quot;: 20,
        &quot;current_page&quot;: 4,
        &quot;last_page&quot;: 237,
        &quot;next_page_url&quot;: &quot;/api/messages?page=5&quot;,
        &quot;prev_page_url&quot;: &quot;/api/messages?page=3&quot;,
        &quot;from&quot;: 61,
        &quot;to&quot;: 80,
        &quot;data&quot;: [
            {
                &quot;id&quot;: 6510,
                &quot;title&quot;: &quot;标题&quot;,
                &quot;type&quot;: &quot;1&quot;,
                &quot;url&quot;: &quot;&quot;,
                &quot;color&quot;: &quot;&quot;,
                &quot;published_at&quot;: &quot;2017-08-28&quot;,
                &quot;created_at&quot;: &quot;2017-08-28 22:00:58&quot;,
                &quot;updated_at&quot;: &quot;2017-08-28 22:01:02&quot;,
                &quot;type_text&quot;: &quot;人事招考&quot;
            }
        ]
    }
}</code></pre>
      </blockquote>
    </li>
  </ul>

  <p><span id="1.3"></span></p>

  <h4 id="toc_8">1.3 获取信息总数</h4>

  <ul>
    <li>
      <p>请求URL</p>

      <blockquote>
        <p><a href="#1.3">/api/messages/count</a></p>
      </blockquote>
    </li>
    <li>
      <p>请求方式</p>

      <blockquote>
        <p>GET</p>
      </blockquote>
    </li>
    <li>
      <p>请求参数</p>

      <blockquote>
        <table>
          <thead>
            <tr>
              <th style="text-align: center">请求参数</th>
              <th style="text-align: center">参数类型</th>
              <th style="text-align: center">是否可选</th>
              <th style="text-align: center">默认值</th>
              <th style="text-align: center">参数说明</th>
            </tr>
          </thead>

          <tbody>
            <tr>
              <td style="text-align: center">type</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">可选</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">消息类型</td>
            </tr>
          </tbody>
        </table>
      </blockquote>
    </li>
    <li>
      <p>返回示例</p>

      <blockquote>
        <pre><code class="language-javascript">{
    &quot;error&quot;: 0,
    &quot;msg&quot;: &quot;获取成功&quot;,
    &quot;result&quot;: 6582
}</code></pre>
      </blockquote>
    </li>
  </ul>

  <p><span id="2"></span>
    <span id="2.1"></span></p>

  <h4 id="toc_9">2.1 注册</h4>

  <ul>
    <li>
      <p>请求URL</p>

      <blockquote>
        <p><a href="#2.1">/api/register</a></p>
      </blockquote>
    </li>
    <li>
      <p>请求方式</p>

      <blockquote>
        <p>POST</p>
      </blockquote>
    </li>
    <li>
      <p>请求参数</p>

      <blockquote>
        <table>
          <thead>
            <tr>
              <th style="text-align: center">请求参数</th>
              <th style="text-align: center">参数类型</th>
              <th style="text-align: center">是否可选</th>
              <th style="text-align: center">默认值</th>
              <th style="text-align: center">参数说明</th>
            </tr>
          </thead>

          <tbody>
            <tr>
              <td style="text-align: center">email</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">邮箱</td>
            </tr>
            <tr>
              <td style="text-align: center">password</td>
              <td style="text-align: center">string</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">密码</td>
            </tr>
            <tr>
              <td style="text-align: center">nickname</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">昵称</td>
            </tr>
          </tbody>
        </table>
      </blockquote>
    </li>
    <li>
      <p>返回示例</p>

      <blockquote>
        <pre><code class="language-javascript">{
    &quot;error&quot;: 0,
    &quot;msg&quot;: &quot;注册成功&quot;
}</code></pre>
      </blockquote>
    </li>
  </ul>

  <p><span id="2.2"></span></p>

  <h4 id="toc_10">2.2 登陆</h4>

  <ul>
    <li>
      <p>请求URL</p>

      <blockquote>
        <p><a href="#2.2">/api/login</a></p>
      </blockquote>
    </li>
    <li>
      <p>请求方式</p>

      <blockquote>
        <p>POST</p>
      </blockquote>
    </li>
    <li>
      <p>请求参数</p>

      <blockquote>
        <table>
          <thead>
            <tr>
              <th style="text-align: center">请求参数</th>
              <th style="text-align: center">参数类型</th>
              <th style="text-align: center">是否可选</th>
              <th style="text-align: center">默认值</th>
              <th style="text-align: center">参数说明</th>
            </tr>
          </thead>

          <tbody>
            <tr>
              <td style="text-align: center">email</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">邮箱</td>
            </tr>
            <tr>
              <td style="text-align: center">password</td>
              <td style="text-align: center">string</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">密码</td>
            </tr>
          </tbody>
        </table>
      </blockquote>
    </li>
    <li>
      <p>返回示例</p>

      <blockquote>
        <pre><code class="language-javascript">{
    &quot;error&quot;: 0,
    &quot;msg&quot;: &quot;登陆成功&quot;,
    &quot;result&quot;: {
        &quot;id&quot;: 1,
        &quot;name&quot;: &quot;Arron&quot;,
        &quot;email&quot;: &quot;email@email.com&quot;,
        &quot;token&quot;: &quot;&quot;,
        &quot;created_at&quot;: &quot;2017-04-30 10:12:25&quot;,
        &quot;updated_at&quot;: &quot;2017-04-30 12:12:41&quot;
    }
}</code></pre>
      </blockquote>
    </li>
  </ul>

  <p><span id="2.3"></span></p>

  <h4 id="toc_11">2.3 收藏</h4>

  <ul>
    <li>
      <p>请求URL</p>

      <blockquote>
        <p><a href="#2.3">/api/collection</a></p>
      </blockquote>
    </li>
    <li>
      <p>请求方式</p>

      <blockquote>
        <p>POST</p>
      </blockquote>
    </li>
    <li>
      <p>请求参数</p>

      <blockquote>
        <table>
          <thead>
            <tr>
              <th style="text-align: center">请求参数</th>
              <th style="text-align: center">参数类型</th>
              <th style="text-align: center">是否可选</th>
              <th style="text-align: center">默认值</th>
              <th style="text-align: center">参数说明</th>
            </tr>
          </thead>

          <tbody>
            <tr>
              <td style="text-align: center">uid</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">用户id</td>
            </tr>
            <tr>
              <td style="text-align: center">mid</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">信息id</td>
            </tr>
          </tbody>
        </table>
      </blockquote>
    </li>
    <li>
      <p>返回示例</p>

      <blockquote>
        <pre><code class="language-javascript">{
    &quot;error&quot;: 0,
    &quot;msg&quot;: &quot;收藏成功&quot;
}</code></pre>
      </blockquote>
    </li>
  </ul>

  <p><span id="2.4"></span></p>

  <h4 id="toc_12">2.4 取消收藏</h4>

  <ul>
    <li>
      <p>请求URL</p>

      <blockquote>
        <p><a href="#2.4">/api/unfavorite</a></p>
      </blockquote>
    </li>
    <li>
      <p>请求方式</p>

      <blockquote>
        <p>POST</p>
      </blockquote>
    </li>
    <li>
      <p>请求参数</p>

      <blockquote>
        <table>
          <thead>
            <tr>
              <th style="text-align: center">请求参数</th>
              <th style="text-align: center">参数类型</th>
              <th style="text-align: center">是否可选</th>
              <th style="text-align: center">默认值</th>
              <th style="text-align: center">参数说明</th>
            </tr>
          </thead>

          <tbody>
            <tr>
              <td style="text-align: center">id</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">收藏id</td>
            </tr>
          </tbody>
        </table>
      </blockquote>
    </li>
    <li>
      <p>返回示例</p>

      <blockquote>
        <pre><code class="language-javascript">{
    &quot;error&quot;: 0,
    &quot;msg&quot;: &quot;取消成功&quot;
}</code></pre>
      </blockquote>
    </li>
  </ul>

  <p><span id="2.5"></span></p>

  <h4 id="toc_13">2.5 收藏列表</h4>

  <ul>
    <li>
      <p>请求URL</p>

      <blockquote>
        <p><a href="#2.4">/api/collections/{id}</a></p>
      </blockquote>
    </li>
    <li>
      <p>请求方式</p>

      <blockquote>
        <p>GET</p>
      </blockquote>
    </li>
    <li>
      <p>请求参数</p>

      <blockquote>
        <table>
          <thead>
            <tr>
              <th style="text-align: center">请求参数</th>
              <th style="text-align: center">参数类型</th>
              <th style="text-align: center">是否可选</th>
              <th style="text-align: center">默认值</th>
              <th style="text-align: center">参数说明</th>
            </tr>
          </thead>

          <tbody>
            <tr>
              <td style="text-align: center">id</td>
              <td style="text-align: center">int</td>
              <td style="text-align: center">必填</td>
              <td style="text-align: center">null</td>
              <td style="text-align: center">用户id</td>
            </tr>
          </tbody>
        </table>
      </blockquote>
    </li>
    <li>
      <p>返回示例</p>

      <blockquote>
        <pre><code class="language-javascript">{
    &quot;error&quot;: 0,
    &quot;msg&quot;: &quot;获取成功&quot;,
    &quot;result&quot;: {
        &quot;total&quot;: 1,
        &quot;per_page&quot;: 15,
        &quot;current_page&quot;: 1,
        &quot;last_page&quot;: 1,
        &quot;next_page_url&quot;: null,
        &quot;prev_page_url&quot;: null,
        &quot;from&quot;: 1,
        &quot;to&quot;: 1,
        &quot;data&quot;: [
            {
                &quot;id&quot;: 25,
                &quot;user_id&quot;: &quot;1&quot;,
                &quot;message_id&quot;: &quot;4496&quot;,
                &quot;created_at&quot;: &quot;2017-07-25 12:02:56&quot;,
                &quot;user&quot;: {
                    &quot;id&quot;: 1,
                    &quot;name&quot;: &quot;Arron&quot;,
                    &quot;email&quot;: &quot;yangyun4814@qq.com&quot;,
                    &quot;token&quot;: &quot;&quot;,
                    &quot;created_at&quot;: &quot;2017-04-30 10:12:25&quot;,
                    &quot;updated_at&quot;: &quot;2017-04-30 12:12:41&quot;
                },
                &quot;message&quot;: {
                    &quot;id&quot;: 4496,
                    &quot;title&quot;: &quot;标题&quot;,
                    &quot;type&quot;: &quot;1&quot;,
                    &quot;url&quot;: &quot;&quot;,
                    &quot;color&quot;: &quot;&quot;,
                    &quot;published_at&quot;: &quot;2017-07-24&quot;,
                    &quot;created_at&quot;: &quot;2017-07-24 22:00:35&quot;,
                    &quot;updated_at&quot;: &quot;2017-07-24 22:00:37&quot;,
                    &quot;type_text&quot;: &quot;人事招考&quot;
                }
            }
        ]
    }
}</code></pre>
      </blockquote>
    </li>
  </ul>

<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?ec6a88e3807e8225993475da825c27a7";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>

</body>

</html>