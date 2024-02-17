package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Language;
import com.example.LinguaSphere.entity.TeacherLanguage;
import com.example.LinguaSphere.entity.dto.TeacherLanguageDto;
import com.example.LinguaSphere.repository.TeacherLanguageRepository;
import com.example.LinguaSphere.service.LanguageService;
import com.example.LinguaSphere.service.TeacherLanguageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TeacherLanguageServiceImpl implements TeacherLanguageService {

    @Autowired
    private TeacherLanguageRepository teacherLanguageRepository;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void save(TeacherLanguage teacherLanguage) {
        teacherLanguageRepository.save(teacherLanguage);
    }

    @Override
    public void saveAll(List<TeacherLanguage> teacherLanguageList) {
        teacherLanguageRepository.saveAll(teacherLanguageList);
    }

    @Override
    public List<TeacherLanguage> findAllByTeacherId(Long teacherId) {
        return teacherLanguageRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<TeacherLanguage> findAllByLanguageId(Long id) {
        return teacherLanguageRepository.findAllByLanguageId(id);
    }

    @Override
    public List<TeacherLanguageDto> getPricesByLanguages(Long teacherId) {
        List<TeacherLanguage> teacherLanguages = findAllByTeacherId(teacherId);
        List<TeacherLanguageDto> teacherLanguageDtoList = new ArrayList<>();
        for (TeacherLanguage teacherLanguage : teacherLanguages
        ) {
            TeacherLanguageDto teacherLanguageDto = modelMapper.map(teacherLanguage, TeacherLanguageDto.class);
            Language language = languageService.findById(teacherLanguage.getLanguageId());
            teacherLanguageDto.setLanguage(language.getName());
            teacherLanguageDtoList.add(teacherLanguageDto);
        }
        return teacherLanguageDtoList;
    }

    @Override
    public TeacherLanguage findByTeacherIdAndLanguageId(Long teacherId, Long languageId) {
        List<TeacherLanguage> teacherLanguages = findAllByTeacherId(teacherId);
        for (TeacherLanguage teacherLanguage : teacherLanguages
             ) {
            if (Objects.equals(teacherLanguage.getLanguageId(), languageId)) {
                return teacherLanguage;
            }
        }
        return null;
    }

}
