package com.helloarron.dhroid;

/**
 * 一些系统常量的定义
 *
 * @author duohuo-jinghao
 */
public class Const {

    public static boolean net_error_try = false;

    public static boolean auto_inject = true;

    public static int DATABASE_VERSION = 1;

    // adapter 的分页相关
    public static String netadapter_page_no = "page";

    public static String netadapter_step = "step";

    public static Integer netadapter_step_default = 20;

    public static String netadapter_timeline = "timeline";

    public static String netadapter_json_timeline = "id";

    public static String netadapter_no_more = "已经没有了";

    // 图片缓存相关
    public static String image_cache_dir = "dhcache";

    public static int image_cache_num = 12;

    public static Boolean image_cache_is_in_sd = false;

    public static long image_cache_time = 100000l;

    // 网络访问返回数据的格式定义
    public static String response_success = "success";

    public static String response_result_status = "";

    public static String response_msg = "msg";

    public static String response_data = "data";

    public static String response_result = "result";

    public static String response_errorCode = "error";
    public static String response_errorMsg = "msg";

    public static String response_total = "total";

    public static String response_current = "current";

    public static String response_code = "code";

    public static int net_pool_size = 10;

    /**
     * 1代表post请求时用key value提交参数,2代表用json的形式提交参数
     */
    public static int postType = 1;

}
