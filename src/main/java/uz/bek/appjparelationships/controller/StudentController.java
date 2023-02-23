package uz.bek.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.bek.appjparelationships.entityMap.Address;
import uz.bek.appjparelationships.entityUniversity.Group;
import uz.bek.appjparelationships.entityUniversity.Student;
import uz.bek.appjparelationships.entityUniversity.Subject;
import uz.bek.appjparelationships.payload.StudentDto;
import uz.bek.appjparelationships.repository.AddressRepository;
import uz.bek.appjparelationships.repository.GroupRepository;
import uz.bek.appjparelationships.repository.StudentRepository;
import uz.bek.appjparelationships.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;

    //GET STUDENTS
    @GetMapping
    public List<Student> getStudent(){
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    //ADD STUDENT
    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto){

        //CREATE ADDRESS
        Address address = new Address();
        address.setStreet(studentDto.getStreet());
        address.setCity(studentDto.getCity());
        address.setStreet(studentDto.getStreet());
        Address saveAddress = addressRepository.save(address);

        //FOUND GROUP FROM DATABASE WITH ID
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()){
            return "Group not found";
        }
        Group group = optionalGroup.get();

        //CREATE SUBJECT LIST
        List<Subject> subjectList = new ArrayList<>();
        List<Integer> subjectIdList = studentDto.getSubjectId();
        for (Integer item : subjectIdList) {
            Optional<Subject> optionalSubject = subjectRepository.findById(item);
            if (optionalSubject.isPresent()){
                Subject subject = optionalSubject.get();
                subjectList.add(subject);
            }
            else {
                return "Subject not found!";
            }
        }

        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setAddress(saveAddress);
        student.setGroup(group);
        student.setSubjects(subjectList);

        studentRepository.save(student);
        return "Student added";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            studentRepository.deleteById(id);
            return "Student deleted";
        }
        return "Student not found";
    }

    //EDIT STUDENT
    @PutMapping("/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDto studentDto){

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            Student editingStudent = optionalStudent.get();

            //CREATE ADDRESS
            Address address = new Address();
            address.setCity(studentDto.getCity());
            address.setStreet(studentDto.getStreet());
            address.setDistrict(studentDto.getDistrict());
            Address saveAddress = addressRepository.save(address);

            //Found group from DB
            Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
            if (!optionalGroup.isPresent()){
                return "Group not found";
            }
            Group group = optionalGroup.get();

            //ADD SUBJECTS TO LIST
            List<Subject> subjectList = new ArrayList<>();
            List<Integer> subjectIdList = studentDto.getSubjectId();
            for (Integer item : subjectIdList) {
                Optional<Subject> optionalSubject = subjectRepository.findById(item);
                if (optionalSubject.isPresent()){
                    Subject subject = optionalSubject.get();
                    subjectList.add(subject);
                }
                else {
                    return "Subject not found!";
                }
            }

            editingStudent.setFirstName(studentDto.getFirstName());
            editingStudent.setLastName(studentDto.getLastName());
            editingStudent.setAddress(saveAddress);
            editingStudent.setGroup(group);
            editingStudent.setSubjects(subjectList);
            return "Student edited";
        }

        return "Student not found";
    }


    // 1. MINISTRY
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    // 2. FOR UNIVERSITY
    @GetMapping("/forUniversitty/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId,pageable);
        return studentPage;
    }

    // 3. FOR FACULTY
    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getStudentForFaculty(@PathVariable Integer facultyId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_FacultyId(facultyId, pageable);
        return studentPage;
    }

    // 4. FOR GROUP
    @GetMapping("/forGroup/{groupId}")
    public Page<Student> getStudentForGroup(@PathVariable Integer groupId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> allByGroupId = studentRepository.findAllByGroupId(groupId, pageable);
        return allByGroupId;
    }
}








