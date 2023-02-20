package uz.bek.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.appjparelationships.entityUniversity.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    boolean existsByNameAndUniversityId(String name, Integer universityId);

    List<Faculty> findAllByUniversityId(Integer university_id);
}
