package song.teamo1.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo1.domain.team.entity.Application;
import song.teamo1.domain.team.entity.Application.Status;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationJpaRepository extends JpaRepository<Application, Long> {
    @Query("select a " +
            " from Application a " +
            "where a.team   = :team " +
            "  and a.user   = :user " +
            "  and a.status = :status")
    Optional<Application> findApplicationByTeamAndUserAndStatus(@Param("team") Team team,
                                                                @Param("user") User user,
                                                                @Param("status") Status status);

    @Query("select a " +
            " from Application a " +
            " join fetch a.user " +
            "where a.team   = :team " +
            "  and a.status = :status")
    List<Application> findApplicationsByTeam(@Param("team") Team team,
                                             @Param("status") Status status);

    @Query("select a " +
            " from Application a " +
            " join fetch a.team " +
            " join fetch a.user " +
            "where a.id = :id " +
            "  and a.status = :status")
    Optional<Application> findByIdAndStatus(@Param("id") Long id,
                                            @Param("status") Status status);

    @Query("select a " +
            " from Application a " +
            "where a.user = :user " +
            "  and a.team = :team ")
    Optional<Application> findApplicationByUserAndTeam(@Param("user") User user,
                                                       @Param("team") Team team);
}
