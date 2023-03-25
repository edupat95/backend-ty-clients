package com.tyclients.tycapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.tyclients.tycapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.tyclients.tycapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.tyclients.tycapp.domain.User.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Authority.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.tyclients.tycapp.domain.AdminClub.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.AdminClub.class.getName() + ".eventos");
            createCache(cm, com.tyclients.tycapp.domain.AdminClub.class.getName() + ".trabajadors");
            createCache(cm, com.tyclients.tycapp.domain.Cliente.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Registrador.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Registrador.class.getName() + ".asociadoClubs");
            createCache(cm, com.tyclients.tycapp.domain.Trabajador.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Cajero.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Cajero.class.getName() + ".ventas");
            createCache(cm, com.tyclients.tycapp.domain.Caja.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Caja.class.getName() + ".cajeros");
            createCache(cm, com.tyclients.tycapp.domain.Caja.class.getName() + ".productoCajas");
            createCache(cm, com.tyclients.tycapp.domain.Producto.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Producto.class.getName() + ".productoVentas");
            createCache(cm, com.tyclients.tycapp.domain.Producto.class.getName() + ".productoCajas");
            createCache(cm, com.tyclients.tycapp.domain.Producto.class.getName() + ".productoDepositos");
            createCache(cm, com.tyclients.tycapp.domain.Venta.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Venta.class.getName() + ".productoVentas");
            createCache(cm, com.tyclients.tycapp.domain.ProductoVenta.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.ProductoCaja.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.ProductoDeposito.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName() + ".formaPagos");
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName() + ".cajas");
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName() + ".depositos");
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName() + ".trabajadors");
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName() + ".asociadoClubs");
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName() + ".eventos");
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName() + ".productos");
            createCache(cm, com.tyclients.tycapp.domain.Asociado.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Asociado.class.getName() + ".ventas");
            createCache(cm, com.tyclients.tycapp.domain.Asociado.class.getName() + ".asociadoClubs");
            createCache(cm, com.tyclients.tycapp.domain.AsociadoClub.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Evento.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Evento.class.getName() + ".accesos");
            createCache(cm, com.tyclients.tycapp.domain.Deposito.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Deposito.class.getName() + ".productoDepositos");
            createCache(cm, com.tyclients.tycapp.domain.Documento.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.Acceso.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.FormaPago.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.TipoProducto.class.getName());
            createCache(cm, com.tyclients.tycapp.domain.TipoProducto.class.getName() + ".productos");
            createCache(cm, com.tyclients.tycapp.domain.Club.class.getName() + ".tipoProductos");
            createCache(cm, com.tyclients.tycapp.domain.FormaPago.class.getName() + ".ventas");
            createCache(cm, com.tyclients.tycapp.domain.Venta.class.getName() + ".formaPagos");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
