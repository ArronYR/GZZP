<?php

namespace App\Http\Controllers\Api;

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
     * 大类总数统计
     *
     * @param Request $request
     * @return void
     */
    public function countByType(Request $request)
    {
        $counts = [];
        $type = null !== $request->input('type') ? $request->input('type') : 1 ;
        $count = Message::where('type', $type)->count();

        $result['error'] = 0;
        $result['msg'] = "获取成功";
        $result['result'] = $count;
        return response()->json($result);
    }

    /**
     * 公司招聘类型总数统计
     *
     * @param Request $request
     * @return void
     */
    public function recruit(Request $request)
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
}