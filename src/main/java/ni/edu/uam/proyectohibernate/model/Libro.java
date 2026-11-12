package ni.edu.uam.proyectohibernate.model;


import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "libro", schema = "catalogos")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column (name = "autor", nullable = false, length = 120)
    private String autor;

    @Column(name = "anio_publicacion", nullable = false)
    private int anioPublicacion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Libro() {
    }

    public Libro(long id, String titulo, String autor, int anioPublicacion, BigDecimal precio, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.precio = precio;
        this.categoria = categoria;
    }
    // Constructor para libros sin categoría especificada
    public Libro(long id, String titulo, String autor,
                 int anioPublicacion, BigDecimal precio) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.precio = precio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
