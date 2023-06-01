package ir.rashasoft.leavemanagement.service;

import ir.rashasoft.leavemanagement.emailService.EmailService;
import ir.rashasoft.leavemanagement.model.LeaveRequest;
import ir.rashasoft.leavemanagement.model.RequestStatus;
import ir.rashasoft.leavemanagement.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class LeaveRequestService {

    @Autowired
    LeaveRequestRepository repository;

    @Autowired
    EmailService emailService;


    public List<LeaveRequest> getAll() {
        return repository.leaveRequest();
    }


    public Page<LeaveRequest> inProgress(int pageNum) {

        Pageable pageable = PageRequest.of(pageNum - 1, 10

        );

        return repository.inProgress(pageable);

    }


    public Page<LeaveRequest> accept(int pageNum) {

        Pageable pageable = PageRequest.of(pageNum - 1, 10

        );

        return repository.accept(pageable);
    }

    public Page<LeaveRequest> reject(int pageNum) {

        Pageable pageable = PageRequest.of(pageNum - 1, 10

        );

        return repository.reject(pageable);
    }


    public List<LeaveRequest> search(String keyword) {
        if (keyword != null) {
            return repository.search(keyword);
        }
        return repository.findAll();
    }


    public LeaveRequest findById(Long id) {
        return repository.getById(id);
    }


    public void save(LeaveRequest leaveRequest) {
        repository.save(leaveRequest);

    }

    public void update(LeaveRequest leaveRequest) {
        repository.save(leaveRequest);
    }


    public void delete(Long id) {

        repository.deleteById(id);
    }

    public void accept(Long id) {
        LeaveRequest leaveRequest = this.findById(id);
        leaveRequest.setRequestStatus(RequestStatus.accept);

        if (leaveRequest.getNumberOfHours() != null) {
            Long totalHours = leaveRequest.getUser().getTotalHours();
            Long numberOfHours = leaveRequest.getNumberOfHours();
            var sum = totalHours + numberOfHours;
            leaveRequest.getUser().setTotalHours(sum);
            this.save(leaveRequest);
        } else if (leaveRequest.getNumberOfDays() != null) {
            Long totalDays = leaveRequest.getUser().getTotalDays();
            Long numberOfDays = leaveRequest.getNumberOfDays();
            var sum = totalDays + numberOfDays;
            leaveRequest.getUser().setTotalDays(sum);
            this.save(leaveRequest);
        }

        this.save(leaveRequest);
        try {
            emailService.send(leaveRequest.getUser().getEmail(), "نتیجه درخواست", "همکار گرامی با مرخصی شما موافقت گردید :");
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void reject(Long id) {
        LeaveRequest leaveRequest = this.findById(id);
        leaveRequest.setRequestStatus(RequestStatus.reject);
        this.save(leaveRequest);
        try {
            emailService.send(leaveRequest.getUser().getEmail(), "نتیجه درخواست", "همکار گرامی با مرخصی شما موافقت نگردید :");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void dayOf(LeaveRequest leaveRequest) {
        leaveRequest.setRequestStatus(RequestStatus.inProgress);
        this.save(leaveRequest);
        try {
            emailService.send("zhina.dr@gmail.com", " درخواست مرخصی :" + leaveRequest.getUser().getFirstName() + " - " + leaveRequest.getUser().getLastName()

                    , " http://185.58.242.45:8084/manager/leaveRequest/panel" +"\n" + leaveRequest.getUser().getFirstName() +" : نام " +"\n" +
                             leaveRequest.getUser().getLastName() +" : نام خانوادگی "+ "\n" +
                              leaveRequest.getUser().getPersonnelCode() +": کد پرسنلی " + "\n" +
                             leaveRequest.getNumberOfDays() + "  : تعداد روز "+"\n" +
                              leaveRequest.getStartDate() +"  : از تاریخ "+ "\n" +
                              leaveRequest.getEndDate()+   " : لغایت " + "\n" +
                             leaveRequest.getDescription() + " : توضیحات "
                    );
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void hourly(LeaveRequest leaveRequest) {
        leaveRequest.setRequestStatus(RequestStatus.inProgress);
        this.save(leaveRequest);
        try {
            emailService.send("zhina.dr@gmail.com", " درخواست مرخصی :" + leaveRequest.getUser().getFirstName() + " - " + leaveRequest.getUser().getLastName()
                    , " http://185.58.242.45:8084/manager/leaveRequest/panel" +"\n" + leaveRequest.getUser().getFirstName() +" : نام " +"\n" +
                            leaveRequest.getUser().getLastName() +" : نام خانوادگی "+ "\n" +
                            leaveRequest.getUser().getPersonnelCode() +": کد پرسنلی " + "\n" +
                            leaveRequest.getNumberOfHours() + "  : تعداد ساعت "+"\n" +
                            leaveRequest.getHourlyLeaveDate() +" :  تاریخ "+ "\n" +
                            leaveRequest.getStartTime() +"  : از ساعت "+ "\n" +
                            leaveRequest.getEndTime()+   " : لغایت " + "\n" +
                            leaveRequest.getDescription() + " : توضیحات "
            );
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
