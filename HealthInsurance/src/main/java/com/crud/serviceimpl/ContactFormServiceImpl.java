package com.crud.serviceimpl;

import com.crud.entity.ContactForm;
import com.crud.repository.ContactFormRepository;
import com.crud.service.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactFormServiceImpl implements ContactFormService {

    @Autowired
    private ContactFormRepository contactFormRepository;


    @Override
    public ContactForm submitContactForm(ContactForm form) {
        return contactFormRepository.save(form);
    }

    @Override
    public List<ContactForm> getAllContactForms() {
        return contactFormRepository.findAll();
    }
}
