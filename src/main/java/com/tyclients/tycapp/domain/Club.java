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
 * A Club.
 */
@Entity
@Table(name = "club")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @JsonIgnoreProperties(value = { "user", "eventos", "trabajadors" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private AdminClub adminClub;

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productos", "club" }, allowSetters = true)
    private Set<TipoProducto> tipoProductos = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ventas", "club" }, allowSetters = true)
    private Set<FormaPago> formaPagos = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cajeros", "productoCajas", "club" }, allowSetters = true)
    private Set<Caja> cajas = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productoDepositos", "club" }, allowSetters = true)
    private Set<Deposito> depositos = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "club", "adminClub" }, allowSetters = true)
    private Set<Trabajador> trabajadors = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "asociado", "club", "registrador" }, allowSetters = true)
    private Set<AsociadoClub> asociadoClubs = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accesos", "adminClub", "club" }, allowSetters = true)
    private Set<Evento> eventos = new HashSet<>();

    @OneToMany(mappedBy = "club")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productoVentas", "productoCajas", "productoDepositos", "club", "tipoProducto" }, allowSetters = true)
    private Set<Producto> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Club id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Club nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Club direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Club estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Club createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Club updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public AdminClub getAdminClub() {
        return this.adminClub;
    }

    public void setAdminClub(AdminClub adminClub) {
        this.adminClub = adminClub;
    }

    public Club adminClub(AdminClub adminClub) {
        this.setAdminClub(adminClub);
        return this;
    }

    public Set<TipoProducto> getTipoProductos() {
        return this.tipoProductos;
    }

    public void setTipoProductos(Set<TipoProducto> tipoProductos) {
        if (this.tipoProductos != null) {
            this.tipoProductos.forEach(i -> i.setClub(null));
        }
        if (tipoProductos != null) {
            tipoProductos.forEach(i -> i.setClub(this));
        }
        this.tipoProductos = tipoProductos;
    }

    public Club tipoProductos(Set<TipoProducto> tipoProductos) {
        this.setTipoProductos(tipoProductos);
        return this;
    }

    public Club addTipoProducto(TipoProducto tipoProducto) {
        this.tipoProductos.add(tipoProducto);
        tipoProducto.setClub(this);
        return this;
    }

    public Club removeTipoProducto(TipoProducto tipoProducto) {
        this.tipoProductos.remove(tipoProducto);
        tipoProducto.setClub(null);
        return this;
    }

    public Set<FormaPago> getFormaPagos() {
        return this.formaPagos;
    }

    public void setFormaPagos(Set<FormaPago> formaPagos) {
        if (this.formaPagos != null) {
            this.formaPagos.forEach(i -> i.setClub(null));
        }
        if (formaPagos != null) {
            formaPagos.forEach(i -> i.setClub(this));
        }
        this.formaPagos = formaPagos;
    }

    public Club formaPagos(Set<FormaPago> formaPagos) {
        this.setFormaPagos(formaPagos);
        return this;
    }

    public Club addFormaPago(FormaPago formaPago) {
        this.formaPagos.add(formaPago);
        formaPago.setClub(this);
        return this;
    }

    public Club removeFormaPago(FormaPago formaPago) {
        this.formaPagos.remove(formaPago);
        formaPago.setClub(null);
        return this;
    }

    public Set<Caja> getCajas() {
        return this.cajas;
    }

    public void setCajas(Set<Caja> cajas) {
        if (this.cajas != null) {
            this.cajas.forEach(i -> i.setClub(null));
        }
        if (cajas != null) {
            cajas.forEach(i -> i.setClub(this));
        }
        this.cajas = cajas;
    }

    public Club cajas(Set<Caja> cajas) {
        this.setCajas(cajas);
        return this;
    }

    public Club addCaja(Caja caja) {
        this.cajas.add(caja);
        caja.setClub(this);
        return this;
    }

    public Club removeCaja(Caja caja) {
        this.cajas.remove(caja);
        caja.setClub(null);
        return this;
    }

    public Set<Deposito> getDepositos() {
        return this.depositos;
    }

    public void setDepositos(Set<Deposito> depositos) {
        if (this.depositos != null) {
            this.depositos.forEach(i -> i.setClub(null));
        }
        if (depositos != null) {
            depositos.forEach(i -> i.setClub(this));
        }
        this.depositos = depositos;
    }

    public Club depositos(Set<Deposito> depositos) {
        this.setDepositos(depositos);
        return this;
    }

    public Club addDeposito(Deposito deposito) {
        this.depositos.add(deposito);
        deposito.setClub(this);
        return this;
    }

    public Club removeDeposito(Deposito deposito) {
        this.depositos.remove(deposito);
        deposito.setClub(null);
        return this;
    }

    public Set<Trabajador> getTrabajadors() {
        return this.trabajadors;
    }

    public void setTrabajadors(Set<Trabajador> trabajadors) {
        if (this.trabajadors != null) {
            this.trabajadors.forEach(i -> i.setClub(null));
        }
        if (trabajadors != null) {
            trabajadors.forEach(i -> i.setClub(this));
        }
        this.trabajadors = trabajadors;
    }

    public Club trabajadors(Set<Trabajador> trabajadors) {
        this.setTrabajadors(trabajadors);
        return this;
    }

    public Club addTrabajador(Trabajador trabajador) {
        this.trabajadors.add(trabajador);
        trabajador.setClub(this);
        return this;
    }

    public Club removeTrabajador(Trabajador trabajador) {
        this.trabajadors.remove(trabajador);
        trabajador.setClub(null);
        return this;
    }

    public Set<AsociadoClub> getAsociadoClubs() {
        return this.asociadoClubs;
    }

    public void setAsociadoClubs(Set<AsociadoClub> asociadoClubs) {
        if (this.asociadoClubs != null) {
            this.asociadoClubs.forEach(i -> i.setClub(null));
        }
        if (asociadoClubs != null) {
            asociadoClubs.forEach(i -> i.setClub(this));
        }
        this.asociadoClubs = asociadoClubs;
    }

    public Club asociadoClubs(Set<AsociadoClub> asociadoClubs) {
        this.setAsociadoClubs(asociadoClubs);
        return this;
    }

    public Club addAsociadoClub(AsociadoClub asociadoClub) {
        this.asociadoClubs.add(asociadoClub);
        asociadoClub.setClub(this);
        return this;
    }

    public Club removeAsociadoClub(AsociadoClub asociadoClub) {
        this.asociadoClubs.remove(asociadoClub);
        asociadoClub.setClub(null);
        return this;
    }

    public Set<Evento> getEventos() {
        return this.eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        if (this.eventos != null) {
            this.eventos.forEach(i -> i.setClub(null));
        }
        if (eventos != null) {
            eventos.forEach(i -> i.setClub(this));
        }
        this.eventos = eventos;
    }

    public Club eventos(Set<Evento> eventos) {
        this.setEventos(eventos);
        return this;
    }

    public Club addEvento(Evento evento) {
        this.eventos.add(evento);
        evento.setClub(this);
        return this;
    }

    public Club removeEvento(Evento evento) {
        this.eventos.remove(evento);
        evento.setClub(null);
        return this;
    }

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        if (this.productos != null) {
            this.productos.forEach(i -> i.setClub(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setClub(this));
        }
        this.productos = productos;
    }

    public Club productos(Set<Producto> productos) {
        this.setProductos(productos);
        return this;
    }

    public Club addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setClub(this);
        return this;
    }

    public Club removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setClub(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Club)) {
            return false;
        }
        return id != null && id.equals(((Club) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Club{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
