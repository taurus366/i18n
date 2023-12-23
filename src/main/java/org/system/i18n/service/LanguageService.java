package org.system.i18n.service;

import org.system.i18n.model.entity.LanguageEntity;

import java.util.List;

public interface LanguageService {

    List<LanguageEntity> getAllLanguages();
    List<LanguageEntity> getAllActiveLanguages();
    LanguageEntity getLanguageById(Long langId);
    LanguageEntity getLanguageByLocale(String locale);

    LanguageEntity getLanguageByDeffault();

    void saveAll(List<LanguageEntity> entities);


}
