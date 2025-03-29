package com.springsecurity.examaccess.service.imp;

import com.springsecurity.admin.service.UserService;
import com.springsecurity.examaccess.model.ExamAccess;
import com.springsecurity.examaccess.model.ExpiredExamAccess;
import com.springsecurity.examaccess.repository.ExamAccessRepository;
import com.springsecurity.examaccess.repository.ExpiredExamAccessRepository;
import com.springsecurity.examaccess.service.ExamAccessService;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import com.springsecurity.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamAccessServiceImp implements ExamAccessService {
    private final ExamAccessRepository examAccessRepository;
    private final ExpiredExamAccessRepository expiredExamAccessRepository;
    private final UserRepository userRepository;


    public ExamAccessServiceImp(ExamAccessRepository examAccessRepository , ExpiredExamAccessRepository expiredExamAccessRepository  , UserRepository userRepository ) {
        this.examAccessRepository = examAccessRepository;
        this.expiredExamAccessRepository = expiredExamAccessRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ExamAccess createExamAccess(ExamAccess examAccess) throws UserException {

        ExamAccess checkExamAccess = examAccessRepository.findByPaymentId(examAccess.getPaymentId());
        if (checkExamAccess!=null){
            throw new UserException("Already PaymentID Found");
        }
        User user = userRepository.findById(Math.toIntExact(examAccess.getUserId())).orElse(null);
        if (user==null){
            throw new UserException("UserId or ExamId is Provided by User is Wrong");
        }

        ExamAccess examAccess1 = new ExamAccess();
        examAccess1.setCreateAt(LocalDateTime.now());
        examAccess1.setExamId(examAccess.getExamId());
        examAccess1.setUserId(examAccess.getUserId());
        examAccess1.setPaymentId(examAccess.getPaymentId());
        return examAccessRepository.save(examAccess1);
    }

    @Override
    public ExamAccess updateExamAccess(ExamAccess examAccess, Long examAccessId) throws UserException {
        ExamAccess examAccess1 = examAccessRepository.findById(examAccessId).orElse(null);
        if (examAccess1==null){
            throw new UserException("ExamAccessId Not Found");
        }
        examAccess1.setAccessed(examAccess.getAccessed());
        return examAccessRepository.save(examAccess1);
    }

    @Override
    public void deleteExamAccess(Long examAccessId) throws UserException {
        ExamAccess examAccess1 = examAccessRepository.findById(examAccessId).orElse(null);
        if (examAccess1==null){
            throw new UserException("ExamAccessId Not Found");
        }
        examAccessRepository.deleteById(examAccessId);
        return;
    }

    @Override
    public List<Integer> getAllExamsAccess(Long userId,boolean access) {
        List<ExamAccess> examAccesses = examAccessRepository.findAllByUserIdAndAccessed(userId,access);
        List<Integer> integerList = new ArrayList<>();
        for (ExamAccess examAccess:examAccesses){
            integerList.add(Math.toIntExact(examAccess.getExamId()));
        }
        return integerList;
    }

    @Override
    public ExamAccess getExamAccessById(Long examAccessId) throws UserException {
        ExamAccess examAccess = examAccessRepository.findById(examAccessId).orElse(null);
        if (examAccess==null){
            throw new UserException("ExamAccessId Not Found");
        }
        return examAccess;
    }

    @Override
    public void removeExpiredExamAccess() {
        List<ExamAccess> examAccesses = examAccessRepository.findAllByAccessed(false);
        for (ExamAccess examAccess:examAccesses){
            ExpiredExamAccess expiredExamAccess = new ExpiredExamAccess();
            expiredExamAccess.setAccessed(examAccess.getAccessed());
            expiredExamAccess.setPaymentId(examAccess.getPaymentId());
            expiredExamAccess.setUserId(examAccess.getUserId());
            expiredExamAccess.setExamId(examAccess.getExamId());
            expiredExamAccess.setCreateAt(examAccess.getCreateAt());
            expiredExamAccessRepository.save(expiredExamAccess);
            examAccessRepository.deleteById(examAccess.getAccessId());
        }

        return;
    }

    @Override
    public void deleteExamAccessByExamId(Long examId) {
        examAccessRepository.deleteByExamId(examId);
    }
}
