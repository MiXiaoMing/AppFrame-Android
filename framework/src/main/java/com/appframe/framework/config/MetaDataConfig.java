package com.appframe.framework.config;

/**
 * 全局配置类
 */
public class MetaDataConfig {

    public MetaDataConfig() {

    }

    /**
     * 初始化缓存日志目录
     * @param dir
     * @return
     */
    public MetaDataConfig initCacheDirectory(String dir) {
        com.appframe.utils.Constants.cache_dir = dir;
        return this;
    }

    /**
     * 初始化日志默认标签
     * @param logTag 默认：AppFrame
     * @return
     */
    public MetaDataConfig initLogTag(String logTag) {
        com.appframe.utils.Constants.log_tag = logTag;
        return this;
    }

    /**
     * 初始化日志打印级别
     * @param logLevel 默认：android.util.Log.DEBUG
     * @return
     */
    public MetaDataConfig initLogLevel(int logLevel) {
        com.appframe.utils.Constants.log_level = logLevel;
        return this;
    }

    /**
     * 初始化SP名称
     * @param storeName 默认：AppFrame
     * @return
     */
    public MetaDataConfig initSPStoreName(String storeName) {
        com.appframe.utils.Constants.sp_store_name = storeName;
        return this;
    }

    /**
     * 默认底图ID
     * @param resId
     * @return
     */
    public MetaDataConfig initDefaultImage(int resId) {
        com.appframe.library.Constants.default_image = resId;
        return this;
    }
}
