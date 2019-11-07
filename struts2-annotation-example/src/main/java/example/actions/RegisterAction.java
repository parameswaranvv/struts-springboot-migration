package example.actions;

import com.opensymphony.xwork2.ActionSupport;
import example.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Acts as a controller to handle actions
 * related to registering a user.
 *
 * @author bruce phillips
 */
@Service
@Namespaces({
        @Namespace("/")
})
public class RegisterAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LogManager.getLogger(RegisterAction.class);

    @Autowired
    private Person personBean;

    @Action(value = "register-input", results = {@Result(name = "input", location = "/WEB-INF/content/register-input.jsp")})
    public String input() throws Exception {
        log.info("In input method of class RegisterAction");
        return INPUT;
    }

    @Action(value = "register", results = {@Result(name = "success", location = "/WEB-INF/content/register-success.jsp")})
    public String execute() throws Exception {
        log.info("In execute method of class RegisterAction");
        return SUCCESS;
    }

    public Person getPersonBean() {
        return personBean;
    }

    public void setPersonBean(Person person) {
        personBean = person;
    }
}
