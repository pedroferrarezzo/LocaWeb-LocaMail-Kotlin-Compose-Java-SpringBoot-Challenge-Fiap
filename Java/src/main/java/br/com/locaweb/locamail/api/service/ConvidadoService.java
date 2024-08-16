package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.repository.AlteracaoRepository;
import br.com.locaweb.locamail.api.repository.ConvidadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvidadoService {

    @Autowired
    private ConvidadoRepository convidadoRepository;

    

}
