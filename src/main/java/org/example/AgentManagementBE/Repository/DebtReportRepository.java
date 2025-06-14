package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.DebtReport;
import org.example.AgentManagementBE.Model.DebtReportID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface DebtReportRepository extends CrudRepository<DebtReport, DebtReportID>
{
    @Query("SELECT debtReport FROM DebtReport debtReport WHERE debtReport.debtReportID.month = :monthtime AND debtReport.debtReportID.year = :yeartime")
    List<DebtReport> getDebtReport(@Param("monthtime") int month, @Param("yeartime") int year);

    @Query("SELECT debtReport FROM DebtReport debtReport WHERE debtReport.debtReportID.month = :monthtime AND debtReport.debtReportID.year = :yeartime AND debtReport.debtReportID.agentID.agentID = :agentID")
    DebtReport getDebtReportByAgent(@Param("monthtime") int month, @Param("yeartime") int year, @Param("agentID") int agentID);

    @Query("SELECT debtReport FROM DebtReport debtReport WHERE debtReport.debtReportID.agentID.agentID = :agentID")
    List<DebtReport> getDebtReportByAgent(@Param("agentID") int agentID);

    @Query("SELECT debtReport FROM DebtReport debtReport WHERE debtReport.debtReportID.agentID.agentID = :agentID")
    List<DebtReport> findByAgentID(@Param("agentID") int agentID);
}