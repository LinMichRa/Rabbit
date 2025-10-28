package co.vinni.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public class OrdenCompra {
    private Long id;
    private List<CarritoItem> items;
    private double total;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaOrden;
    private String nombreTitular;
    private String numeroTarjeta;
    private String estado;

    // Constructor vacío
    public OrdenCompra() {
        this.fechaOrden = LocalDateTime.now();
        this.estado = "PROCESADA";
    }

    // Constructor con parámetros
    public OrdenCompra(List<CarritoItem> items, double total, String nombreTitular, String numeroTarjeta) {
        this();
        this.items = items;
        this.total = total;
        this.nombreTitular = nombreTitular;
        // Solo guardamos los últimos 4 dígitos por seguridad
        this.numeroTarjeta = numeroTarjeta != null && numeroTarjeta.length() > 4 ? 
            "****-****-****-" + numeroTarjeta.substring(numeroTarjeta.length() - 4) : "****-****-****-0000";
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CarritoItem> getItems() {
        return items;
    }

    public void setItems(List<CarritoItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDateTime fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidadItems() {
        return items != null ? items.stream().mapToInt(CarritoItem::getCantidad).sum() : 0;
    }

    @Override
    public String toString() {
        return "OrdenCompra{" +
                "id=" + id +
                ", total=" + total +
                ", fechaOrden=" + fechaOrden +
                ", nombreTitular='" + nombreTitular + '\'' +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                ", estado='" + estado + '\'' +
                ", cantidadItems=" + getCantidadItems() +
                '}';
    }
}