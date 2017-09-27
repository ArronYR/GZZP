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