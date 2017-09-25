@extends('layouts.app')

@section('content')

<div class="register-wrapper">
    <Card style="width:350px" class="register-content">
        <h2 slot="title">重置密码</h2>
        <repw-form action="{{ route('password.request') }}" email="{{ $email or old('email') }}"></repw-form>
        @if ($errors->count())
            <Alert type="warning">{{ $errors->first() }}</Alert>
        @endif
    </Card>
</div>

@endsection
