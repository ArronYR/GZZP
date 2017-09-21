<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Collection extends Model
{
    /**
     * 与模型关联的数据表
     *
     * @var string
     */
    protected $table = 'collections';

    protected $fillable = [
        'user_id', 'message_id'
    ];
    /**
     * 该模型是否被自动维护时间戳
     *
     * @var bool
     */
    public $timestamps = false;

    public function user()
    {
        return $this->belongsTo('App\User', 'user_id');
    }

    public function message()
    {
        return $this->belongsTo('App\Message', 'message_id');
    }
}
