package uz.bek.appjparelationships.entityUniversity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne   // MANY group TO ONE faculty
    private Faculty faculty;

//    @OneToMany  //ONE group TO MANY students
//    private List<Student> students;
}
