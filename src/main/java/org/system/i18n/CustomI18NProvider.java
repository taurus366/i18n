package org.system.i18n;

import com.vaadin.flow.component.Direction;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.service.LanguageService;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomI18NProvider implements I18NProvider {

    private final LanguageService languageService;

    public static final String BUNDLE_PREFIX = "i18n/translate";

    public final Locale LOCALE_EN = new Locale("en");
    public final Locale LOCALE_BG = new Locale("bg");

    private final List<Locale> locales = List.of(LOCALE_EN, LOCALE_BG);

    public CustomI18NProvider(LanguageService languageService) {
        this.languageService = languageService;
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        if( key == null) {
            LoggerFactory.getLogger(CustomI18NProvider.class.getName())
                    .warn("Got language request for key with null value!");
            return "";
        }

        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);


        String value;
        try {
            value = bundle.getString(key);
            System.out.println(value);
        } catch (final MissingResourceException e) {
            LoggerFactory.getLogger(CustomI18NProvider.class.getName())
                    .warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }

    public ComboBox<String> getLanguageSelectorBox() {
        ComboBox<String> languageSelector = new ComboBox<>("Select Language");

        final Collection<LanguageDTO> allActiveLanguages = this.getAllActiveLanguages();

//        languageSelector.setItems(LOCALE_BG, LOCALE_EN);
            languageSelector.setItems(allActiveLanguages
                    .stream()
                    .map(LanguageDTO::getName)
                    .collect(Collectors.toList()));

//        languageSelector.setValue(VaadinService.getCurrentRequest().getLocale());

        languageSelector.addValueChangeListener(event -> {
            System.out.println(event);
//            UI.getCurrent().setDirection(Direction.LEFT_TO_RIGHT);
////            Locale selectedLocale = event.getValue();
//            UI.getCurrent().setLocale(LOCALE_BG);
//            UI.getCurrent().getPage().reload();
        });
//        https://github.com/vaadin/vaadin-localization-example
        return languageSelector;
    }

    public List<LanguageDTO> getAllLanguages() {
        return this.languageService.getAllLanguages();
    }

    public List<LanguageDTO> getAllActiveLanguages() {
        return this.languageService.getAllActiveLanguages();
    }

    public LanguageDTO getLanguageById(Long langId) {
        return this.languageService.getLanguageById(langId);
    }

    public LanguageDTO getLanguageByLocale(String locale) {
        return this.languageService.getLanguageByLocale(locale);
    }
}
