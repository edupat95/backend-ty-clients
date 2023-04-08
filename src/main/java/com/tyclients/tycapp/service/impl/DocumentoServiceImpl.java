package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.domain.AsociadoClub;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.domain.Documento;
import com.tyclients.tycapp.repository.AsociadoClubRepository;
import com.tyclients.tycapp.repository.AsociadoRepository;
import com.tyclients.tycapp.repository.DocumentoRepository;
import com.tyclients.tycapp.repository.RegistradorRepository;
import com.tyclients.tycapp.service.DocumentoService;

import io.jsonwebtoken.io.IOException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Documento}.
 */
@Service
@Transactional
public class DocumentoServiceImpl implements DocumentoService {

    private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private final DocumentoRepository documentoRepository;
    
    private final RegistradorRepository registradorRepository;
    
    private final AsociadoRepository asociadoRepository;
    
    private final AsociadoClubRepository asociadoClubRepository;
    
    public DocumentoServiceImpl(DocumentoRepository documentoRepository, RegistradorRepository registradorRepository, AsociadoRepository asociadoRepository, AsociadoClubRepository asociadoClubRepository) {
        this.documentoRepository = documentoRepository;
        this.registradorRepository = registradorRepository;
        this.asociadoRepository = asociadoRepository;
        this.asociadoClubRepository = asociadoClubRepository;
    }
    @Override
    public Documento save(Documento documento) {
        log.debug("Request to save Documento : {}", documento);
        return documentoRepository.save(documento);
    }

    @Override
    public Documento update(Documento documento) {
        log.debug("Request to save Documento : {}", documento);
        return documentoRepository.save(documento);
    }

