package song.teamo1.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo1.domain.team.entity.Team;

import java.util.Optional;

@Repository
public interface TeamJpaRepository extends JpaRepository<Team, Long> {
    @Query("select t from Team t where t.name = :name")
    Optional<Team> findTeamByName(@Param("name") String name);
}