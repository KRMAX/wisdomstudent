package net.huawei.wisdomstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class StudentManagerController {
    @RequestMapping(value = "/{role}/{username}/studentManager", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String studentManager(@PathVariable String role) {
        return role+"/studentManager";
    }


}
