package ir.rashasoft.leavemanagement.controller;

import ir.rashasoft.leavemanagement.emailService.EmailService;
import ir.rashasoft.leavemanagement.login.UserRepository;
import ir.rashasoft.leavemanagement.model.LeaveRequest;
import ir.rashasoft.leavemanagement.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employee/leaveRequest")
public class LeaveRequestController {
    @Autowired
    LeaveRequestService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;
    @GetMapping("/enter")
    public String showEnterForm() {
        return "enter";
    }

    @RequestMapping(value = "/requests", method = {RequestMethod.POST})
    public String requests(@RequestParam Long personnelCode, Model model) {
        List<LeaveRequest> leaveRequestList = service.getAll()
                .stream()
                .filter(leaveRequest -> leaveRequest.getUser().getPersonnelCode().equals(personnelCode))
                .limit(12)
                .collect(Collectors.toList());
        model.addAttribute("leaveRequestList", leaveRequestList);
        model.addAttribute("personnelCode", personnelCode);
        return "leave-request";
    }

    @GetMapping("/search")
    public String search(@Param("keyword") String keyword, Model model) {
        List<LeaveRequest> leaveRequestList = service.search(keyword);
        model.addAttribute("leaveRequestList", leaveRequestList);
        model.addAttribute("keyword", keyword);
        return "manage-requests-search";
    }

    @GetMapping("/dayOf")
    public String showCreateForm(LeaveRequest leaveRequest, Model model) {
        model.addAttribute("user", userRepository.findAll());
        return "day-off";
    }

    @GetMapping("/hourly")
    public String showCreateForm1(LeaveRequest leaveRequest, Model model) {
        model.addAttribute("user", userRepository.findAll());
        return "hourly-leave";
    }

    @GetMapping("/panel")
    public String employeePanel() {
        return "employee-panel";
    }

    @PostMapping("/hourly")
    public String hourly(LeaveRequest leaveRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userRepository.findAll());
            return "hourly-leave";
        }
        service.hourly(leaveRequest);
        model.addAttribute("leaveRequest", service.getAll());
        return "redirect:/employee/leaveRequest/panel";
    }

    @PostMapping("/dayOf")
    public String dayOf(LeaveRequest leaveRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userRepository.findAll());
            return "day-off";
        }
        service.dayOf(leaveRequest);
        model.addAttribute("leaveRequest", service.getAll());
        return "redirect:/employee/leaveRequest/panel";
    }

}
