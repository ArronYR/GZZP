@extends('layouts.app')

@section('content')

<div class="register-wrapper">
    <Card style="width:350px" class="register-content">
        <h2 slot="title">用户注册</h2>
        <register-form action="{{ route('register') }}" old-name="{{ old('name') }}" old-email="{{ old('email') }}"></register-form>
        @if ($errors->count())
            <Alert type="warning">{{ $errors->first() }}</Alert>
        @endif
    </Card>
</div>

@endsection
