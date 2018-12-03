package com.xpj.redis;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用于封装redis返回类型
 */
@Component
public class RedisTable {

    /**
     * 保存redis的主键ID
     */
    private Long redisId;

    /**
     * redis的类型如：set/list/hash/sortedset/string
     */
    private String redisType;

    /**
     * 保存redis时使用的key
     */
    private String redisKey;

    /**
     * 此属性主要用于hash数据结构时，保存member的
     */
    private String objectName;

    /**
     * 存储的redis的值
     */
    private String redisValue;

    /**
     * 保存Token时，为区分拼接的字符串
     */
    private String keyToken;

    /**
     * 此属性为sortedset数据结构时，保存的score值
     */
    private String score;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * redis的IP地址  当然此处也可以存储mac地址
     */
    private String macIp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否修改。此属性可以用于增量备份时，即在每个redis存储时可以更具key多存储一个属性isModify。 如果有修改，则置为 Y,否则为N.
     */
    private String isModify;

    /**
     * value-String
     *
     * @return
     */
    private String value_string;

    /**
     * value-Set
     */
    private Set<?> value_set;

    /**
     * value-list
     */
    private List<?> value_list;

    /**
     *
     */
    private Object value_obj;

    private List<?> value_hash;

    public RedisTable() {
    }

    public Long getRedisId() {
        return redisId;
    }

    public void setRedisId(Long redisId) {
        this.redisId = redisId;
    }

    public String getRedisType() {
        return redisType;
    }

    public void setRedisType(String redisType) {
        this.redisType = redisType;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getRedisValue() {
        return redisValue;
    }

    public void setRedisValue(String redisValue) {
        this.redisValue = redisValue;
    }

    public String getKeyToken() {
        return keyToken;
    }

    public void setKeyToken(String keyToken) {
        this.keyToken = keyToken;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getMacIp() {
        return macIp;
    }

    public void setMacIp(String macIp) {
        this.macIp = macIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsModify() {
        return isModify;
    }

    public void setIsModify(String isModify) {
        this.isModify = isModify;
    }

    public String getValue_string() {
        return value_string;
    }

    public void setValue_string(String value_string) {
        this.value_string = value_string;
    }

    public List<?> getValue_list() {
        return value_list;
    }

    public void setValue_list(List<?> value_list) {
        this.value_list = value_list;
    }

    public Set<?> getValue_set() {
        return value_set;
    }

    public void setValue_set(Set<?> value_set) {
        this.value_set = value_set;
    }

    public List<?> getValue_hash() {
        if (value_hash != null && value_hash.size() > 0) {
            return value_hash;
        } else {
            return new ArrayList<>();
        }
    }

    public void setValue_hash(List<?> value_hash) {
        this.value_hash = value_hash;
    }

    public Object getValue_obj() {
        return value_obj;
    }

    public void setValue_obj(Object value_obj) {
        this.value_obj = value_obj;
    }
}
