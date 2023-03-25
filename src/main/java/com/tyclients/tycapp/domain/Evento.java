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
 * A Evento.
 */
@Entity
@Table(name = "evento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_evento")
    private Instant fechaEvento;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "evento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "asociado", "evento" }, allowSetters = true)
    private Set<Acceso> accesos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "eventos", "trabajadors" }, allowSetters = true)
    private AdminClub adminClub;

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

    public Evento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaEvento() {
        return this.fechaEvento;
    }

    public Evento fechaEvento(Instant fechaEvento) {
        this.setFechaEvento(fechaEvento);
        return this;
    }

    public void setFechaEvento(Instant fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Instant getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Evento fechaCreacion(Instant fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Evento estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Evento direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Evento createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Evento updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<Acceso> getAccesos() {
        return this.accesos;
    }

    public void setAccesos(Set<Acceso> accesos) {
        if (this.accesos != null) {
            this.accesos.forEach(i -> i.setEvento(null));
        }
        if (accesos != null) {
            accesos.forEach(i -> i.setEvento(this));
        }
        this.accesos = accesos;
    }

    public Evento accesos(Set<Acceso> accesos) {
        this.setAccesos(accesos);
        return this;
    }

    public Evento addAcceso(Acceso acceso) {
        this.accesos.add(acceso);
        acceso.setEvento(this);
        return this;
    }

    public Evento removeAcceso(Acceso acceso) {
        this.accesos.remove(acceso);
        acceso.setEvento(null);
        return this;
    }

    public AdminClub getAdminClub() {
        return this.adminClub;
    }

    public void setAdminClub(AdminClub adminClub) {
        this.adminClub = adminClub;
    }

    public Evento adminClub(AdminClub adminClub) {
        this.setAdminClub(adminClub);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Evento club(Club club) {
        this.setClub(club);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evento)) {
            return false;
        }
        return id != null && id.equals(((Evento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Evento{" +
            "id=" + getId() +
            ", fechaEvento='" + getFechaEvento() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
