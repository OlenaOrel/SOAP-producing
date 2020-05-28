package ua.training.soap.producing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.soap.producing.entity.CellPhone;

public interface CellPhoneRepository extends JpaRepository<CellPhone, Long> {
}
