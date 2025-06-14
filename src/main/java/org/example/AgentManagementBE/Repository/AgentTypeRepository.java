package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.AgentType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface AgentTypeRepository extends CrudRepository<AgentType, Integer> {
    @Query("SELECT a FROM AgentType a WHERE a.agentTypeID = :agentTypeID")
    AgentType existsById(@Param("agentTypeID") String agentTypeID);

    @Query("SELECT a FROM AgentType a WHERE a.agentTypeName = :agentTypeName")
    AgentType findByAgentTypeName(@Param("agentTypeName") String agentTypeName);

    @Query("SELECT a FROM AgentType a WHERE a.agentTypeID = :agentTypeID")
    AgentType findByAgentTypeID(int agentTypeID);
}
