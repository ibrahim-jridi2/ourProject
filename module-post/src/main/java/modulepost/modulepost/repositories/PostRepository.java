package modulepost.modulepost.repositories;


import modulepost.modulepost.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
