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
 * A Deposito.
 */
@Entity
@Table(name = "deposito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deposito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "deposito")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deposito", "producto" }, allowSetters = true)
    private Set<ProductoDeposito> productoDepositos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
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

    public Deposito id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Deposito name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Deposito estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Deposito createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Deposito updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<ProductoDeposito> getProductoDepositos() {
        return this.productoDepositos;
    }

    public void setProductoDepositos(Set<ProductoDeposito> productoDepositos) {
        if (this.productoDepositos != null) {
            this.productoDepositos.forEach(i -> i.setDeposito(null));
        }
        if (productoDepositos != null) {
            productoDepositos.forEach(i -> i.setDeposito(this));
        }
        this.productoDepositos = productoDepositos;
    }

    public Deposito productoDepositos(Set<ProductoDeposito> productoDepositos) {
        this.setProductoDepositos(productoDepositos);
        return this;
    }

    public Deposito addProductoDeposito(ProductoDeposito productoDeposito) {
        this.productoDepositos.add(productoDeposito);
        productoDeposito.setDeposito(this);
        return this;
    }

    public Deposito removeProductoDeposito(ProductoDeposito productoDeposito) {
        this.productoDepositos.remove(productoDeposito);
        productoDeposito.setDeposito(null);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Deposito club(Club club) {
        this.setClub(club);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deposito)) {
            return false;
        }
        return id != null && id.equals(((Deposito) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deposito{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
