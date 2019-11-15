package example.struts1.form;

import org.springframework.stereotype.Component;

@Component
public class LoginForm {

    private static final long serialVersionUID = -732087133601148347L;
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
