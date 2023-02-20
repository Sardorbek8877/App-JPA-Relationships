package uz.bek.appjparelationships.payload;

import lombok.Data;
import uz.bek.appjparelationships.entityUniversity.Subject;

import java.util.List;

@Data
public class StudentDto {

    private String firstName;
    private String lastName;

    private String city;
    private String district;
    private String street;

    private Integer groupId;

    private List<Integer> subjectId;

}
