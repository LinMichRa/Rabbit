package co.vinni.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String username;
    private String password;  // --> Para login básico
    private String direccion;


    public Cliente() {}

    public Cliente(String nombre, String email, String password, String username, String direccion) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.username = username;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDireccion() {
    return direccion;
}

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
