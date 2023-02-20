package uz.bek.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.appjparelationships.entityUniversity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);
}
