<!doctype html>
<html lang="{{ config('app.locale') }}">
    <head>
        @include('common/head')

        <title>{{ config('app.name', 'Gzzp') }}</title>

        <!-- Fonts -->
        <link href="{{ asset('css/normalize.css') }}" rel="stylesheet">
        <link href="{{ asset('css/raleway.css') }}" rel="stylesheet" type="text/css">

        <!-- Styles -->
        <style>
            html, body {
                background-color: #fff;
                color: #636b6f;
                font-family: 'Raleway', sans-serif;
                font-weight: 100;
                font-size: 16px;
                height: 100vh;
                margin: 0;
            }
            .full-height {
                height: 100vh;
            }
            .flex-center::before{
                content: "";
            }
            .flex-center {
                align-items: center;
                display: flex;
                justify-content: space-between;
                flex-direction: column;
            }
            .footer{
                color: #636b6f;
                padding: 1rem;
            }
            .footer a{
                color: #636b6f;
                font-weight: 100;
                letter-spacing: .1rem;
                text-decoration: none;
            }
            .position-ref {
                position: relative;
            }
            .top-right {
                position: absolute;
                right: 10px;
                top: 18px;
            }
            .content {
                text-align: center;
            }
            .title {
                font-size: 84px;
            }
            .links > a {
                display: inline-block;
                color: #636b6f;
                padding: 10px 25px;
                font-size: 16px;
                font-weight: 300;
                letter-spacing: .1rem;
                text-decoration: none;
                text-transform: uppercase;
            }
            .m-b-md {
                margin-bottom: 30px;
            }
            @media screen and (max-width: 560px){
                .content .links > a{
                    display: block;
                }
            }
        </style>
    </head>
    <body>
        <div class="flex-center position-ref full-height">
            @if (Route::has('login'))
                <div class="top-right links">
                    @if (Auth::check())
                        <a href="{{ url('/home') }}">Home</a>
                    @else
                        <a href="{{ url('/login') }}">Login</a>
                        <a href="{{ url('/register') }}">Register</a>
                    @endif
                </div>
            @endif

            <div class="content">
                <div class="title m-b-md">
                    {{ config('app.name', 'Laravel') }}
                </div>

                <div class="links">
                    <a href="{{ url('/dashboard') }}">Dashboard</a>
                    <a href="{{ url('/docs') }}">Documentation</a>
                    <a href="https://github.com/ArronYR">GitHub</a>
                </div>
            </div>

            <div class="footer">
                &copy;{{ date('Y') }} - 
                <a href="http:://helloarron.com" target="_blank">Arron</a>
            </div>
        </div>
    </body>
</html>
