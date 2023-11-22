package dev.cele.asa_sm.services;

import dev.cele.asa_sm.dto.ini.IniExtraMap;
import dev.cele.asa_sm.dto.ini.IniSection;
import dev.cele.asa_sm.dto.ini.IniValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
@Slf4j
public class IniSerializerService {

    @SneakyThrows
    public void readIniFile(Object iniDtoObject, File iniFile) {

        //use ini4j to read the ini file
        Ini ini = new Ini(iniFile);
        Map<String, Profile.Section> gameUserSettingsMap = ini.entrySet().stream()
                .collect(toMap(it -> it.getKey(), it -> it.getValue()));

        //read recognized sections from dto
        var iniDtoClass = iniDtoObject.getClass();
        var allFields = iniDtoClass.getDeclaredFields();
        var sections = Arrays.stream(allFields)
                .filter(field -> {;
                    var hasAnnotation = field.isAnnotationPresent(IniSection.class);
                    var classHasAnnotation = field.getType().isAnnotationPresent(IniSection.class);
                    return hasAnnotation || classHasAnnotation;
                })
                .toList();

        //loop over sections
        for(Field sectionField: sections) {
            //finding section info/dto/class
            var sectionName = sectionField.getName();
            if(sectionField.isAnnotationPresent(IniSection.class) && !sectionField.getAnnotation(IniSection.class).value().isEmpty()){
                sectionName = sectionField.getAnnotation(IniSection.class).value();
            }
            if(sectionField.getType().isAnnotationPresent(IniSection.class) && !sectionField.getType().getAnnotation(IniSection.class).value().isEmpty()){
                sectionName = sectionField.getType().getAnnotation(IniSection.class).value();
            }

            log.debug("== READING SECTION " + sectionName + " ==");

            if(!gameUserSettingsMap.containsKey(sectionName)){
                log.debug(sectionName + "NOT FOUND!");
                continue;
            }

            var sectionIniContent = gameUserSettingsMap.remove(sectionName);
            var sectionFieldClass = sectionField.getType();
            sectionField.setAccessible(true);
            var sectionFieldObject = sectionField.get(iniDtoObject);

            var validFields = Arrays.stream(sectionFieldClass.getDeclaredFields()).filter(
                    field -> field.isAnnotationPresent(IniValue.class) && !field.isAnnotationPresent(IniExtraMap.class)
            ).toList();

            //reading fields inside the sectionField class
            var readFields = new ArrayList<String>();
            for(Field iniField: validFields){
                var fieldName = iniField.isAnnotationPresent(IniValue.class) ? iniField.getAnnotation(IniValue.class).value() : iniField.getName();
                readFields.add(fieldName);
                if(sectionIniContent.containsKey(fieldName)){
                    log.info("rd: " + fieldName);
                    var fieldValueToSet = sectionIniContent.get(fieldName, iniField.getType());
                    log.info(fieldName + " <= " + fieldValueToSet);
                    iniField.setAccessible(true);
                    iniField.set(sectionFieldObject, fieldValueToSet);
                }
            }

            //read extra fields from the section
            var extraFieldsFromIni = sectionIniContent.entrySet().stream()
                    .filter(it -> !readFields.contains(it.getKey()))
                    .collect(Collectors.toMap(it -> it.getKey(), it -> it.getValue()));

            var extraFieldsContainer = Arrays.stream(sectionFieldClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(IniExtraMap.class))
                    .findFirst();

            if(!extraFieldsFromIni.isEmpty() && extraFieldsContainer.isPresent()){
                log.debug("Found extra fields container, injecting remaining fields there");

                var extraFieldsContainerField = extraFieldsContainer.get();
                extraFieldsContainerField.setAccessible(true);
                var extraFieldsMap = (Map<String, String>) extraFieldsContainerField.get(sectionFieldObject);
                if(extraFieldsMap == null){
                    extraFieldsMap = extraFieldsFromIni;
                }else{
                    extraFieldsMap.putAll(extraFieldsFromIni);
                }
                extraFieldsContainerField.set(sectionFieldObject, extraFieldsMap);
            }
        }

        log.debug("== UNRECOGNIZED SECTIONS == ");
        for(var unrecognizedSection: gameUserSettingsMap.entrySet()){
            log.debug(unrecognizedSection.getKey());
        }
        var extraSectionsContainer = Arrays.stream(iniDtoClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(IniExtraMap.class))
                .findFirst();
        if(extraSectionsContainer.isPresent()){
            Map<String, Map<String, Object>> extraSections = gameUserSettingsMap.entrySet().stream().collect(
                    toMap(
                            it -> it.getKey(),
                            it -> it.getValue().entrySet().stream().collect(toMap(it2 -> it2.getKey(), it2 -> it2.getValue()))
                    )
            );

            log.debug("Found extra sections container, injecting remaining sections there");
            var extraSectionsContainerField = extraSectionsContainer.get();
            extraSectionsContainerField.setAccessible(true);
            extraSectionsContainerField.set(iniDtoObject, extraSections);
        }

    }


