<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
<head>
    <meta charset="utf-8">
    <meta name="full-screen" content="true">
    <meta name="screen-orientation" content="true">
    <meta name="x5-fullscreen" content="true">
    <meta name="360-fullscreen" content="true">
    <meta name="viewport" content="width=device-width,maximum-scale=1.0, initial-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta name="renderer" content="webkit">

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="{{ asset('img/favicon.png') }}">
    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="{{ config('app.name', 'Gzzp') }}"/>
    <link rel="apple-touch-icon-precomposed" href="{{ asset('img/favicon.png') }}">
    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="{{ asset('img/favicon.png') }}">
    <meta name="msapplication-TileColor" content="#0e90d2">

    <!-- CSRF Token -->
    <meta name="csrf-token" content="{{ csrf_token() }}">

    <title>{{ $message ? "【". $message->type_text ."】". $message->title : config('app.name', 'Gzzp') }}</title>

    <!-- Styles -->
    <link href="{{ asset('css/normalize.css') }}" rel="stylesheet">
    <style>
        body{
            font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
            line-height: 1.5;
            color: #495060;
            background-color: #fff;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
        }
        .layout{
            position: relative;
            height: inherit;
            min-height: max-content;
            max-width: 960px;
            margin: 0 auto;
        }
        .layout-content{
            padding: 10px 4px 10px 8px;
            position: relative;
            height: min-content;
            font-weight: normal;
        }
    </style>
    
    <!-- Scripts -->
    <script>
        window.Laravel = {!! json_encode([
            'csrfToken' => csrf_token(),
        ]) !!};
    </script>
</head>
<body>
    <div class="layout">
        <div class="layout-content">
            {!! $message->content !!}
        </div>
    </div>
</body>
</html>
