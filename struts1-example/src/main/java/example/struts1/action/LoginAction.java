package example.struts1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import example.struts1.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class LoginAction extends ApplicationContextAwareAction {

	private static Logger logger = LoggerFactory.getLogger(LoginAction.class);


	@Value("${helloworld.message}")
	private String helloWorldMessage;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("Autowired message for Hello world: " + helloWorldMessage);
		String path = "error";
		LoginForm lf = (LoginForm) form;
		String userName = lf.getUserName();
		String password = lf.getPassword();
		if((userName != null && "admin".equals(userName)) &&   // 
				(password != null && "admin".equals(password))){
			path = "success";
			request.setAttribute("userName", userName);
		}
		return mapping.findForward(path);
	}
	
}
