package org.system.i18n.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.system.i18n.model.entity.LanguageEntity;
import org.system.i18n.repository.LanguageRepository;
import org.system.i18n.service.LanguageService;

import java.util.List;

@Component
public class languageInit implements CommandLineRunner {

    private final LanguageRepository languageRepository;

    public languageInit(LanguageRepository languageRepository) {
       this.languageRepository = languageRepository;
    }

    private void initLanguages() {


        LanguageEntity entityBG = new LanguageEntity("Bulgarian", "bg", "bg_BG" , true, true);
        LanguageEntity entityEN = new LanguageEntity("English", "en", "en_EN" , true, false);
        LanguageEntity entityTR = new LanguageEntity("Turkish", "tr", "tr_TR" , true, false);

        LanguageEntity entityES = new LanguageEntity("Spanish", "es", "es_ES", false, false);
        LanguageEntity entityFR = new LanguageEntity("French", "fr", "fr_FR", false, false);
        LanguageEntity entityDE = new LanguageEntity("German", "de", "de_DE", false, false);

        languageRepository.saveAll(List.of(entityBG, entityEN, entityTR, entityES, entityFR, entityDE));

    }

    @Override
    public void run(String... args) throws Exception {
        if(languageRepository.count() == 0L) {
            initLanguages();
        }
    }
}
