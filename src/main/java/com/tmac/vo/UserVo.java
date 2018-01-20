package com.tmac.vo;

import com.tmac.entity.Function;
import com.tmac.entity.Menu;
import com.tmac.entity.Role;
import com.tmac.enums.SexEnum;

import java.util.Date;
import java.util.List;

public class UserVo {
    private String id;
    private String username;
    private String password;
    private List<Function> functions;
    private List<MenuVo> menus;

    private String account;
    private String department;
    private String email;
    private Integer age;
    private SexEnum sex;
    private String img;
    private String phone;
    private Date createTime;

    public List<MenuVo> getMenus() {
        return menus;
    }

    public void setMenus(final List<MenuVo> menus) {
        this.menus = menus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(final List<Function> functions) {
        this.functions = functions;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(final String account) {
        this.account = account;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(final SexEnum sex) {
        this.sex = sex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(final String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }
}
