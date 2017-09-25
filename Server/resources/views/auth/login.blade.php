@extends('layouts.app')

@section('content')

<div class="login-wrapper">
    <Card style="width:350px" class="login-content">
        <h2 slot="title">用户登陆</h2>
        <login-form action="{{ route('login') }}" old-email="{{ old('email') }}"></login-form>
        @if ($errors->count())
            <Alert type="warning">{{ $errors->first() }}</Alert>
        @endif
    </Card>
</div>

@endsection
