package sv.edu.udb.model; //Bryan Steven Hernandez Polio HP240512

//Clase modelo que me sirve para representar los productos con sus valores: nombre, precio y cantidad
//ademas de sus getter y setters.
public class Producto {
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto() {
    }
    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
