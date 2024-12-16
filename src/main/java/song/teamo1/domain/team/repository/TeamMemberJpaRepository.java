package song.teamo1.domain.team.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface TeamMemberJpaRepository extends JpaRepository<TeamMember, Long> {
//    @EntityGraph(attributePaths = {"team", "user"})
    @Query("select tm " +
            " from TeamMember tm " +
            " join fetch tm.team and " +
//            " join fetch tm.user " +
            "where tm.team.id = :teamId " +
            "  and tm.user.id = :userId")
    Optional<TeamMember> findTeamMemberByTeamIdAndUserId(@Param("teamId") Long teamId,
                                                         @Param("userId") Long userId);

    @Query("select tm " +
            " from TeamMember tm " +
            "where tm.team = :team " +
            "  and tm.user = :user")
    Optional<TeamMember> findTeamMemberByTeamAndUser(@Param("team") Team team,
                                                     @Param("user") User user);

}
