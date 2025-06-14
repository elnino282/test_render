package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.Unit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface UnitRepository extends CrudRepository<Unit, Integer> {
    @Query("SELECT unit FROM Unit unit WHERE unit.unitName = :unitName")
    Unit findByUnitName(@Param("unitName") String unitName);
}
