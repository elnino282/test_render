package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.District;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface DistrictRepository extends CrudRepository<District, Integer> {

    @Query("SELECT district FROM District district WHERE district.districtID = :districtID")
    District existsById(@Param("districtID") String districtID);

    @Query("SELECT district FROM District district WHERE district.districtName = :districtName")
    District findByDistrictName(@Param("districtName") String districtName);
}
