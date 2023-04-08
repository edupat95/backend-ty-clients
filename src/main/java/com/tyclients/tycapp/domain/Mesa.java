package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mesa.
 */
@Entity
@Table(name = "mesa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mesa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_mesa")
    private Integer numeroMesa;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "mesa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mesa", "producto" }, allowSetters = true)
    private Set<ProductoMesa> productoMesas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "adminClub",
            "planContratado",
            "tipoProductos",
            "formaPagos",
            "cajas",
            "depositos",
            "trabajadors",
            "asociadoClubs",
            "eventos",
            "productos",
            "mesas",
        },
        allowSetters = true
    )
    private Club club;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mesa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroMesa() {
        return this.numeroMesa;
    }

    public Mesa numeroMesa(Integer numeroMesa) {
        this.setNumeroMesa(numeroMesa);
        return this;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Mesa estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Mesa createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Mesa updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<ProductoMesa> getProductoMesas() {
        return this.productoMesas;
    }

    public void setProductoMesas(Set<ProductoMesa> productoMesas) {
        if (this.productoMesas != null) {
            this.productoMesas.forEach(i -> i.setMesa(null));
        }
        if (productoMesas != null) {
            productoMesas.forEach(i -> i.setMesa(this));
        }
        this.productoMesas = productoMesas;
    }

    public Mesa productoMesas(Set<ProductoMesa> productoMesas) {
        this.setProductoMesas(productoMesas);
        return this;
    }

    public Mesa addProductoMesa(ProductoMesa productoMesa) {
        this.productoMesas.add(productoMesa);
        productoMesa.setMesa(this);
        return this;
    }

    public Mesa removeProductoMesa(ProductoMesa productoMesa) {
        this.productoMesas.remove(productoMesa);
        productoMesa.setMesa(null);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Mesa club(Club club) {
        this.setClub(club);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mesa)) {
            return false;
        }
        return id != null && id.equals(((Mesa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mesa{" +
            "id=" + getId() +
            ", numeroMesa=" + getNumeroMesa() +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
