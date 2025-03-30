package com.springsecurity.examsets.service.imp;

import com.springsecurity.examsets.model.ExamSet;
import com.springsecurity.examsets.repository.ExamSetRepository;
import com.springsecurity.examsets.service.ExamSetService;
import com.springsecurity.user.exception.UserException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamSetServiceImp implements ExamSetService {
    private final ExamSetRepository examSetRepository;
    public ExamSetServiceImp(ExamSetRepository examSetRepository) {
        this.examSetRepository = examSetRepository;
    }

    @Override
    public ExamSet createExamSet(ExamSet examSet) throws UserException {
        examSet.setCreatedAt(LocalDateTime.now());
        return examSetRepository.save(examSet);
    }

    @Override
    public ExamSet updateExamSet(ExamSet examSet, Long examSetId) throws UserException {
        ExamSet examSet1 = examSetRepository.findById(examSetId).orElse(null);
        if (examSet1==null || !examSet1.getExamId().equals(examSet.getExamId())){
            throw new UserException("ExamSet Not Found or ExamName Not Match");
        }
        examSet1.setExamId(examSet.getExamId());
        examSet1.setExamSetName(examSet1.getExamSetName());
        examSet1.setCreatedAt(LocalDateTime.now());
        examSet1.setIsAttempted(examSet.getIsAttempted());
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
