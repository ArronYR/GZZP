<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
<head>
    @include('common/head')

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