    @SneakyThrows
    public void writeIniFile(Object iniDtoObject, File iniFile) {
        Ini ini = new Ini();

        //read recognized sections from dto
        var iniDtoClass = iniDtoObject.getClass();
        var allFields = iniDtoClass.getDeclaredFields();
        var sections = Arrays.stream(allFields)
                .filter(field -> {;
                    var hasAnnotation = field.isAnnotationPresent(IniSection.class);
                    var classHasAnnotation = field.getType().isAnnotationPresent(IniSection.class);
                    return hasAnnotation || classHasAnnotation;
                })
                .toList();

        //loop over sections
        for(Field sectionField: sections) {
            //finding section info/dto/class
            var sectionName = sectionField.getName();
            if (sectionField.isAnnotationPresent(IniSection.class) && !sectionField.getAnnotation(IniSection.class).value().isEmpty()) {
                sectionName = sectionField.getAnnotation(IniSection.class).value();
            }
            if (sectionField.getType().isAnnotationPresent(IniSection.class) && !sectionField.getType().getAnnotation(IniSection.class).value().isEmpty()) {
                sectionName = sectionField.getType().getAnnotation(IniSection.class).value();
            }

            var sectionFieldClass = sectionField.getType();
            sectionField.setAccessible(true);
            var sectionFieldObject = sectionField.get(iniDtoObject);

            log.debug("Writing section " + sectionName);

            var validFields = Arrays.stream(sectionFieldClass.getDeclaredFields()).filter(
                    field -> field.isAnnotationPresent(IniValue.class) && !field.isAnnotationPresent(IniExtraMap.class)
            ).toList();

            //writing fields inside the sectionField class
            for (Field iniField : validFields) {
                var fieldName = iniField.isAnnotationPresent(IniValue.class) ? iniField.getAnnotation(IniValue.class).value() : iniField.getName();
                iniField.setAccessible(true);
                var fieldValue = iniField.get(sectionFieldObject);
                if (fieldValue != null) {
                    log.debug("Setting field " + fieldName + " to " + fieldValue);
                    ini.put(sectionName, fieldName, fieldValue);
                }
            }

            //write extra fields
            var extraFieldsContainer = Arrays.stream(sectionFieldClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(IniExtraMap.class))
                    .findFirst();

            if(extraFieldsContainer.isPresent()){
                log.debug("Found extra fields container, injecting remaining fields there");

                var extraFieldsContainerField = extraFieldsContainer.get();
                extraFieldsContainerField.setAccessible(true);
                var extraFieldsMap = (Map<String, String>) extraFieldsContainerField.get(sectionFieldObject);
                if(extraFieldsMap != null){
                    for(var extraField: extraFieldsMap.entrySet()){
                        log.debug("Setting field " + extraField.getKey() + " to " + extraField.getValue());
                        ini.put(sectionName, extraField.getKey(), extraField.getValue());
                    }
                }
            }

        }
        //write extra sections
        var extraSectionsContainer = Arrays.stream(iniDtoClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(IniExtraMap.class))
                .findFirst();
        if(extraSectionsContainer.isPresent()){
            var extraSectionsContainerField = extraSectionsContainer.get();
            extraSectionsContainerField.setAccessible(true);
            var extraSections = (Map<String, Map<String, Object>>) extraSectionsContainerField.get(iniDtoObject);
            if(extraSections != null){
                for(var extraSection: extraSections.entrySet()){
                    log.debug("Writing extra section " + extraSection.getKey());
                    for(var extraSectionField: extraSection.getValue().entrySet()){
                        log.debug("Setting field " + extraSectionField.getKey() + " to " + extraSectionField.getValue());
                        ini.put(extraSection.getKey(), extraSectionField.getKey(), extraSectionField.getValue());
                    }
                }
            }
        }

        ini.store(iniFile);
    }

}


