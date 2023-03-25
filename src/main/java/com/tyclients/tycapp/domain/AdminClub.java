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
 * A AdminClub.
 */
@Entity
@Table(name = "admin_club")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminClub implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @OneToMany(mappedBy = "adminClub")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accesos", "adminClub", "club" }, allowSetters = true)
    private Set<Evento> eventos = new HashSet<>();

    @OneToMany(mappedBy = "adminClub")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "club", "adminClub" }, allowSetters = true)
    private Set<Trabajador> trabajadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdminClub id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public AdminClub estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public AdminClub createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public AdminClub updatedDate(Instant updatedDate) {
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

    public AdminClub user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Evento> getEventos() {
        return this.eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        if (this.eventos != null) {
            this.eventos.forEach(i -> i.setAdminClub(null));
        }
        if (eventos != null) {
            eventos.forEach(i -> i.setAdminClub(this));
        }
        this.eventos = eventos;
    }

    public AdminClub eventos(Set<Evento> eventos) {
        this.setEventos(eventos);
        return this;
    }

    public AdminClub addEvento(Evento evento) {
        this.eventos.add(evento);
        evento.setAdminClub(this);
        return this;
    }

    public AdminClub removeEvento(Evento evento) {
        this.eventos.remove(evento);
        evento.setAdminClub(null);
        return this;
    }

    public Set<Trabajador> getTrabajadors() {
        return this.trabajadors;
    }

    public void setTrabajadors(Set<Trabajador> trabajadors) {
        if (this.trabajadors != null) {
            this.trabajadors.forEach(i -> i.setAdminClub(null));
        }
        if (trabajadors != null) {
            trabajadors.forEach(i -> i.setAdminClub(this));
        }
        this.trabajadors = trabajadors;
    }

    public AdminClub trabajadors(Set<Trabajador> trabajadors) {
        this.setTrabajadors(trabajadors);
        return this;
    }

    public AdminClub addTrabajador(Trabajador trabajador) {
        this.trabajadors.add(trabajador);
        trabajador.setAdminClub(this);
        return this;
    }

    public AdminClub removeTrabajador(Trabajador trabajador) {
        this.trabajadors.remove(trabajador);
        trabajador.setAdminClub(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminClub)) {
            return false;
        }
        return id != null && id.equals(((AdminClub) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminClub{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
