package restaurantreservations.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping
    public String defaultMapping() {
        return "Springboot demo";
    }
}
