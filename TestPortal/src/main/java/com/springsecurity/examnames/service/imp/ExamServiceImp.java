package com.springsecurity.examnames.service.imp;

import com.springsecurity.examaccess.model.ExamAccess;
import com.springsecurity.examaccess.repository.ExamAccessRepository;
import com.springsecurity.examaccess.service.ExamAccessService;
import com.springsecurity.examnames.model.Exam;
import com.springsecurity.examnames.repository.ExamRepository;
import com.springsecurity.examnames.service.ExamService;
import com.springsecurity.user.exception.UserException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImp implements ExamService {
    private final ExamRepository examRepository;
    private final ExamAccessService examAccessService;

    public ExamServiceImp(ExamRepository examRepository, ExamAccessService examAccessService) {
        this.examRepository = examRepository;
        this.examAccessService = examAccessService;
    }

    @Override
    public Exam createExam(Exam exam) throws UserException {
        Exam exam1 = new Exam();
        if (exam.getExamName().isEmpty() || exam.getPrice()<=0){
            throw new UserException("User Enter Wrong Input");
        }
        exam1.setExamName(exam.getExamName());
        exam1.setPrice(exam.getPrice());
        exam1.setExamCategory(exam.getExamCategory());

        return examRepository.save(exam1);
    }

    @Override
    public Exam updateExam(Exam exam, Long examId) throws UserException {
        Exam updateExam = examRepository.findById(examId).orElse(null);
        if (updateExam==null){
            throw new UserException("No Exam found With this id");
        }
        updateExam.setExamName(exam.getExamName());
        updateExam.setPrice(exam.getPrice());
        updateExam.setExamCategory(exam.getExamCategory());
        return examRepository.save(updateExam);
    }

    @Override
    public void deleteExam(Long examId) throws UserException {
        Exam deleteExam = examRepository.findById(examId).orElse(null);
        if(deleteExam==null){
            throw new UserException("No Exam found With this id");
        }

        examAccessService.deleteExamAccessByExamId(examId);
        examRepository.deleteById(examId);
        return;
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public Exam getExamById(Long examId) throws UserException {
        Optional<Exam> getExams = examRepository.findById(examId);
        if (getExams.isEmpty()){
            throw new UserException("No Exam found with this id");
        }
        return getExams.get();
    }

    @Override
    public Boolean isExamExist(Long examId) {
        Optional<Exam> getExams = examRepository.findById(examId);
        if (getExams.isEmpty()){
            return false;
        }
        return true;
    }
}
