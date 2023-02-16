package uz.bek.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.bek.appjparelationships.entity.University;
import uz.bek.appjparelationships.repository.AddressRepository;
import uz.bek.appjparelationships.repository.UniversityRepository;

import java.util.List;

@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;

    //READ
    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversity(){
        List<University> universityList = universityRepository.findAll();
        return universityList;
    }

    //CREATE

}
