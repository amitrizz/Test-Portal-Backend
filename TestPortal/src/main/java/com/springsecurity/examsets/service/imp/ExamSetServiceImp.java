package com.springsecurity.examsets.service.imp;

import com.springsecurity.examnames.service.ExamService;
import com.springsecurity.examsetquestion.service.ExamSetQuestionService;
import com.springsecurity.examsets.model.ExamSet;
import com.springsecurity.examsets.repository.ExamSetRepository;
import com.springsecurity.examsets.service.ExamSetService;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.userresultquestion.service.UserResultQuestionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamSetServiceImp implements ExamSetService {
    private final ExamSetRepository examSetRepository;
//    private final ExamService examService;
//    private final ExamSetQuestionService examSetQuestionService;
//    private final UserResultQuestionService userResultQuestionService;

    public ExamSetServiceImp(ExamSetRepository examSetRepository, ExamService examService) {
        this.examSetRepository = examSetRepository;
//        this.examService = examService;
//        this.examSetQuestionService = examSetQuestionService;
//        this.userResultQuestionService = userResultQuestionService;
    }

    @Override
    public ExamSet createExamSet(ExamSet examSet) throws UserException {
        ExamSet examSet1 = new ExamSet();
//        if(!examService.isExamExist(examSet.getExamId())){
//            throw new UserException("Exam Not Found");
//        }
        examSet1.setExamId(examSet.getExamId());
        examSet1.setCreatedAt(LocalDateTime.now());
        examSet1.setExamSetName(examSet.getExamSetName());
        return examSetRepository.save(examSet1);
    }

    @Override
    public ExamSet updateExamSet(ExamSet examSet, Long examSetId) throws UserException {
//        if(!examService.isExamExist(examSet.getExamId())){
//            throw new UserException("Exam Not Found");
//        }
        ExamSet examSet1 = examSetRepository.findById(examSetId).orElse(null);
        if (examSet1==null || !examSet1.getExamId().equals(examSet.getExamId())){
            throw new UserException("ExamSet Not Found or ExamName Not Match");
        }
        examSet1.setExamId(examSet.getExamId());
        examSet1.setCreatedAt(LocalDateTime.now());
        examSet1.setExamSetName(examSet.getExamSetName());
        return examSetRepository.save(examSet1);
    }

    @Override
    public List<ExamSet> getAllExamSetByExamId(Long examId) {
        return examSetRepository.findByExamId(examId);
    }

    @Override
    public Boolean isExamSetExist(Long examSetId) {
        ExamSet examSet = examSetRepository.findById(examSetId).orElse(null);
        if (examSet==null){
            return false;
        }
        return true;
    }

    @Override
    public void deleteExamSet(Long examSetId) throws UserException {
        ExamSet examSet = examSetRepository.findById(examSetId).orElse(null);
        if (examSet==null){
            throw new UserException("ExamSet Not Found");
        }
        examSetRepository.deleteById(examSetId);
        return;
    }


    @Override
    public void deleteExamSetsByExamId(Long examId) throws UserException {
        List<ExamSet> examSets = getAllExamSetByExamId(examId);
        for (ExamSet examSet:examSets){
            deleteExamSet(examSet.getSetId());
        }
        return;
    }

    @Override
    public ExamSet getExamSetById(Long examSetId) throws UserException {
        ExamSet examSet = examSetRepository.findById(examSetId).orElse(null);
        if (examSet==null){
            throw new UserException("ExamSet Not Found");
        }
        return examSet;
    }
}
