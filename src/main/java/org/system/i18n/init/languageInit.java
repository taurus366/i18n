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


        LanguageEntity entityBG = new LanguageEntity("Bulgarian", "bg_BG", "bg" , true, true);
        LanguageEntity entityEN = new LanguageEntity("English", "en_EN", "en" , true, false);
        LanguageEntity entityTR = new LanguageEntity("Turkish", "tr_TR", "tr" , true, false);

        LanguageEntity entityES = new LanguageEntity("Spanish", "es_ES", "es", false, false);
        LanguageEntity entityFR = new LanguageEntity("French", "fr_FR", "fr", false, false);
        LanguageEntity entityDE = new LanguageEntity("German", "de_DE", "de", false, false);

        languageRepository.saveAll(List.of(entityBG, entityEN, entityTR, entityES, entityFR, entityDE));

    }

    @Override
    public void run(String... args) throws Exception {
        if(languageRepository.count() == 0L) {
            initLanguages();
        }
    }
}
