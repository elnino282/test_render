package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.ExportDetail;
import org.example.AgentManagementBE.Model.ExportDetail.ExportDetailID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories

public interface ExportDetailRepository extends CrudRepository<ExportDetail, ExportDetailID> {
    @Query("SELECT exportDetail FROM ExportDetail exportDetail WHERE exportDetail.exportReceiptID.exportReceiptID = :exportReceiptID")
    List<ExportDetail> getExportDetailByExportReceiptID(@Param("exportReceiptID") int exportReceiptID);

    @Query("SELECT exportDetail FROM ExportDetail exportDetail WHERE exportDetail.productID.productID = :productID")
    List<ExportDetail> getExportDetailByProductID(@Param("productID") int productID);

    @Query("SELECT exportDetail FROM ExportDetail exportDetail WHERE exportDetail.exportReceiptID.exportReceiptID = :exportReceiptID AND exportDetail.productID.productID = :productID")
    ExportDetail getExportDetailByExportReceiptIDAndProductID(@Param("exportReceiptID") int exportReceiptID, @Param("productID") int productID);

    @Query("SELECT ed FROM ExportDetail ed JOIN ed.exportReceiptID er WHERE er.agentID.agentID = :agentID")
    List<ExportDetail> findByAgentID(@Param("agentID") int agentID);
}
