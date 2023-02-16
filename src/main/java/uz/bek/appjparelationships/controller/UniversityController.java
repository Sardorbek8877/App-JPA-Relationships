package uz.bek.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.bek.appjparelationships.entity.Address;
import uz.bek.appjparelationships.entity.University;
import uz.bek.appjparelationships.payload.UniversityDto;
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
    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto){

        // CREATE NEW ADDRESS
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());

        Address saveAddress = addressRepository.save(address);

        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(saveAddress);

        universityRepository.save(university);

        return "University added";
    }
}
