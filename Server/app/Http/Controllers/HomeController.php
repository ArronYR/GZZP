<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

use App\Message;
use App\Collection;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth')->except('detail');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $user = Auth::user();
        $collections = Collection::where(['user_id' => $user->id])
            ->orderBy('created_at')
            ->paginate(15);
        
        return view('home', compact('collections'));
    }

    public function detail(Request $request, $id = 0)
    {
        $message = Message::findOrFail($id);
        $message->type_text = $message->getTypeText($message->type);            
        if ($message->content == null) {
            $content = "<div style='margin-top:200px;text-align:center'>内容加载出错，请访问原链接。<br/>"
                        ."<a href='".$message->url."' target='_parent' >"
                        .$message->title."</a>"
                        ."</div>";
            $message->content = $content;
        }
        
        return view('detail', compact('message'));
    }
}
