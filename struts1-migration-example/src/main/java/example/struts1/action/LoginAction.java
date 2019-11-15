package example.struts1.action;

import com.opensymphony.xwork2.ActionSupport;

import example.struts1.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class LoginAction extends ActionSupport {

	private static Logger logger = LoggerFactory.getLogger(LoginAction.class);

	@Autowired
	private LoginForm loginForm;

	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	@Value("${helloworld.message}")
	private String helloWorldMessage;

	public String execute() {

		logger.info("Autowired message for Hello world: " + helloWorldMessage);
		logger.info("Login Form:" + loginForm);
		String path = ERROR;
		String userName = loginForm.getUserName();
		String password = loginForm.getPassword();
		if((userName != null && "admin".equals(userName)) &&   // 
				(password != null && "admin".equals(password))){
			path = SUCCESS;
			RequestContextHolder.currentRequestAttributes().setAttribute("userName", userName, 0);
		}
		logger.info("Action completed: " + path);
		return path;
	}
	
}
