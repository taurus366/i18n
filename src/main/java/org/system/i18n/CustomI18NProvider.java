package org.system.i18n;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.model.dto.MultiLangualTextAreaDTO;
import org.system.i18n.service.LanguageService;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;
import java.awt.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

//        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);
        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, Locale.of(locale.toString().split("_")[0]));


        String value;
        try {
            value = bundle.getString(key);
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

    public ComboBox<LanguageDTO> getLanguageSelectorBox(String locale, String title) {
        ComboBox<LanguageDTO> languageSelector = new ComboBox<>(title);

        final Collection<LanguageDTO> allActiveLanguages = this.getAllActiveLanguages();

        languageSelector.setItems(allActiveLanguages);
        languageSelector.setItemLabelGenerator(LanguageDTO::getName);

        final LanguageDTO languageByLocale = languageService.getLanguageByLocale(locale);

        languageSelector.setValue(languageByLocale);

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

    public MultiLangualTextAreaDTO multiLanguageTextArea(Map<Long, String> data) {
        CustomI18NProvider provider = new CustomI18NProvider(languageService);
        final List<LanguageDTO> allActiveLanguages = provider.getAllActiveLanguages();
        AtomicInteger currentLanguageIndex = new AtomicInteger();


        HorizontalLayout layout = new HorizontalLayout();
        layout.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("width", "100%");

        TextArea textArea = new TextArea();
        textArea.getStyle()
                .set("width", "100%")
                .set("max-height", "58px")
                .set("min-height", "58px");

        Section sectionTextAreaAndFlag = new Section();
        sectionTextAreaAndFlag.getStyle()
                .set("display", "flex")
                .set("width", "100%")
                .set("align-items", "center")
                .set("justify-content", "space-around");


        CountryFlagT flag = new CountryFlagT(allActiveLanguages.get(currentLanguageIndex.get()).getCode(), false);

        flag.addAttachListener(event -> {
            Long selectedLanguageId = allActiveLanguages.get(currentLanguageIndex.get()).getId();
            String dataText = data.getOrDefault(selectedLanguageId, ""); // Get the text associated with the selected language ID
            textArea.setValue(dataText);
        });

        flag.addClickListener(event -> {

            if(currentLanguageIndex.get() + 1 < allActiveLanguages.size()){
                flag.setCountry(allActiveLanguages.get(currentLanguageIndex.addAndGet(1)).getCode());
            } else {
                currentLanguageIndex.set(0);
                flag.setCountry(allActiveLanguages.get(currentLanguageIndex.get()).getCode());
            }

            Long selectedLanguageId = allActiveLanguages.get(currentLanguageIndex.get()).getId();
            String dataText = data.getOrDefault(selectedLanguageId, ""); // Get the text associated with the selected language ID

            textArea.setValue(dataText);
        });
        textArea.addValueChangeListener(event -> {
            Long selectedLanguageId = allActiveLanguages.get(currentLanguageIndex.get()).getId();
               data.put(selectedLanguageId, textArea.getValue());

        });

        Button btnSave = new Button("Save");


        sectionTextAreaAndFlag.add(textArea, flag);

        layout.add(sectionTextAreaAndFlag, btnSave);

        MultiLangualTextAreaDTO dto = new MultiLangualTextAreaDTO();
        dto.setTextArea(textArea);
        dto.setData(data);
        dto.setButton(btnSave);
        dto.setHorizontalLayout(layout);

        return dto;
    }
}
