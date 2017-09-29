<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
<head>
    @include('common/head')

    <title>{{ config('app.name', 'Gzzp') }}</title>

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
            @include('common.ceiling')
            <div class="layout-header">
                <a href="{{ url('/') }}" class="layout-logo">
                    <img src="{{ asset('img/icon_app.png') }}" alt="Icon">
                </a>
                <db-menu active-name="{{ Request::path() }}"></db-menu>
            </div>
            <div class="layout-content">
                @yield('content')
            </div>
            @include('common.footer')
        </div>
    </div>

    <!-- Scripts -->
    <script src="{{ asset('js/app.js') }}"></script>
</body>
</html>
