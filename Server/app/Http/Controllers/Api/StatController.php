<?php

namespace App\Http\Controllers\Api;

use DB;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

use App\Message;

class StatController extends Controller
{
    private $types = [
        0  => '未知',
        1  => '人事招考',
        2  => '贵州招聘',
        3  => '贵阳招聘',
        4  => '毕节招聘',
        5  => '遵义招聘',
        6  => '铜仁招聘',
        7  => '黔东南招聘',
        8  => '黔西南招聘',
        9  => '黔南招聘',
        10 => '安顺招聘',
        11 => '六盘水招聘',
    ];

    /**
     * 根据某一类型获取总数
     *
     * @param Request $request
     * @return void
     */
    public function countByType(Request $request)
    {
        $counts = [];
        $type = $request->input('type', 1);
        $count = Message::where('type', $type)->count();

        $result['error'] = 0;
        $result['msg'] = "获取成功";
        $result['result'] = $count;
        return response()->json($result);
    }

    /**
     * 公司招聘类型总数统计
     * {name: '', value: ''} 形式
     *
     * @param Request $request
     * @return void
     */
    public function recruitCount(Request $request)
    {
        $counts = [];
        $types = array_keys($this->types);
        for ($i = 2; $i < count($types); $i++) {
            $count = Message::where('type', $i)->count();
            array_push($counts, ['type' => $i, 'name' => $this->types[$i], 'value' => $count]);
        }

        $result['error'] = 0;
        $result['msg'] = "获取成功";
        $result['result'] = $counts;
        return response()->json($result);
    }

    /**
     * 获取所有类型的总数统计
     * {keys:[], values: []} 形式
     * 
     * @param Request $request
     * @return void
     */
    public function allCount(Request $request)
    {
        $counts = [];
        $types = array_keys($this->types);
        for ($i = 1; $i < count($types); $i++) {
            $count = Message::where('type', $i)->count();
            array_push($counts, $count);
        }
        $values = array_values($this->types);
        array_shift($values);

        $result['error'] = 0;
        $result['msg'] = "获取成功";
        $result['result']['keys'] = $values;
        $result['result']['values'] = $counts;
        return response()->json($result);
    }

    /**
     * 根据日期获取每日统计
     *
     * @param Request $request
     * @return void
     */
    public function countByDate(Request $request)
    {
        $types = [];
        if (null !== $request->input('type')) {
            array_push($types, intval($request->input('type')));
        } else {
            $types = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
        }
        
        $start = $request->input('start', date("Y-m-d", time()));
        $end = $request->input('end', date("Y-m-d", time()));
        $keyword = $request->input('keyword', '');

        $messages = DB::table('messages')
            ->select(DB::raw('count(*) as msg_count, published_at'))
            ->where('title', 'like', "%$keyword%")
            ->whereIn('type', $types)
            ->whereBetween('published_at', array($start, $end))
            ->groupBy('published_at')
            ->get();

        $keys = [];
        $values = [];
        foreach ($messages as $k => $message) {
            array_push($keys, $message->published_at);
            array_push($values, $message->msg_count);
        }
        $result['error'] = 0;
        $result['msg'] = "获取成功";
        $result['result']['keys'] = $keys;
        $result['result']['values'] = $values;
        return response()->json($result);
    }
}