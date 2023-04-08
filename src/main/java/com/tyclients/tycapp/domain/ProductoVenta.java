package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductoVenta.
 */
@Entity
@Table(name = "producto_venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductoVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "costo_total")
    private Long costoTotal;

    @Column(name = "costo_total_puntos")
    private Long costoTotalPuntos;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productoVentas", "cajero", "asociado", "formaPago", "entregador" }, allowSetters = true)
    private Venta venta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "productoVentas", "productoCajas", "productoDepositos", "productoMesas", "club", "tipoProducto" },
        allowSetters = true
    )
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductoVenta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostoTotal() {
        return this.costoTotal;
    }

    public ProductoVenta costoTotal(Long costoTotal) {
        this.setCostoTotal(costoTotal);
        return this;
    }

    public void setCostoTotal(Long costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Long getCostoTotalPuntos() {
        return this.costoTotalPuntos;
    }

    public ProductoVenta costoTotalPuntos(Long costoTotalPuntos) {
        this.setCostoTotalPuntos(costoTotalPuntos);
        return this;
    }

    public void setCostoTotalPuntos(Long costoTotalPuntos) {
        this.costoTotalPuntos = costoTotalPuntos;
    }

    public Long getCantidad() {
        return this.cantidad;
    }

    public ProductoVenta cantidad(Long cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ProductoVenta createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public ProductoVenta updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public ProductoVenta venta(Venta venta) {
        this.setVenta(venta);
        return this;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ProductoVenta producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoVenta)) {
            return false;
        }
        return id != null && id.equals(((ProductoVenta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoVenta{" +
            "id=" + getId() +
            ", costoTotal=" + getCostoTotal() +
            ", costoTotalPuntos=" + getCostoTotalPuntos() +
            ", cantidad=" + getCantidad() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
