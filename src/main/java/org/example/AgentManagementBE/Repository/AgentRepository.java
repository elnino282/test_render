package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.AgentInfo;
import org.example.AgentManagementBE.Model.Agent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

@EnableJpaRepositories

public interface AgentRepository extends CrudRepository<Agent, Integer> {
    @Query("SELECT a FROM Agent a WHERE a.agentID = :agentID")
    Agent getAgentById(@Param("agentID") int agentID);

    @Query("SELECT a FROM Agent a WHERE a.agentName = :agentName")
    Agent getAllAgentByAgentName(@Param("agentName") String agentName);

    @Query("SELECT a FROM Agent a WHERE a.address = :address")
    Agent getAllAgentByAddress(@Param("address") String address);

    @Query("SELECT a FROM Agent a")
    List<Agent> getAllAgentIdAndName();

    @Query("SELECT DISTINCT a FROM Agent a LEFT JOIN FETCH a.agentTypeID LEFT JOIN FETCH a.districtID")
    List<Agent> getAllAgent();

    @Query("SELECT COUNT (*) FROM Agent a WHERE a.districtID.districtID = :districtID ")
    int countAgentByAgentType(@Param("districtID") int districtID);

    @Query("SELECT a FROM Agent a WHERE a.agentID = :agentID ")
    List<Agent> getAgentDebt(@Param("agentID") int agentID);
}
