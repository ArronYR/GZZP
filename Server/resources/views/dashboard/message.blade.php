@extends('layouts.dashboard')

@section('content')
    
    <message-table :type-id="{{ $id }}"></message-table>

@endsection