package uz.bek.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.appjparelationships.entityUniversity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {



}
