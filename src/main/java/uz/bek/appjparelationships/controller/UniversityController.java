package uz.bek.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bek.appjparelationships.entity.Address;
import uz.bek.appjparelationships.entity.University;
import uz.bek.appjparelationships.payload.UniversityDto;
import uz.bek.appjparelationships.repository.AddressRepository;
import uz.bek.appjparelationships.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

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

    //GET UNIVERSITY BY ID
    @RequestMapping(value = "/university/{id}", method = RequestMethod.GET)
    public University getUniversityById(@PathVariable Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()){
            University university = optionalUniversity.get();
            return university;
        }
        return new University();
    }

    //DELETE UNIVERSITY
    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id){
        universityRepository.deleteById(id);
        return "University deleted";
    }

    //EDIT UNIVERSITY
    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()){
            University editingUniversity = optionalUniversity.get();
            Address address = new Address();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());

            Address saveAddress = addressRepository.save(address);
            editingUniversity.setAddress(saveAddress);
            editingUniversity.setName(universityDto.getName());

            universityRepository.save(editingUniversity);
            return "University edited!";
        }
        return "University not found!";
    }
}
