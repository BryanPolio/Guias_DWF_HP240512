package sv.edu.udb.service; //Bryan Steven Hernandez Polio HP240512

//Contiene la logica de negocio para poder registrar,listar y buscar productos,
//y por ello se comunica con el repositorio.
import sv.edu.udb.model.Producto;
import sv.edu.udb.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }
    public void registrarProducto(Producto producto) {
        repository.save(producto);
    }
    public List<Producto> listarProductos() {
        return repository.findAll();
    }
    public Optional<Producto> buscarProducto(String nombre) {
        return repository.findByNombre(nombre);
    }
}
