package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//import pl.coderslab.service.EmployeeService;

@RestController
public class HomeController {
//    public EmployeeService employeeService;
//
//    public HomeController(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }
//
//    @GetMapping("/")
//    public String home(Model model) {
//        Employee frodo = new Employee("Frodo", "Baggins", "ring bearer");
//        employeeService.saveEmployee(frodo);
//        model.addAttribute("frodo", frodo);
//        return String.format("Hello %s!", frodo.getFirstName());
//    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Admin!";
    }

    @GetMapping("/user")
    public String userEndpoint() {
        return "User!";
    }

    @GetMapping("/all")
    public String allRolesEndpoint() {
        return "All Roles!";
    }
}