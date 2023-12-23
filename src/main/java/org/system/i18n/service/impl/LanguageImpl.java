package org.system.i18n.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.system.i18n.model.entity.LanguageEntity;
import org.system.i18n.repository.LanguageRepository;
import org.system.i18n.service.LanguageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<LanguageEntity> getAllLanguages() {
                    return this.languageRepository.findAll();
    }

    @Override
    public List<LanguageEntity> getAllActiveLanguages() {
        return languageRepository.findAllByActive(true);
    }

    @Override
    public LanguageEntity getLanguageById(Long langId) {
        return languageRepository.findById(langId).orElse(null);
    }

    @Override
    public LanguageEntity getLanguageByLocale(String locale) {
        final LanguageEntity byLocale = languageRepository.findByLocale(locale);
       return byLocale;
    }

    @Override
    public LanguageEntity getLanguageByDeffault() {
        final LanguageEntity byDeffault = languageRepository.findByDefaultLang(true);
        return byDeffault;
    }

    @Override
    public void saveAll(List<LanguageEntity> entities) {
        languageRepository.saveAll(entities);
    }
}
