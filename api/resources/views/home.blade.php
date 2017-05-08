@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">个人信息</div>

                <div class="panel-body">
                    <div style="margin:1rem 0;">
                        邮箱：<?php echo Auth::user()->email;?>
                    </div>
                    <div style="margin:1rem 0;">
                        个人凭证：<?php echo Auth::user()->token;?>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">个人收藏</div>

                <div class="panel-body">
                    <ul>
                        @foreach ($collections as $collection)
                            @if ($collection->message)
                            <li id="item_{{ $collection->id }}">
                                <a href="{{ $collection->message->url}}" class="text-info" target="_blank">
                                    {{ $collection->message->published_at }}
                                    {{ $collection->message->title }}
                                </a>
                            </li>
                            @endif
                        @endforeach
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
