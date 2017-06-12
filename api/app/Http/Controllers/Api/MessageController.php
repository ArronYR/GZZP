<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

use App\Message;
use App\User;
use App\Collection;

class MessageController extends Controller
{
    /**
     * 获取消息内容
     *
     * @param int $id
     * @return void
     */
    public function message($id = 0)
    {
        $message = Message::find($id);
        if($message){
            $result['error'] = 0;
            $result['msg'] = "获取成功";
            $data = new \stdClass();
            $data->id = $message->id;
            $data->title = $message->title;
            $data->color = $message->color;
            $data->url = $message->url;
            $data->content = $message->content;
            $data->published_at = $message->published_at;
            $data->type = $message->type;
            $data->type_text = $message->getTypeText($message->type);
            $data->created_at = $message->created_at;
            $result['result'] = $data;
        }else{
            $result['error'] = 1;
            $result['msg'] = "不存在该记录";
        }

        return response()->json($result);
    }

    /**
     * 消息集合
     *
     * @return void
     */
    public function messages(Request $request)
    {
        $keyword = $request->input('keyword');
        $type = null !== $request->input('type') ? $request->input('type') : 1 ;

        $messages = Message::orderBy('created_at', 'desc')
            ->where('type', $type)
            ->where('title', 'like', "%$keyword%")
            ->paginate(20);
       
        foreach ($messages as $m => $message) {
            $message->type_text = $message->getTypeText($message->type);
            if ($message->content == null) {
                $content = "<div style='margin-top:20px;text-align:center'>请点击右上角图标打开原链接查看</div>";
                $message->content = $content;
            }
        }
        
        $result['error'] = 0;
        $result['msg'] = "获取成功";
        $result['result'] = $messages;
        return response()->json($result);
    }
    
}
