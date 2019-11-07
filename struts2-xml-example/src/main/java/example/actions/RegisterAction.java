package example.actions;

import com.opensymphony.xwork2.ActionSupport;
import example.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Acts as a controller to handle actions
 * related to registering a user.
 *
 * @author bruce phillips
 */
@Service
public class RegisterAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LogManager.getLogger(RegisterAction.class);

    @Autowired
    private Person personBean;

    public String input() throws Exception {
        log.info("In input method of class RegisterAction");
        return INPUT;
    }

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
