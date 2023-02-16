package uz.bek.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bek.appjparelationships.entity.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {

}
