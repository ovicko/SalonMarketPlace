/*
 * Copyright (c) 2019. AMWOLLO VICTOR <https://ovicko.com>
 */

package mes.cheveux.salon.data.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("created_at")
    @Expose
    private int created_at;

    @SerializedName("name")
    @Expose
    private  String username;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("auth_key")
    @Expose
    private String authKey;

    @SerializedName("email")
    @Expose
    private String email;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
