package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.ImportDetail;
import org.example.AgentManagementBE.Model.ImportDetail.ImportDetailID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

@EnableJpaRepositories
public interface ImportDetailRepository extends CrudRepository<ImportDetail, ImportDetailID> {
    @Query("SELECT importDetail FROM ImportDetail importDetail WHERE importDetail.importReceiptID.importReceiptID = :importReceiptID")
    List<ImportDetail> getImportDetailByImportReceiptID(@Param("importReceiptID") int importReceiptID);

    @Query("SELECT importDetail FROM ImportDetail importDetail WHERE importDetail.productID.productID = :productID")
    List<ImportDetail> getImportDetailByProductID(@Param("productID") int productID);

    @Query("SELECT importDetail FROM ImportDetail importDetail WHERE importDetail.importReceiptID.importReceiptID = :importReceiptID AND importDetail.productID.productID = :productID")
    ImportDetail getImportDetailByImportReceiptIDAndProductID(@Param("importReceiptID") int importReceiptID, @Param("productID") int productID);
}
