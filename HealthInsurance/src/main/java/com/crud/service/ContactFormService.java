package com.crud.service;

import com.crud.entity.ContactForm;

import java.util.List;

public interface ContactFormService {

    ContactForm submitContactForm(ContactForm form);
    List<ContactForm>getAllContactForms();


}
