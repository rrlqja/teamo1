package song.teamo1.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.teamo1.domain.team.entity.Application;

@Repository
public interface ApplicationJpaRepository extends JpaRepository<Application, Long> {

}
