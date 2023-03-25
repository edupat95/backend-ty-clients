package com.tyclients.tycapp.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Documento.
 */
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_tramite")
    private Long numeroTramite;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "numero_dni")
    private Long numeroDni;

    @Column(name = "ejemplar")
    private String ejemplar;

    @Column(name = "nacimiento")
    private LocalDate nacimiento;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "inicio_fin_cuil")
    private Integer inicioFinCuil;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Documento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroTramite() {
        return this.numeroTramite;
    }

    public Documento numeroTramite(Long numeroTramite) {
        this.setNumeroTramite(numeroTramite);
        return this;
    }

    public void setNumeroTramite(Long numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Documento apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return this.nombres;
    }

    public Documento nombres(String nombres) {
        this.setNombres(nombres);
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getSexo() {
        return this.sexo;
    }

    public Documento sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Long getNumeroDni() {
        return this.numeroDni;
    }

    public Documento numeroDni(Long numeroDni) {
        this.setNumeroDni(numeroDni);
        return this;
    }

    public void setNumeroDni(Long numeroDni) {
        this.numeroDni = numeroDni;
    }

    public String getEjemplar() {
        return this.ejemplar;
    }

    public Documento ejemplar(String ejemplar) {
        this.setEjemplar(ejemplar);
        return this;
    }

    public void setEjemplar(String ejemplar) {
        this.ejemplar = ejemplar;
    }

    public LocalDate getNacimiento() {
        return this.nacimiento;
    }

    public Documento nacimiento(LocalDate nacimiento) {
        this.setNacimiento(nacimiento);
        return this;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public LocalDate getFechaEmision() {
        return this.fechaEmision;
    }

    public Documento fechaEmision(LocalDate fechaEmision) {
        this.setFechaEmision(fechaEmision);
        return this;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Integer getInicioFinCuil() {
        return this.inicioFinCuil;
    }

    public Documento inicioFinCuil(Integer inicioFinCuil) {
        this.setInicioFinCuil(inicioFinCuil);
        return this;
    }

    public void setInicioFinCuil(Integer inicioFinCuil) {
        this.inicioFinCuil = inicioFinCuil;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Documento createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Documento updatedDate(Instant updatedDate) {
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

    public Documento user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documento)) {
            return false;
        }
        return id != null && id.equals(((Documento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documento{" +
            "id=" + getId() +
            ", numeroTramite=" + getNumeroTramite() +
            ", apellidos='" + getApellidos() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", numeroDni=" + getNumeroDni() +
            ", ejemplar='" + getEjemplar() + "'" +
            ", nacimiento='" + getNacimiento() + "'" +
            ", fechaEmision='" + getFechaEmision() + "'" +
            ", inicioFinCuil=" + getInicioFinCuil() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
