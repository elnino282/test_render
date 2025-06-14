package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.PaymentReceipt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface PaymentReceiptRepository extends CrudRepository<PaymentReceipt, Integer> {
    @Query("SELECT paymentReceipt FROM PaymentReceipt paymentReceipt WHERE paymentReceipt.paymentReceiptID = :paymentReceiptID")
    Iterable<PaymentReceipt> getPaymentReceiptByID(@Param("paymentReceiptID") int paymentReceiptID);

    @Query("SELECT paymentReceipt FROM PaymentReceipt paymentReceipt WHERE paymentReceipt.agentID.agentID = :agentID")
    Iterable<PaymentReceipt> getPaymentReceiptByAgentID(@Param("agentID") int agentID);

    @Query("SELECT paymentReceipt FROM PaymentReceipt paymentReceipt WHERE paymentReceipt.agentID.agentID = :agentID")
    List<PaymentReceipt> findByAgentID(@Param("agentID") int agentID);
}