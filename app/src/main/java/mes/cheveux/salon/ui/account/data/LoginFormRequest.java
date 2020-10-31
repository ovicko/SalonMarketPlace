
/*
 * Copyright (c) 2019. AMWOLLO VICTOR <https://ovicko.com>
 */

package mes.cheveux.salon.ui.account.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginFormRequest {
    private String email,password;

    public LoginFormRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public class LoginResponse {

        @SerializedName("message")
        @Expose
        private  String message;

        @SerializedName("code")
        @Expose
        private  int code;

        @SerializedName("user")
        @Expose
        private UserModel user;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public UserModel getUser() {
            return user;
        }

        public void setUser(UserModel user) {
            this.user = user;
        }
    }

}
