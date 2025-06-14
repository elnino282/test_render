package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.ExportReceipt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ExportReceiptRepository extends CrudRepository<ExportReceipt, Integer> {
    @Query("SELECT exportReceipt FROM ExportReceipt exportReceipt WHERE exportReceipt.exportReceiptID = :exportReceiptID")
    ExportReceipt getExportReceiptById(@Param("exportReceiptID") int exportReceiptID);

    @Query("SELECT exportReceipt FROM ExportReceipt exportReceipt WHERE CAST(exportReceipt.dateReceipt AS string) = :dateReceipt")
    ExportReceipt getAllExportReceiptByDateReceipt(@Param("dateReceipt") String dateReceipt);

    @Query(value = "SELECT * FROM EXPORT_RECEIPT exportReceipt WHERE MONTH(DATE_RECEIPT) = :monthtime AND YEAR(DATE_RECEIPT) = :yeartime", nativeQuery = true)
    List<ExportReceipt> getAllExportReceiptByMonthAndYearOfDateReceipt(@Param("monthtime") int month, @Param("yeartime") int year);

    @Query("SELECT COUNT(*) FROM ExportReceipt exportReceipt WHERE MONTH(exportReceipt.dateReceipt) = :monthtime AND YEAR(exportReceipt.dateReceipt) = :yeartime")
    int getQuantityExportByMonthAndYearOfDateReceipt(@Param("monthtime") int month, @Param("yeartime") int year);

    @Query("SELECT COUNT(*) FROM ExportReceipt exportReceipt WHERE exportReceipt.agentID.agentID = :agentID AND MONTH(exportReceipt.dateReceipt) = :monthtime AND YEAR(exportReceipt.dateReceipt) = :yeartime")
    int getQuantityExportByMonthAndYearOrderByAgentID(@Param("monthtime") int month, @Param("yeartime") int year, @Param("agentID") int agentID);

    @Query("SELECT SUM(exportReceipt.totalMoney) FROM ExportReceipt exportReceipt WHERE exportReceipt.agentID.agentID = :agentID AND MONTH(exportReceipt.dateReceipt) = :monthtime AND YEAR(exportReceipt.dateReceipt) = :yeartime")
    Integer getTotalMoneyByMonthAndYearOfAgent(@Param("agentID") int agentID, @Param("monthtime") int month, @Param("yeartime") int year);

    @Query("SELECT SUM(exportReceipt.totalMoney) FROM ExportReceipt exportReceipt WHERE MONTH(exportReceipt.dateReceipt) = :monthtime AND YEAR(exportReceipt.dateReceipt) = :yeartime")
    int getTotalMoneyByMonthAndYear(@Param("monthtime") int month, @Param("yeartime") int year);

    @Query("SELECT exportReceipt FROM ExportReceipt exportReceipt WHERE exportReceipt.agentID.agentID = :agentID")
    List<ExportReceipt> getExportReceiptByAgent(@Param("agentID") int agentID);

    @Query("SELECT exportReceipt FROM ExportReceipt exportReceipt WHERE exportReceipt.agentID.agentID = :agentID")
    List<ExportReceipt> findByAgentID(@Param("agentID") int agentID);

    @Query("SELECT COALESCE(SUM(exportReceipt.remainAmount), 0) FROM ExportReceipt exportReceipt WHERE exportReceipt.agentID.agentID = :agentID AND MONTH(exportReceipt.dateReceipt) = :monthtime AND YEAR(exportReceipt.dateReceipt) = :yeartime")
    Integer getTotalRemainAmountByMonthAndYear(@Param("agentID") int agentID, @Param("monthtime") int month, @Param("yeartime") int year);
}
