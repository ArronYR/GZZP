<?php

namespace App\Http\Controllers\Dashboard;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class HomeController extends Controller
{
    /**
     * 首页
     *
     * @param Request $request
     * @return void
     */
    public function index (Request $request)
    {
        return view('dashboard.index');
    }

    /**
     * 消息
     *
     * @param Request $request
     * @param integer $id
     * @return void
     */
    function message (Request $request, $id = 0)
    {
        return view('dashboard.message', compact('id'));
    }
}