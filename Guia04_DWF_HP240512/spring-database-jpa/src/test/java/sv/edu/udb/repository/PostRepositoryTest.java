package sv.edu.udb.repository;       //BRYAN STEVEN HERNANDEZ POLIO HP240512
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.repository.domain.Post;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Test
    void shouldHasOnePost_When_FindAll() {
        int expectedPostNumber = 1;
        final List<Post> actualPostList = postRepository.findAll();
        assertNotNull(actualPostList);
        assertEquals(expectedPostNumber, actualPostList.size());
    }
    @Test
    void shouldGetPost_When_IdExist() {
        Long expectedId = 1L;
        final String expectedTitle = "SpringBoot as a back-end";
        final LocalDate expectedDate = LocalDate.of(2023, 9, 29);
        final Post actualPost = postRepository.findById(expectedId);
        assertNotNull(actualPost);
        assertEquals(expectedId, actualPost.getId());
        assertEquals(expectedTitle, actualPost.getTitle());
        assertEquals(expectedDate, actualPost.getPostDate());
    }
    @Test
    @Transactional
    void shouldSavePost_When_PostIsNew() {
//Se utiliza @Transactional, porque estamos
//realizando dos operaciones de modificado
        //de datos al mismo tiempo entonces es necesario
        //mantener la transaccion consistente con las operaciones
        final Long expectedId = 2L;
        final String expectedTitle = "Anything you want to write";
        final LocalDate expectedDate = LocalDate.of(2024, 8, 24);
        final Post newPost = Post
                .builder()
                .id(expectedId)
                .title(expectedTitle)
                .postDate(expectedDate)
                .build();
        postRepository.save(newPost);

        final Post actualPost = postRepository.findById(expectedId);
        assertNotNull(actualPost);
        assertEquals(expectedId, actualPost.getId());
        assertEquals(expectedTitle, actualPost.getTitle());
        assertEquals(expectedDate, actualPost.getPostDate());

        //Despues de comprobar que el post con Id 2 existe
        //lo borramos para mantener consistencia de los datos y los test cases
        postRepository.delete(newPost); //deleting
    }

    @Test
    @Transactional
    void shouldDeletePost_When_PostExist() {
        final Long expectedId = 3L;
        final String expectedTitle = "Deleted";
        final LocalDate expectedDate = LocalDate.of(2024, 8, 24);

        final Post newPost = Post
                .builder()
                .id(expectedId)
                .title(expectedTitle)
                .postDate(expectedDate)
                .build();

        postRepository.save(newPost); //saving

        final Post actualPost = postRepository.findById(expectedId);
        assertNotNull(actualPost);

        postRepository.delete(newPost); //deleting

        final Post deletedPost = postRepository.findById(expectedId);
        assertNull(deletedPost);
    }
}