package uz.bek.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bek.appjparelationships.entityUniversity.Faculty;
import uz.bek.appjparelationships.entityUniversity.Group;
import uz.bek.appjparelationships.payload.GroupDto;
import uz.bek.appjparelationships.repository.FacultyRepository;
import uz.bek.appjparelationships.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;

    //GET ALL GROUPS
    @GetMapping
    public List<Group> getGroups(){
        List<Group> groups = groupRepository.findAll();
        return groups;
    }

    //GET GROUPS BY ID, FOR UNIVERSITY EMPLOYEE
    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupByUniversityId(@PathVariable Integer universityId){
        List<Group> allByFacultyUniversityId = groupRepository.findAllByFaculty_University_Id(universityId);
        return allByFacultyUniversityId;
    }

    //ADD GROUP
    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto){

        Group group = new Group();
        group.setName(groupDto.getName());

        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (optionalFaculty.isPresent()){
            Faculty faculty = optionalFaculty.get();
            group.setFaculty(faculty);
            groupRepository.save(group);
            return "Group added";
        }
        return "Faculty not found";
    }

    //DELETE GROUP
    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Integer id){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()){
            groupRepository.deleteById(id);
            return "Group deleted";
        }
        return "Group not found";
    }

    //EDIT Group
    @PutMapping("/{id}")
    public String editGroup(@PathVariable Integer id, @RequestBody GroupDto groupDto){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()){
            Group group = optionalGroup.get();
            group.setName(groupDto.getName());

            Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
            if (optionalFaculty.isPresent()){
                Faculty faculty = optionalFaculty.get();
                group.setFaculty(faculty);
                groupRepository.save(group);
                return "Group edited";
            }
            return "Faculty not found";
        }
        return "Group not found";
    }
}




