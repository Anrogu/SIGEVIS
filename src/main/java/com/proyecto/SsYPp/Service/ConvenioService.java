package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Entity.Convenio;
import com.proyecto.SsYPp.Repository.ConvenioRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ConvenioService {

    private final ConvenioRepository repository;

    public ConvenioService(ConvenioRepository repository) {
        this.repository = repository;
    }

    public List<Convenio> findAll() {
        return repository.findAll();
    }

    public Convenio save(Convenio convenio) {
        return repository.save(convenio);
    }

    public Convenio findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<Convenio> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
