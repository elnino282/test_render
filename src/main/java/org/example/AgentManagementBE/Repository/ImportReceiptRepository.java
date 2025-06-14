package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.ImportReceipt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.sql.Date;
import java.util.List;

@EnableJpaRepositories
public interface ImportReceiptRepository extends CrudRepository<ImportReceipt, Integer>
{
    @Query("SELECT importReceipt FROM ImportReceipt importReceipt WHERE importReceipt.importReceiptID = :importReceiptID")
    ImportReceipt getImportReceiptById(@Param("importReceiptID") int importReceiptID);

    @Query("SELECT importReceipt FROM ImportReceipt importReceipt WHERE importReceipt.dateReceipt = :dateReceipt")
    List<ImportReceipt> getAllImportReceiptByDateReceipt(@Param("dateReceipt") Date dateReceipt);
}