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

<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?ec6a88e3807e8225993475da825c27a7";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
