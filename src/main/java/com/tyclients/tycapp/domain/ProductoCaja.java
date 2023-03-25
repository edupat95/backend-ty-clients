package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductoCaja.
 */
@Entity
@Table(name = "producto_caja")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductoCaja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productoVentas", "productoCajas", "productoDepositos", "club", "tipoProducto" }, allowSetters = true)
    private Producto producto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cajeros", "productoCajas", "club" }, allowSetters = true)
    private Caja caja;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductoCaja id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidad() {
        return this.cantidad;
    }

    public ProductoCaja cantidad(Long cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ProductoCaja createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public ProductoCaja updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ProductoCaja producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    public Caja getCaja() {
        return this.caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public ProductoCaja caja(Caja caja) {
        this.setCaja(caja);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoCaja)) {
            return false;
        }
        return id != null && id.equals(((ProductoCaja) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoCaja{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
