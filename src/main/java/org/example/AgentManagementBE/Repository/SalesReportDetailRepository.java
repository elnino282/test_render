package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.SalesReportDetail;
import org.example.AgentManagementBE.Model.SalesReportDetail.SalesReportDetailID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface SalesReportDetailRepository extends CrudRepository<SalesReportDetail, SalesReportDetailID> {
    @Query("SELECT salesReportDetail FROM SalesReportDetail salesReportDetail WHERE salesReportDetail.agentID.agentID = :agentID")
    List<SalesReportDetail> getSalesReportDetailByAgentID(@Param("agentID")int agentID);

    @Query("SELECT salesReportDetail FROM SalesReportDetail salesReportDetail WHERE salesReportDetail.salesReportID.salesReportID = :salesReportID")
    List<SalesReportDetail> getSalesReportDetailBySalesReportID(@Param("salesReportID") int salesReportID);

    @Query("SELECT salesReportDetail FROM SalesReportDetail salesReportDetail WHERE salesReportDetail.agentID.agentID = :agentID AND salesReportDetail.salesReportID.salesReportID = :salesReportID")
    SalesReportDetail getSalesReportDetailByAgentIDAndSalesReportID(@Param("agentID")int agentID, @Param("salesReportID")int salesReportID);
}
