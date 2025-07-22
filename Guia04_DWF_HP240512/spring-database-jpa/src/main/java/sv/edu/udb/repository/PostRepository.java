package sv.edu.udb.repository;             //BRYAN STEVEN HERNANDEZ POLIO HP240512
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.repository.domain.Post;
import java.util.List;
@Repository
public class PostRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public List<Post> findAll() {
        final String QUERY = "select p from Post p";
        return entityManager
                .createQuery(QUERY, Post.class)
                .getResultList();
    }
    public Post findById(final Long id) {
        return entityManager.find(Post.class, id);
    }
    @Transactional
    public void save(final Post post) {
        entityManager.persist(post);
    }
    public void delete(final Post post) {
        entityManager.remove(post);
    }
}