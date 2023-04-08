package com.tyclients.tycapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanContratado.
 */
@Entity
@Table(name = "plan_contratado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlanContratado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tiempo_contratado")
    private Integer tiempoContratado;

    @Column(name = "fecha_vencimiento")
    private Instant fechaVencimiento;

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
    private Plan plan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanContratado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTiempoContratado() {
        return this.tiempoContratado;
    }

    public PlanContratado tiempoContratado(Integer tiempoContratado) {
        this.setTiempoContratado(tiempoContratado);
        return this;
    }

    public void setTiempoContratado(Integer tiempoContratado) {
        this.tiempoContratado = tiempoContratado;
    }

    public Instant getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public PlanContratado fechaVencimiento(Instant fechaVencimiento) {
        this.setFechaVencimiento(fechaVencimiento);
        return this;
    }

    public void setFechaVencimiento(Instant fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public PlanContratado estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public PlanContratado createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public PlanContratado updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Plan getPlan() {
        return this.plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public PlanContratado plan(Plan plan) {
        this.setPlan(plan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanContratado)) {
            return false;
        }
        return id != null && id.equals(((PlanContratado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanContratado{" +
            "id=" + getId() +
            ", tiempoContratado=" + getTiempoContratado() +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
