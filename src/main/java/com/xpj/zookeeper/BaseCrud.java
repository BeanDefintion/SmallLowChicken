package com.xpj.zookeeper;

import java.util.Map;

/**
 * 封装最基本的增删查改的操作
 */
public interface BaseCrud {

    /**
     * 配置平台根节点名称
     */

    static String zooRoot = "/zoo";

    /**
     * 初始化操作
     */
    void init();

    /**
     * 重新读取操作
     */
    void reload();

    /**
     * 增加操作
     *
     * @param key
     * @param value
     */
    void add(String key, String value);

    /**
     * 更新
     *
     * @param key
     * @param value
     */
    void update(String key, String value);

    /**
     * 删除操作
     *
     * @param key
     */
    void delete(String key);

    /**
     * 获取单个key
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 获取所有
     *
     * @return
     */
    Map<String, String> getAll();
}
