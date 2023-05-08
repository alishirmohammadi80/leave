package ir.rashasoft.leavemanagement.repository;

import ir.rashasoft.leavemanagement.model.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {

   @Query("SELECT p FROM LeaveRequest p WHERE p.requestStatus=1  AND CONCAT(p.dateCreated, ' ', p.user.personnelCode) LIKE %?1%")
     List<LeaveRequest> search(String keyword);

    @Query("SELECT a FROM LeaveRequest a WHERE a.requestStatus= 0  ORDER BY a.dateCreated DESC ")
    Page<LeaveRequest> inProgress(Pageable pageable);

    @Query("SELECT a FROM LeaveRequest a WHERE a.requestStatus= 1  ORDER BY a.dateCreated DESC ")
    Page<LeaveRequest> accept(Pageable pageable);

    @Query("SELECT a FROM LeaveRequest a WHERE a.requestStatus= 2  ORDER BY a.dateCreated DESC ")
    Page<LeaveRequest> reject(Pageable pageable);

    @Query("SELECT a FROM LeaveRequest  a ORDER BY a.dateCreated DESC")
    List<LeaveRequest> leaveRequest();

}
