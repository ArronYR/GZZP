<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

// 消息相关
Route::middleware('api')
    ->get('/message/{id}', ['uses'=>'Api\MessageController@message'])
    ->where(['id' => '[0-9]+']);

Route::middleware('api')
    ->get('/messages', ['uses'=>'Api\MessageController@messages']);

Route::middleware('api')
    ->get('/messages/count', ['uses'=>'Api\MessageController@count']);

// 用户相关
Route::middleware('api')
    ->post('/login', ['uses'=>'Api\UserController@login']);

Route::middleware('api')
    ->post('/register', ['uses'=>'Api\UserController@register']);

Route::middleware('api')
    ->post('/collection', ['uses'=>'Api\UserController@collection']);

Route::middleware('api')
    ->post('/unfavorite', ['uses'=>'Api\UserController@unfavorite']);

Route::middleware('api')
    ->get('/collections/{id}', ['uses'=>'Api\UserController@collections'])
    ->where(['id' => '[0-9]+']);

Route::middleware('api')
    ->get('/stat/countByType', ['uses'=>'Api\StatController@countByType']);

Route::middleware('api')
    ->get('/stat/recruit', ['uses'=>'Api\StatController@recruit']);