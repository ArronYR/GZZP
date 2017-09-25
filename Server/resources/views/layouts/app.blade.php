<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSRF Token -->
    <meta name="csrf-token" content="{{ csrf_token() }}">

    <title>{{ config('app.name', 'Arron') }}</title>

    <!-- Styles -->
    <link href="{{ asset('css/app.css') }}" rel="stylesheet">
    <link href="{{ asset('css/main.css') }}" rel="stylesheet">

    <!-- Scripts -->
    <script>
        window.Laravel = {!! json_encode([
            'csrfToken' => csrf_token(),
        ]) !!};
    </script>
</head>
<body>
    <div id="app">
        <div class="layout">
            <div class="layout-ceiling">
                <div class="layout-ceiling-main">
                    @if (Auth::guest())
                        <a href="{{ url('/login') }}">登录</a> |
                        <a href="{{ url('/register') }}">注册</a> |
                    @else
                        <span> {{ Auth::user()->name }} </span> |
                        <a href="{{ route('logout') }}" onclick="event.preventDefault();document.getElementById('logout-form').submit();">退出</a> |
                        <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                            {{ csrf_field() }}
                        </form>
                    @endif
                        <a href="{{ url('/docs') }}">接口文档</a> |
                        <a href="https://github.com/ArronYR">GitHub</a>
                </div>
            </div>
            <div class="layout-header">
                <a href="{{ url('/') }}" class="layout-logo">
                    <img src="{{ asset('img/icon_app.png') }}" alt="Icon">
                </a>
                @if (!Auth::guest())
                    <user-menu active-name="{{ Route::currentRouteName() }}"></user-menu>
                @endif
            </div>
            <div class="layout-content">
                @yield('content')
            </div>
            <div class="layout-footer">
                2016-2017 &copy; Arron
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="{{ asset('js/app.js') }}"></script>
</body>
</html>
