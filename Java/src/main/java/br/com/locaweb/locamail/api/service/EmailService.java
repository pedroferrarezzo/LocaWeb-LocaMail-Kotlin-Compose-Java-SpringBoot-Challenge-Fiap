package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    

}
