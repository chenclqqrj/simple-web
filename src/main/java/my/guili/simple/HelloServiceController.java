package my.guili.simple;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/json")
public class HelloServiceController {

    @RequestMapping(value = "/sayhello/{username}", method = RequestMethod.GET)
    public String sayHello(@PathVariable String username) {
        return "Hello " + username;
    }

}
