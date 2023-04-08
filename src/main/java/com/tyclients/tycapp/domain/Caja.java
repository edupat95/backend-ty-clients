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
 * A Caja.
 */
@Entity
@Table(name = "caja")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Caja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "caja")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "trabajador", "ventas", "caja" }, allowSetters = true)
    private Set<Cajero> cajeros = new HashSet<>();

    @OneToMany(mappedBy = "caja")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "producto", "caja" }, allowSetters = true)
    private Set<ProductoCaja> productoCajas = new HashSet<>();

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

    public Caja id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Caja nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Caja estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Caja createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Caja updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<Cajero> getCajeros() {
        return this.cajeros;
    }

    public void setCajeros(Set<Cajero> cajeros) {
        if (this.cajeros != null) {
            this.cajeros.forEach(i -> i.setCaja(null));
        }
        if (cajeros != null) {
            cajeros.forEach(i -> i.setCaja(this));
        }
        this.cajeros = cajeros;
    }

    public Caja cajeros(Set<Cajero> cajeros) {
        this.setCajeros(cajeros);
        return this;
    }

    public Caja addCajero(Cajero cajero) {
        this.cajeros.add(cajero);
        cajero.setCaja(this);
        return this;
    }

    public Caja removeCajero(Cajero cajero) {
        this.cajeros.remove(cajero);
        cajero.setCaja(null);
        return this;
    }

    public Set<ProductoCaja> getProductoCajas() {
        return this.productoCajas;
    }

    public void setProductoCajas(Set<ProductoCaja> productoCajas) {
        if (this.productoCajas != null) {
            this.productoCajas.forEach(i -> i.setCaja(null));
        }
        if (productoCajas != null) {
            productoCajas.forEach(i -> i.setCaja(this));
        }
        this.productoCajas = productoCajas;
    }

    public Caja productoCajas(Set<ProductoCaja> productoCajas) {
        this.setProductoCajas(productoCajas);
        return this;
    }

    public Caja addProductoCaja(ProductoCaja productoCaja) {
        this.productoCajas.add(productoCaja);
        productoCaja.setCaja(this);
        return this;
    }

    public Caja removeProductoCaja(ProductoCaja productoCaja) {
        this.productoCajas.remove(productoCaja);
        productoCaja.setCaja(null);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Caja club(Club club) {
        this.setClub(club);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Caja)) {
            return false;
        }
        return id != null && id.equals(((Caja) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Caja{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