    @Override
    public Optional<Documento> partialUpdate(Documento documento) {
        log.debug("Request to partially update Documento : {}", documento);

        return documentoRepository
            .findById(documento.getId())
            .map(existingDocumento -> {
                if (documento.getNumeroTramite() != null) {
                    existingDocumento.setNumeroTramite(documento.getNumeroTramite());
                }
                if (documento.getApellidos() != null) {
                    existingDocumento.setApellidos(documento.getApellidos());
                }
                if (documento.getNombres() != null) {
                    existingDocumento.setNombres(documento.getNombres());
                }
                if (documento.getSexo() != null) {
                    existingDocumento.setSexo(documento.getSexo());
                }
                if (documento.getNumeroDni() != null) {
                    existingDocumento.setNumeroDni(documento.getNumeroDni());
                }
                if (documento.getEjemplar() != null) {
                    existingDocumento.setEjemplar(documento.getEjemplar());
                }
                if (documento.getNacimiento() != null) {
                    existingDocumento.setNacimiento(documento.getNacimiento());
                }
                if (documento.getFechaEmision() != null) {
                    existingDocumento.setFechaEmision(documento.getFechaEmision());
                }
                if (documento.getInicioFinCuil() != null) {
                    existingDocumento.setInicioFinCuil(documento.getInicioFinCuil());
                }
                if (documento.getCreatedDate() != null) {
                    existingDocumento.setCreatedDate(documento.getCreatedDate());
                }
                if (documento.getUpdatedDate() != null) {
                    existingDocumento.setUpdatedDate(documento.getUpdatedDate());
                }

                return existingDocumento;
            })
            .map(documentoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Documento> findAll(Pageable pageable) {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Documento> findOne(Long id) {
        log.debug("Request to get Documento : {}", id);
        return documentoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Documento : {}", id);
        documentoRepository.deleteById(id);
    }
    
    @Override
	public AsociadoClub createAsociadoClubByDoducmento(Documento documento, Registrador registrador, Club club) {
		
    	//BUSCO A VER SI EL DOCUMNETO EXISTE
        Optional<Documento> documentoExist = documentoRepository.findByNumeroDni(documento.getNumeroDni());
        //BUSCAMOS AL REGISTRADOR
        Optional<Registrador> resultRegistrador = registradorRepository.findById(registrador.getId()); 
        
        if(documentoExist.isPresent()) {
        	//AsociadoClub asociadoClub = documentoService.createAsociadoClubByDoducmento(documentoExist.get(), registrador);
        	//System.out.println("EL DOCUMENT EXISTE!!!!!!");
        	Optional<Asociado> asociadoExist = asociadoRepository.findByDocumento(documentoExist);
        	if(asociadoExist.isPresent()) {
        		//System.out.println("EL ASOCIADO EXISTE!!!!!!");
        		Optional<AsociadoClub> asociadoClubExist = asociadoClubRepository.findByAsociadoAndClub(asociadoExist.get(), club);
        		if(asociadoClubExist.isPresent()) { //SI EL ASOCIADOCLUB YA EXISTE, LO DEVOLVEMOS
        			//System.out.println("EL ASOCIASDOCLUB EXISTE!!!" + asociadoClubExist.get().toString());
        			return asociadoClubExist.get();
        		} else { //EN EL CASO DE QUE NO, LO CREAMOS
        			//System.out.println("EL ASOCIADOCLUB NO EXISTE Y LO CREAMOS.");
        			AsociadoClub asociadoClub = new AsociadoClub();
        			asociadoClub.setIdentificador(UUID.randomUUID());
        			asociadoClub.setFechaAsociacion(Instant.now());
        			asociadoClub.setPuntosClub(0L);
        			asociadoClub.setEstado(true);
        			asociadoClub.setAsociado(asociadoExist.get());
        			asociadoClub.setClub(resultRegistrador.get().getTrabajador().getClub());
        			asociadoClub.setRegistrador(resultRegistrador.get());
        			AsociadoClub resultAsociadoClub = asociadoClubRepository.save(asociadoClub);
        			return resultAsociadoClub;
        		}
        	} else { // SI EL DOCUMENTO EXISTE PERO EL ASOCIADO NO, CREAMOS EL ASOCIADO Y EL ASOCIADOCLUB
        		//System.out.println("CREAMOS AL ASOCIADO.");
        		Asociado asociado = new Asociado();
        		asociado.setEstado(true);
        		asociado.setDocumento(documentoExist.get());
        		Asociado resultAsociado = asociadoRepository.save(asociado);
        		AsociadoClub asociadoClub = new AsociadoClub();
        		asociadoClub.setIdentificador(UUID.randomUUID());
        		asociadoClub.setFechaAsociacion(Instant.now());
        		asociadoClub.setPuntosClub(0L);
        		asociadoClub.setEstado(true);
        		asociadoClub.setAsociado(resultAsociado);
        		asociadoClub.setClub(resultRegistrador.get().getTrabajador().getClub());
        		asociadoClub.setRegistrador(resultRegistrador.get());
        		AsociadoClub resultAsociadoClub = asociadoClubRepository.save(asociadoClub);
        		return resultAsociadoClub;
        	}
        } else { //SI EL DOCUMENTO NO EXITE TENEMOS QUE CREAR DOCUMENTO, ASOCIADO Y ASOCIADO CLUB
        	//System.out.println("CREAMOS TODO.");
        	try {
        		// Crear un documento
        		Documento resultDocumento = documentoRepository.save(documento);
        		
        		// crear un Asociado
        		Asociado asociado = new Asociado();
        		asociado.setEstado(true);
        		asociado.setDocumento(resultDocumento);
        		Asociado resultAsociado = asociadoRepository.save(asociado);
        		// crear un AsociadoClub
        		AsociadoClub asociadoClub = new AsociadoClub();
        		asociadoClub.setIdentificador(UUID.randomUUID());
        		asociadoClub.setFechaAsociacion(Instant.now());
        		asociadoClub.setPuntosClub(0L);
        		asociadoClub.setEstado(true);
        		asociadoClub.setAsociado(resultAsociado);
        		asociadoClub.setClub(resultRegistrador.get().getTrabajador().getClub());
        		asociadoClub.setRegistrador(resultRegistrador.get());
        		AsociadoClub resultAsociadoClub = asociadoClubRepository.save(asociadoClub);
        		return resultAsociadoClub;
        	} catch(IOException e) {
        		System.out.println("ERROR AL INTENTAR ASOCIADR UN CLIENTE CON DOCUMENTO Y REGISTRADOR");
        		return null;
        	}
        }
		
		
	}
	@Override
	public Optional<Documento> findByNumeroDni(Long numeroDni) {
		log.debug("Request to get Documento by numeroDni: {}", numeroDni);
        return documentoRepository.findByNumeroDni(numeroDni);
	}
}
