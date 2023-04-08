package com.tyclients.tycapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Trabajador.
 */
@Entity
@Table(name = "trabajador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trabajador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sueldo")
    private Long sueldo;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private ZonedDateTime fechaIngreso;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "eventos", "trabajadors" }, allowSetters = true)
    private AdminClub adminClub;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trabajador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSueldo() {
        return this.sueldo;
    }

    public Trabajador sueldo(Long sueldo) {
        this.setSueldo(sueldo);
        return this;
    }

    public void setSueldo(Long sueldo) {
        this.sueldo = sueldo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Trabajador descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFechaIngreso() {
        return this.fechaIngreso;
    }

    public Trabajador fechaIngreso(ZonedDateTime fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(ZonedDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Trabajador estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Trabajador createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Trabajador updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trabajador user(User user) {
        this.setUser(user);
        return this;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Trabajador club(Club club) {
        this.setClub(club);
        return this;
    }

    public AdminClub getAdminClub() {
        return this.adminClub;
    }

    public void setAdminClub(AdminClub adminClub) {
        this.adminClub = adminClub;
    }

    public Trabajador adminClub(AdminClub adminClub) {
        this.setAdminClub(adminClub);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trabajador)) {
            return false;
        }
        return id != null && id.equals(((Trabajador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trabajador{" +
            "id=" + getId() +
            ", sueldo=" + getSueldo() +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
