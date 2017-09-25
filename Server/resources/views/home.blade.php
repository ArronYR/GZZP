@extends('layouts.app')

@section('content')

<div class="collections-wrapper">
    <collections uid={{ Auth::user()->id }}></collections>
</div>

@endsection
