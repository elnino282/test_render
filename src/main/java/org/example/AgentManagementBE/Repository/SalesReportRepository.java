package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.SalesReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

@EnableJpaRepositories
public interface SalesReportRepository extends CrudRepository<SalesReport, Integer> {
    @Query("SELECT s FROM SalesReport s WHERE s.month = :month AND s.year = :year")
    SalesReport getSalesReportByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
