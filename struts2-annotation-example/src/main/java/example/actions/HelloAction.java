package example.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.*;
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
public class HelloAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LogManager.getLogger(HelloAction.class);

    private String message;

    @Action(value = "hello", results = {@Result(name = "success", location = "/WEB-INF/content/hello-success.jsp")})
    public String execute() throws Exception {
        log.info("In execute method of class Hello");
        message = "Hello from Struts 2 with no XML configuration.";
        return SUCCESS;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}



