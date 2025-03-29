package com.springsecurity.examsetquestion.service.imp;

import com.springsecurity.examsetquestion.model.Category;
import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import com.springsecurity.examsetquestion.repository.ExamSetQuestionRepository;
import com.springsecurity.examsetquestion.service.ExamSetQuestionService;
import com.springsecurity.examsets.repository.ExamSetRepository;
import com.springsecurity.examsets.service.ExamSetService;
import com.springsecurity.user.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamSetQuestionServiceImp implements ExamSetQuestionService {
    private final ExamSetQuestionRepository examSetQuestionRepository;
    private final ExamSetService examSetService;

    public ExamSetQuestionServiceImp(ExamSetQuestionRepository examSetQuestionRepository, ExamSetRepository examSetRepository, ExamSetService examSetService) {
        this.examSetQuestionRepository = examSetQuestionRepository;
        this.examSetService = examSetService;
    }

    @Override
    public ExamSetQuestion createExamSetQuestion(ExamSetQuestion examSetQuestion) throws UserException {
        System.out.println(examSetQuestion);
        if(!examSetService.isExamSetExist(examSetQuestion.getExamSetId())) {
            throw new UserException("ExamSet Not Found");
        }

        return examSetQuestionRepository.save(examSetQuestion);
    }

    @Override
    public ExamSetQuestion updateExamSetQuestion(ExamSetQuestion examSetQuestion, Long examSetQuestionId) throws UserException {
        if(!examSetService.isExamSetExist(examSetQuestion.getExamSetId())) {
            throw new UserException("ExamSet Not Found");
        }
        ExamSetQuestion examSetQuestion1 = examSetQuestionRepository.findById(examSetQuestionId).orElse(null);
        if (examSetQuestion1==null){
            throw new UserException("Question Not Found");
        }
//        examSetQuestion1.setQuestion(examSetQuestion.getQuestion());
//        examSetQuestion1.setExamSetId(examSetQuestion.getExamSetId());
//        examSetQuestion1.setMultiQuestion(examSetQuestion.isMultiQuestion());
//        examSetQuestion1.setAnswer(examSetQuestion.getAnswer());
//
//        examSetQuestion1.setMultiQuestionValue(examSetQuestion1.getMultiQuestionValue());
//        examSetQuestion1.setInfo(examSetQuestion.isInfo());
//        examSetQuestion1.setCategory(examSetQuestion.getCategory());
//        examSetQuestion1.setOptions(examSetQuestion.getOptions());
//
//        examSetQuestion1.setBeforeImage(examSetQuestion1.getBeforeImage());
//        examSetQuestion1.setAfterImage(examSetQuestion1.getAfterImage());
//        examSetQuestion1.setImageUrl(examSetQuestion1.getImageUrl());

        return examSetQuestionRepository.save(examSetQuestion);
    }

    @Override
    public void deleteExamSetQuestion(Long examSetQuestionId) throws UserException {
        ExamSetQuestion examSetQuestion1 = examSetQuestionRepository.findById(examSetQuestionId).orElse(null);
        if (examSetQuestion1==null){
            throw new UserException("Question Not Found");
        }
        examSetQuestionRepository.deleteById(examSetQuestionId);
        return;
    }

    @Override
    public List<ExamSetQuestion> getAllExamSetCategoryQuestion(Long examSetId, String category) {
        return examSetQuestionRepository.findByExamSetIdAndCategoryOrderByMultiQuestionValueAsc(examSetId,category);
    }

    @Override
    public ExamSetQuestion getExamSetQuestionById(Long examSetQuestionId) throws UserException {
        ExamSetQuestion examSetQuestion = examSetQuestionRepository.findById(examSetQuestionId).orElse(null);
        if (examSetQuestion==null){
            throw new UserException("Question Not Found");
        }
        return examSetQuestion;
    }

    @Override
    public Boolean isExamSetQuestionExist(Long examSetQuestionId) {
        ExamSetQuestion examSetQuestion = examSetQuestionRepository.findById(examSetQuestionId).orElse(null);
        if(examSetQuestion==null){
            return false;
        }
        return true;
    }

    @Override
    public List<ExamSetQuestion> getAllExamSetQuestions(Integer examSetId) {
        return examSetQuestionRepository.findByExamSetId(Long.valueOf(examSetId));
    }


//    @Override
//    public void deleteExamSetQuestionByExamSetId(Long examSetId) {
//        examSetQuestionRepository.deleteByExamSetId(examSetId);
//        return;
//    }
}
