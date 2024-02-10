package com.example.LinguaSphere.web.rest;

import com.example.LinguaSphere.entity.TestAnswer;
import com.example.LinguaSphere.entity.TestQuestion;
import com.example.LinguaSphere.entity.TestQuestionAnswer;
import com.example.LinguaSphere.entity.dto.TestAnswerDtoBytes;
import com.example.LinguaSphere.entity.dto.TestQuestionDtoBytes;
import com.example.LinguaSphere.service.TestAnswerService;
import com.example.LinguaSphere.service.TestQuestionAnswerService;
import com.example.LinguaSphere.service.TestQuestionService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestQuestionService testQuestionService;
    @Autowired
    private TestAnswerService testAnswerService;
    @Autowired
    private TestQuestionAnswerService testQuestionAnswerService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getTestQuestions")
    public Map<TestQuestionDtoBytes, List<TestAnswerDtoBytes>> getTestQuestions(Long languageId) {
        List<TestQuestion> testQuestionList = testQuestionService.findAllByLanguageId(languageId);

        Map<TestQuestionDtoBytes, List<TestAnswerDtoBytes>> testQuestions = new HashMap<>();
        for (TestQuestion testQuestion : testQuestionList
             ) {
            TestQuestionDtoBytes testQuestionDtoBytes = modelMapper.map(testQuestion, TestQuestionDtoBytes.class);
            testQuestionDtoBytes.setFile(Base64.encodeBase64String(testQuestion.getImage()));
            List<TestQuestionAnswer> questionAnswers = testQuestionAnswerService.findAllByQuestionId(testQuestion.getId());
            List<Long> answersIds = new ArrayList<>();
            for (TestQuestionAnswer testQuestionAnswer : questionAnswers
                 ) {
                answersIds.add(testQuestionAnswer.getAnswerId());
            }
            List<TestAnswer> testAnswers = testAnswerService.findAllByIds(answersIds);
            List<TestAnswerDtoBytes> testAnswersDtoBytes = new ArrayList<>();
            for (TestAnswer testAnswer : testAnswers
                 ) {
                TestAnswerDtoBytes testAnswerDtoBytes = modelMapper.map(testAnswer, TestAnswerDtoBytes.class);
                testAnswerDtoBytes.setFile(Base64.encodeBase64String(testAnswer.getImage()));
                testAnswersDtoBytes.add(testAnswerDtoBytes);
            }
            testQuestions.put(testQuestionDtoBytes, testAnswersDtoBytes);
        }

        return testQuestions;
    }

    @PostMapping("/addQuestion")
    public void addQuestion(Long languageId) {
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setText("");
        testQuestion.setLanguageId(languageId);
        testQuestion.setLevel(1);
        testQuestionService.save(testQuestion);
    }

    @DeleteMapping("/deleteQuestion")
    public void deleteQuestion(Long questionId) {
        testQuestionService.deleteById(questionId);
        List<TestQuestionAnswer> testQuestionAnswerList = testQuestionAnswerService.findAllByQuestionId(questionId);
        List<Long> answers = new ArrayList<>();
        for (TestQuestionAnswer element : testQuestionAnswerList
             ) {
            answers.add(element.getAnswerId());
        }
        testAnswerService.deleteByIds(answers);
    }

    @PostMapping("/addAnswer")
    public void addAnswer(Long questionId) {
        TestAnswer testAnswer = new TestAnswer();
        testAnswer.setText("");
        testAnswerService.save(testAnswer);
        TestQuestionAnswer testQuestionAnswer = new TestQuestionAnswer();
        testQuestionAnswer.setQuestionId(questionId);
        testQuestionAnswer.setAnswerId(testAnswer.getId());
        testQuestionAnswerService.save(testQuestionAnswer);
    }

    @DeleteMapping("/deleteAnswer")
    public void deleteAnswer(Long answerId) {
        TestQuestionAnswer testQuestionAnswer = testQuestionAnswerService.findByAnswerId(answerId);
        testQuestionAnswerService.deleteById(testQuestionAnswer.getId());
        testAnswerService.deleteById(answerId);
    }

    @PostMapping("/editQuestionText")
    public void editQuestionText(Long questionId, String text) {
        TestQuestion testQuestion = testQuestionService.findById(questionId);
        testQuestion.setText(text);
        testQuestionService.save(testQuestion);
    }

    @PostMapping("/editQuestionFile")
    public void editQuestionFile(Long questionId, MultipartFile file) throws IOException {
        TestQuestion testQuestion = testQuestionService.findById(questionId);
        testQuestion.setImage(file.getBytes());
        testQuestionService.save(testQuestion);
    }

    @PostMapping("/deleteQuestionFile")
    public void deleteQuestionFile(Long questionId) {
        TestQuestion testQuestion = testQuestionService.findById(questionId);
        testQuestion.setImage(null);
        testQuestionService.save(testQuestion);
    }

    @PostMapping("/editQuestionLevel")
    public void editQuestionLevel(Long questionId, int level) {
        TestQuestion testQuestion = testQuestionService.findById(questionId);
        testQuestion.setLevel(level);
        testQuestionService.save(testQuestion);
    }

}
