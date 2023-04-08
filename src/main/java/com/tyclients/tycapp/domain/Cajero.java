package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Entity
@Table(name = "cajero")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cajero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plata_de_cambio")
    private Long plataDeCambio;

    @Column(name = "tipo")
    private Integer tipo;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "cread_date")
    private Instant creadDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @JsonIgnoreProperties(value = { "user", "club", "adminClub" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Trabajador trabajador;

    @OneToMany(mappedBy = "cajero")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productoVentas", "cajero", "asociado", "formaPago", "entregador" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "cajeros", "productoCajas", "club" }, allowSetters = true)
    private Caja caja;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cajero id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlataDeCambio() {
        return this.plataDeCambio;
    }

    public Cajero plataDeCambio(Long plataDeCambio) {
        this.setPlataDeCambio(plataDeCambio);
        return this;
    }

    public void setPlataDeCambio(Long plataDeCambio) {
        this.plataDeCambio = plataDeCambio;
    }

    public Integer getTipo() {
        return this.tipo;
    }

    public Cajero tipo(Integer tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Cajero estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreadDate() {
        return this.creadDate;
    }

    public Cajero creadDate(Instant creadDate) {
        this.setCreadDate(creadDate);
        return this;
    }

    public void setCreadDate(Instant creadDate) {
        this.creadDate = creadDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Cajero updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Trabajador getTrabajador() {
        return this.trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Cajero trabajador(Trabajador trabajador) {
        this.setTrabajador(trabajador);
        return this;
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.setCajero(null));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.setCajero(this));
        }
        this.ventas = ventas;
    }

    public Cajero ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public Cajero addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.setCajero(this);
        return this;
    }

    public Cajero removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.setCajero(null);
        return this;
    }

    public Caja getCaja() {
        return this.caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Cajero caja(Caja caja) {
        this.setCaja(caja);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cajero)) {
            return false;
        }
        return id != null && id.equals(((Cajero) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cajero{" +
            "id=" + getId() +
            ", plataDeCambio=" + getPlataDeCambio() +
            ", tipo=" + getTipo() +
            ", estado='" + getEstado() + "'" +
            ", creadDate='" + getCreadDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
