package com.cyf.spike.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "sk_user")
public class SkUser implements Serializable {
    /**
     * 用户id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 昵称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * MD5(MD5(pass明文+固定salt)+salt
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 混淆盐
     */
    @Column(name = "salt")
    private String salt;

    /**
     * 混淆盐
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 头像，云存储的ID
     */
    @Column(name = "head")
    private String head;

    /**
     * 注册时间
     */
    @Column(name = "register_date")
    private Date registerDate;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_date")
    private Date lastLoginDate;

    /**
     * 登录次数
     */
    @Column(name = "login_count")
    private Integer loginCount;

    private static final long serialVersionUID = 1L;
}