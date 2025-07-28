package sv.edu.udb.repository; //Bryan Steven Hernandez Polio HP240512

//Este clase me ayuda a poder simular una base de datos usando una lista,
//asi permitiendome listar todos y buscar por nombre los productos.
import sv.edu.udb.model.Producto;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository {
    private final List<Producto> productos = new ArrayList<>();

    public Producto save(Producto producto) {
        productos.add(producto);
        return producto;
    }

    public List<Producto> findAll() {
        return productos;
    }

    public Optional<Producto> findByNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
}
