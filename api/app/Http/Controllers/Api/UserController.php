<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use Illuminate\Support\Str;
use App\Http\Controllers\Controller;
use Hash;

use App\Message;
use App\User;
use App\Collection;

class UserController extends Controller
{
    /**
     * 注册
     *
     * @return void
     */
    public function register(Request $request)
    {
        $email = $request->input('email');
        $password = $request->input('password');
        $name = $request->input('nickname');

        if (!$email || !$password || !$name) {
            return response()->json(['error' => 1, 'msg' => '参数错误']);
        }
        $exist = User::where(['email' => $email])->first();
        if ($exist) {
            return response()->json(['error' => 1, 'msg' => '该邮箱已被注册']);
        }

        $user = User::create([
            'name' => $name,
            'email' => $email,
            'password' => bcrypt($password),
            'token' => bcrypt($email.Str::random(60)),
        ]);
        if ($user) {
            return response()->json(['error' => 0, 'msg' => '注册成功']);
        }
        return response()->json(['error' => 1, 'msg' => '注册失败']);
    }

    /**
     * 登陆
     *
     * @param Request $request
     * @return void
     */
    public function login(Request $request)
    {
        $email = $request->input('email');
        $password = $request->input('password');

        if (!$email || !$password) {
            return response()->json(['error' => 1, 'msg' => '参数错误']);
        }
        $user = User::where(['email' => $email])->first();
        if (!$user) {
            return response()->json(['error' => 1, 'msg' => '用户不存在']);
        }
        if (Hash::check($password, $user->password)) {
            return response()->json(['error' => 0, 'msg' => '登陆成功', 'result' => $user]);
        }
        return response()->json(['error' => 1, 'msg' => '密码错误']);
    }

    /**
     * 新增收藏
     *
     * @param Request $request
     * @return void
     */
    public function collection(Request $request)
    {
        $uid = $request->input('uid');
        $message_id = $request->input('mid');

        if (!$uid || !$message_id) {
            return response()->json(['error' => 1, 'msg' => '参数错误']);
        }
        $user = User::find($uid);
        if (!$user) {
            return response()->json(['error' => 1, 'msg' => '用户不存在']);
        }
        $message = Message::find($message_id);
        if (!$message) {
            return response()->json(['error' => 1, 'msg' => '消息不存在']);
        }
        $exist = Collection::where(['user_id' => $uid, 'message_id' => $message_id])->first();
        if ($exist) {
            return response()->json(['error' => 1, 'msg' => '已收藏过该信息']);
        }
        $collection = Collection::create([
            'user_id' => $uid,
            'message_id' => $message_id
        ]);
        if ($collection) {
            return response()->json(['error' => 0, 'msg' => '收藏成功']);
        }
        return response()->json(['error' => 1, 'msg' => '收藏失败']);
    }

    /**
     * 取消收藏
     *
     * @param Request $request
     * @return void
     */
    public function unfavorite(Request $request)
    {
        $id = $request->input('id');
        if (!$id) {
            return response()->json(['error' => 1, 'msg' => '参数错误']);
        }

        $collection = Collection::find($id);
        if (!$collection) {
            return response()->json(['error' => 1, 'msg' => '未收藏该信息']);
        }
        if ($collection->delete()) {
            return response()->json(['error' => 0, 'msg' => '取消成功']);
        }
        return response()->json(['error' => 1, 'msg' => '取消失败']);
    }

    /**
     * 用户收藏集合
     *
     * @param int $id
     * @return void
     */
    public function collections(Request $request, $id = 0)
    {
        $result['error'] = 0;
        $result['msg'] = "获取成功";

        $collections = Collection::where(['user_id' => $id])
            ->orderBy('created_at', 'desc')
            ->paginate(15);

        foreach ($collections as $collection) {
            $collection->user = $collection->user;
            $collection->message = $collection->message;
            if ($collection->message) {
                $collection->message->type_text = $collection->message->getTypeText($collection->message->type);
            }
        }

        $result['result'] = $collections;
        return response()->json($result);
    }
}
