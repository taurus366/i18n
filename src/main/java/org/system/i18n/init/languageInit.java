package org.system.i18n.init;

import org.system.i18n.model.entity.LanguageEntity;
import org.system.i18n.repository.LanguageRepository;
import org.system.i18n.service.LanguageService;

import java.util.List;

public class languageInit {



    public static void initLanguages(LanguageService languageService) {


        LanguageEntity entityBG = new LanguageEntity("Bulgarian", "bg_BG", "bg" , true, true);
        LanguageEntity entityEN = new LanguageEntity("English", "en_EN", "en" , true, false);
        LanguageEntity entityTR = new LanguageEntity("Turkish", "tr_TR", "tr" , true, false);

        LanguageEntity entityES = new LanguageEntity("Spanish", "es_ES", "es", false, false);
        LanguageEntity entityFR = new LanguageEntity("French", "fr_FR", "fr", false, false);
        LanguageEntity entityDE = new LanguageEntity("German", "de_DE", "de", false, false);

        languageService.saveAll(List.of(entityBG, entityEN, entityTR, entityES, entityFR, entityDE));

    }
}
