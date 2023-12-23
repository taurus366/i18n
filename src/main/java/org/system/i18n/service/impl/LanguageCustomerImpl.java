package org.system.i18n.service.impl;

import org.springframework.stereotype.Service;
import org.system.i18n.model.entity.LanguageCustomerEntity;
import org.system.i18n.model.entity.LanguageEntity;
import org.system.i18n.repository.LanguageCustomerRepository;
import org.system.i18n.repository.LanguageRepository;
import org.system.i18n.service.LanguageCustomerService;

@Service
public class LanguageCustomerImpl implements LanguageCustomerService {

    private final LanguageCustomerRepository languageCustomerRepository;
    private final LanguageRepository languageRepository;

    public LanguageCustomerImpl(LanguageCustomerRepository languageCustomerRepository, LanguageRepository languageRepository) {
        this.languageCustomerRepository = languageCustomerRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public LanguageCustomerEntity findBySession(String session) {
        return languageCustomerRepository.findBySession(session);
    }

    @Override
    public void save(LanguageCustomerEntity en1) {
        languageCustomerRepository.save(en1);
    }

    @Override
    public LanguageCustomerEntity getLocaleBySession(String userSession) {
        // 1 check if the session is exists on db
        LanguageCustomerEntity bySession = findBySession(userSession);
        // 2 if doesnt exists on db , create new record then set language as a BG by deffault
        if(bySession == null) {
            LanguageEntity deffaultLang = languageRepository.findByDefaultLang(true);
            LanguageCustomerEntity newEntity = new LanguageCustomerEntity();
            newEntity.setSession(userSession);
            newEntity.setLocale(deffaultLang.getLocale());
            languageCustomerRepository.save(newEntity);
            bySession = newEntity;
        }

        return bySession;
    }
}
