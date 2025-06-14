package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.Parameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface ParameterRepository extends CrudRepository<Parameter, String> {
    @Query("SELECT parameter FROM Parameter parameter")
    List<Parameter> getAllParameter();

    @Query("SELECT parameter FROM Parameter parameter WHERE parameter.parameterName = :parameterName")
    Parameter getParameterByName(@Param("parameterName") String parameterName);
}
