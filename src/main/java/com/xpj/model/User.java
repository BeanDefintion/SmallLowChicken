package com.xpj.model;

import java.time.LocalDateTime;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usert.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usert.sokeNo
     *
     * @mbggenerated
     */
    private Integer sokeno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usert.intro
     *
     * @mbggenerated
     */
    private String intro;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usert.hobby
     *
     * @mbggenerated
     */
    private String hobby;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usert.label
     *
     * @mbggenerated
     */
    private String label;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usert.crt_time
     *
     * @mbggenerated
     */
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usert.upt_time
     *
     * @mbggenerated
     */
    private LocalDateTime updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usert
     *
     * @mbggenerated
     */
    public User(Integer id, Integer sokeno, String intro, String hobby, String label, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.sokeno = sokeno;
        this.intro = intro;
        this.hobby = hobby;
        this.label = label;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usert
     *
     * @mbggenerated
     */
    public User() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usert.id
     *
     * @return the value of usert.id
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usert.id
     *
     * @param id the value for usert.id
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usert.sokeNo
     *
     * @return the value of usert.sokeNo
     * @mbggenerated
     */
    public Integer getSokeno() {
        return sokeno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usert.sokeNo
     *
     * @param sokeno the value for usert.sokeNo
     * @mbggenerated
     */
    public void setSokeno(Integer sokeno) {
        this.sokeno = sokeno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usert.intro
     *
     * @return the value of usert.intro
     * @mbggenerated
     */
    public String getIntro() {
        return intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usert.intro
     *
     * @param intro the value for usert.intro
     * @mbggenerated
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usert.hobby
     *
     * @return the value of usert.hobby
     * @mbggenerated
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usert.hobby
     *
     * @param hobby the value for usert.hobby
     * @mbggenerated
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usert.label
     *
     * @return the value of usert.label
     * @mbggenerated
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usert.label
     *
     * @param label the value for usert.label
     * @mbggenerated
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usert.crt_time
     *
     * @return the value of usert.crt_time
     * @mbggenerated
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usert.crt_time
     *
     * @param createTime the value for usert.crt_time
     * @mbggenerated
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usert.upt_time
     *
     * @return the value of usert.upt_time
     * @mbggenerated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usert.upt_time
     *
     * @param updateTime the value for usert.upt_time
     * @mbggenerated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}