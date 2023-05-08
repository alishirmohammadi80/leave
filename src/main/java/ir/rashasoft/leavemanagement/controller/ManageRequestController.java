package ir.rashasoft.leavemanagement.controller;
import ir.rashasoft.leavemanagement.emailService.EmailService;
import ir.rashasoft.leavemanagement.login.UserRepository;
import ir.rashasoft.leavemanagement.model.LeaveRequest;
import ir.rashasoft.leavemanagement.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/manager/leaveRequest")
public class ManageRequestController {

    @Autowired
    LeaveRequestService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @GetMapping("/panel")
    public String managerPanel() {
        return "manager-panel";
    }

    @GetMapping("/inProgress")
    public String inProgress(Model model) {
        return inProgress(model, 1);
    }

    @RequestMapping("/inProgress/page/{pageNum}")
    public String inProgress(Model model, @PathVariable(name = "pageNum") int pageNum) {
        Page<LeaveRequest> page = service.inProgress(pageNum);
        List<LeaveRequest> leaveRequestList = page.getContent();
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("leaveRequestList", leaveRequestList);
        return "manage-requests";
    }


    @GetMapping("/accept")
    public String accept(Model model) {
        return accept(model, 1);
    }

    @RequestMapping("/accept/page/{pageNum}")
    public String accept(Model model, @PathVariable(name = "pageNum") int pageNum) {
        Page<LeaveRequest> page = service.accept(pageNum);
        List<LeaveRequest> leaveRequestList = page.getContent();
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("leaveRequestList", leaveRequestList);
        return "accept-request";
    }

    @GetMapping("/reject")
    public String reject(Model model) {
        return reject(model, 1);
    }

    @RequestMapping("/reject/page/{pageNum}")
    public String reject(Model model, @PathVariable(name = "pageNum") int pageNum) {
        Page<LeaveRequest> page = service.reject(pageNum);
        List<LeaveRequest> leaveRequestList = page.getContent();
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("leaveRequestList", leaveRequestList);
        return "reject-request";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        LeaveRequest leaveRequest = service.findById(id);
        model.addAttribute("leaveRequest", leaveRequest);
        return "manage-requests";
    }


    @GetMapping("/accept/{id}")
    public String accept(@PathVariable("id") Long id) {
        service.accept(id);
        return "redirect:/manager/leaveRequest/inProgress";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable("id") Long id) {
        service.reject(id);
        return "redirect:/manager/leaveRequest/inProgress";
    }
}
