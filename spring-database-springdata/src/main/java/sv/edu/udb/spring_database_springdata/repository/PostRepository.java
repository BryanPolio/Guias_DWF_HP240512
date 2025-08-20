package sv.edu.udb.spring_database_springdata.repository; //BRYAN STEVEN HERNANDEZ POLIO HP240512

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.spring_database_springdata.repository.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}