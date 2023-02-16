package uz.bek.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bek.appjparelationships.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
