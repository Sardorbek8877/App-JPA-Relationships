package uz.bek.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bek.appjparelationships.entity.Faculty;
import uz.bek.appjparelationships.entity.University;
import uz.bek.appjparelationships.payload.FacultyDto;
import uz.bek.appjparelationships.repository.FacultyRepository;
import uz.bek.appjparelationships.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    //ADD FACULTY
    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto){

        boolean exist = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exist)
            return "This university such exist";

        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if (optionalUniversity.isPresent()){
            University university = optionalUniversity.get();
            Faculty faculty = new Faculty();
            faculty.setName(facultyDto.getName());
            faculty.setUniversity(university);
            facultyRepository.save(faculty);
            return "Faculty added";
        }
        return "University not found";
    }

    //GET FACULTY
    @GetMapping
    public List<Faculty> getFaculty(){
        List<Faculty> facultyList = facultyRepository.findAll();
        return facultyList;
    }

    //GET FACULTY BY ID
    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable Integer id){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()){
            Faculty faculty=optionalFaculty.get();
            return faculty;
        }
        return new Faculty();
    }

    //DELETE FACULTY
    @DeleteMapping("{id}")
    public String deleteFaculty(@PathVariable Integer id){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()){
            facultyRepository.deleteById(id);
            return "Faculty deleted";
        }
        return "Faculty not found";
    }

    //EDIT FACULTY
    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()){
            Faculty faculty = optionalFaculty.get();
            faculty.setName(facultyDto.getName());

            Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
            if (optionalUniversity.isPresent()){
                University university = optionalUniversity.get();
                faculty.setUniversity(university);
                facultyRepository.save(faculty);
                return "Faculty edited";
            }
            return "University not found";
        }
        return "Faculty not found";
    }

    //GET FACULTIES FOR UNIVERSITY EMPLOYEE
    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId){
        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
        return allByUniversityId;
    }
}






