package sv.edu.udb.controller; //Bryan Steven Hernandez Polio HP240512

//Mi controlador REST que expone los endpoints para registrar, listar y buscar con POST y GET
import sv.edu.udb.model.Producto;
import sv.edu.udb.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public String registrarProducto(@RequestBody Producto producto) {
        service.registrarProducto(producto);
        return "Producto registrado correctamente.";
    }

    @GetMapping
    public List<Producto> listar() {
        return service.listarProductos();
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        Optional<Producto> producto = service.buscarProducto(nombre);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto no encontrado.");
        }
    }
}
