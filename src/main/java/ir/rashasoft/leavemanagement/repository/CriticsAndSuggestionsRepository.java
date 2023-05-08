package ir.rashasoft.leavemanagement.repository;

import ir.rashasoft.leavemanagement.model.CriticsAndSuggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriticsAndSuggestionsRepository extends JpaRepository<CriticsAndSuggestions,Long> {

}
