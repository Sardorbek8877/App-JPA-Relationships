package uz.bek.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.bek.appjparelationships.entityUniversity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findAllByFaculty_University_Id(Integer faculty_university_id);

    @Query("select gr from groups gr where gr.faculty.university.id=:universityId")
    List<Group> getGroupsByUniversityId(Integer universityId);

//    @Query(value = "select * from groups join faculty f on groups.faculty_id = f.id on faculty=groups.faculty_id " +
//            "join university on university.id=faculty.university_id " +
//            "where university_id=:universityId", nativeQuery = true);
//    List<Group> getGroupsByUniversityIdNative(Integer universityId);
}
