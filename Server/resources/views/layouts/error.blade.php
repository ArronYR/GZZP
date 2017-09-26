<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
    <head>
        <meta charset="utf-8">
        <meta name="full-screen" content="true">
        <meta name="screen-orientation" content="true">
        <meta name="x5-fullscreen" content="true">
        <meta name="360-fullscreen" content="true">
        <meta name="viewport" content="width=device-width,maximum-scale=1.0, initial-scale=1.0, user-scalable=no">
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
        
        <title>{{ config('app.name', 'Gzzp') }} - @yield('title', "Error").</title>

        <link href="{{ asset('css/lato.css') }}" rel="stylesheet" type="text/css">

        <style>
            html, body {
                height: 100%;
            }
            body {
                margin: 0;
                padding: 0;
                width: 100%;
                color: #B0BEC5;
                display: table;
                font-weight: 100;
                font-family: 'Lato';
            }
            .container {
                text-align: center;
                display: table-cell;
                vertical-align: middle;
            }
            .content {
                text-align: center;
                display: inline-block;
            }
            .title {
                font-size: 3rem;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="content">
                @yield('content', "Not found.")
            </div>
        </div>
    </body>
</html>
