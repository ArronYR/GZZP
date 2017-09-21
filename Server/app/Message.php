<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Message extends Model
{
    /**
     * 与模型关联的数据表
     *
     * @var string
     */
    protected $table = 'messages';
    
    /**
     * 该模型是否被自动维护时间戳
     *
     * @var bool
     */
    public $timestamps = false;

    protected $hidden = [
        'content',
    ];

    /**
     * 获取类型文字
     *
     * @param Object $value
     * @return void
     */
    public function getTypeText($value)
    {
        $types = [
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

        if (in_array($value, array_keys($types))) {
            return $types[$value];
        }else{
            return $types[0];
        }
    }
}
