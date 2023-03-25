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
 * A FormaPago.
 */
@Entity
@Table(name = "forma_pago")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FormaPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "formaPago")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productoVentas", "cajero", "asociado", "formaPago" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "adminClub", "tipoProductos", "formaPagos", "cajas", "depositos", "trabajadors", "asociadoClubs", "eventos", "productos",
        },
        allowSetters = true
    )
    private Club club;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FormaPago id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public FormaPago name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public FormaPago estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public FormaPago createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public FormaPago updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.setFormaPago(null));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.setFormaPago(this));
        }
        this.ventas = ventas;
    }

    public FormaPago ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public FormaPago addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.setFormaPago(this);
        return this;
    }

    public FormaPago removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.setFormaPago(null);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public FormaPago club(Club club) {
        this.setClub(club);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormaPago)) {
            return false;
        }
        return id != null && id.equals(((FormaPago) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormaPago{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
