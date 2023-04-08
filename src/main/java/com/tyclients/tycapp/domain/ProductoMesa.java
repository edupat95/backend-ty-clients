package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductoMesa.
 */
@Entity
@Table(name = "producto_mesa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductoMesa implements Serializable {

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

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productoMesas", "club" }, allowSetters = true)
    private Mesa mesa;

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

    public ProductoMesa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostoTotal() {
        return this.costoTotal;
    }

    public ProductoMesa costoTotal(Long costoTotal) {
        this.setCostoTotal(costoTotal);
        return this;
    }

    public void setCostoTotal(Long costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Long getCostoTotalPuntos() {
        return this.costoTotalPuntos;
    }

    public ProductoMesa costoTotalPuntos(Long costoTotalPuntos) {
        this.setCostoTotalPuntos(costoTotalPuntos);
        return this;
    }

    public void setCostoTotalPuntos(Long costoTotalPuntos) {
        this.costoTotalPuntos = costoTotalPuntos;
    }

    public Long getCantidad() {
        return this.cantidad;
    }

    public ProductoMesa cantidad(Long cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public ProductoMesa estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ProductoMesa createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public ProductoMesa updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Mesa getMesa() {
        return this.mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public ProductoMesa mesa(Mesa mesa) {
        this.setMesa(mesa);
        return this;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ProductoMesa producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoMesa)) {
            return false;
        }
        return id != null && id.equals(((ProductoMesa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoMesa{" +
            "id=" + getId() +
            ", costoTotal=" + getCostoTotal() +
            ", costoTotalPuntos=" + getCostoTotalPuntos() +
            ", cantidad=" + getCantidad() +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
