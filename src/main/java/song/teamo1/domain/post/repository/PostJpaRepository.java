package song.teamo1.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import song.teamo1.domain.post.entity.Post;
import song.teamo1.domain.post.entity.Teaming;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long> {

    @Query("select t from Teaming t ")
    Page<Teaming> findTeamingList(Pageable pageable);
}
