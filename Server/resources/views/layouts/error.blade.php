<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
    <head>
        @include('common/head')
        
        <title>{{ config('app.name', 'Gzzp') }} - @yield('title', "Error").</title>

        <link href="{{ asset('css/normalize.css') }}" rel="stylesheet">
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
