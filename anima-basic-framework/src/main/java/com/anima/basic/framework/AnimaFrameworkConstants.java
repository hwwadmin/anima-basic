package com.anima.basic.framework;

/**
 * 常量
 *
 * @author hww
 * @date 2022/7/27
 */
public interface AnimaFrameworkConstants {

    // ------ 缓存键前缀 ------ //
    String global_prefix = "anima:"; // 系统统一前缀
    String sysProperties = "sysProperties:"; // 系统参数前缀
    String dict = "dict:"; // 字典前缀
    String rbac = "rbac:"; // rbac权限控制

    // 字典新增锁
    String cache_dict_add = global_prefix + dict + "add:l";
    // 字典新增锁
    String cache_dict_item_add = global_prefix + dict + "add:i:l";
    // 字典详情缓存
    String cache_dict_item = global_prefix + dict + "i:c";

    // 系统参数缓存
    String cache_sysProperties = global_prefix + sysProperties + "c";

    // 用户角色缓存
    String cahce_rbac_user_role = global_prefix + rbac + "ur";
    // 角色API缓存
    String cahce_rbac_role_api = global_prefix + rbac + "ra";

    // CrashLog定时任务锁
    String crashLog_lock = global_prefix + "cl:l";
    // CrashLog缓存
    String crashLog_cache = global_prefix + "cl:c";

}
