package sv.edu.udb.controller;  //Bryan Steven Hernandez Polio HP240512

import org.junit.jupiter.api.BeforeEach;  //Mi clase que me ayuda a hacer pruebas unitarias para el controlador productos
import org.junit.jupiter.api.Test;        //Y uso MockMvc para simular las peticiones de HTTP y asi ver el funcionamiento de los endpoints.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String productoJson = """
        {
            "nombre": "Arroz",
            "cantidad": 2,
            "precio": 3.50
        }
        """;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegistrarProducto() throws Exception {
        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testListarProductos() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testBuscarProductoExistente() throws Exception {
        mockMvc.perform(get("/productos/Arroz"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }


    @Test
    public void testBuscarProductoNoExistente() throws Exception {
        mockMvc.perform(get("/productos/NoExiste"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Producto no encontrado."));
    }
}
