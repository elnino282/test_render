package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT person FROM Person person WHERE person.personEmail = :personEmail")
    Person getUserByEmail(@Param("personEmail") String personEmail);

    @Query(value = "SELECT * FROM Person WHERE SOUNDEX(personName) = SOUNDEX(:personName)", nativeQuery = true)
    Iterable<Person> findByNameSimilarity(@Param("personName") String personName);

    @Query("SELECT person FROM Person person WHERE person.personEmail = :personEmail")
    Person existsByEmail(@Param("personEmail") String personEmail);
}
