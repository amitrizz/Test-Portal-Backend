package com.springsecurity.userresultquestion.repository;

import com.springsecurity.userresultquestion.model.UserResultQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserResultQuestionRepository extends JpaRepository<UserResultQuestion,Long> {
    @Query(value = """
        SELECT 
            category,
            COUNT(*) AS Total_Question,
            COUNT(*) FILTER (WHERE question_marks > 0) AS Correct_Ans,
            SUM(question_marks) AS total_marks
        FROM 
            public.user_result_question
        WHERE 
            exam_set_id = :examSetId 
            AND user_id = :userId
        GROUP BY 
            category
        ORDER BY 
            total_marks DESC
        """, nativeQuery = true)
    List<UserResultProjection> getUserResults(@Param("examSetId") Long examSetId, @Param("userId") Long userId);

    @Query(value = """
        SELECT 
            category
        FROM 
            public.user_result_question
        WHERE 
            exam_set_id = :examSetId
        GROUP BY 
            category
        ORDER BY 
            category
        """, nativeQuery = true)
    List<ExamSetCategoryProjection> getExamSetCategory(@Param("examSetId") Long examSetId);

    @Query(value = """
        SELECT 
            RANK() OVER (ORDER BY SUM(question_marks) DESC) AS rank_order,
            user_id AS userId,
            SUM(question_marks) AS marks
        FROM 
            public.user_result_question
        WHERE 
            exam_set_id = :examSetId
        GROUP BY 
            user_id
        ORDER BY 
            marks DESC
        LIMIT 10
        """, nativeQuery = true)
    List<UserRankingProjection> getUserRankings(@Param("examSetId") Long examSetId);

    @Query(value = """
                        SELECT * FROM (
                            SELECT
                                user_id,
                                SUM(question_marks) AS marks,
                                RANK() OVER (ORDER BY SUM(question_marks) DESC) AS rank_order
                            FROM
                                public.user_result_question
                            WHERE
                                exam_set_id = :examSetId
                            GROUP BY
                                user_id
                        ) ranked_users
                        WHERE ranked_users.user_id = :userId
                    """, nativeQuery = true)
    Optional<UserRankingProjection> getCurrentUserRank(@Param("examSetId") Long examSetId, @Param("userId") Long userId);


    @Query(value = """   
                    SELECT
                        exam_set_id as examSetId
                    FROM
                        public.user_result_question
                    WHERE
                        user_id = :userId
                    GROUP BY
                        exam_set_id
                    """, nativeQuery = true)
    List<UserTakenExamSets> getCurrentUserExamSets(@Param("userId") Long userId);


    UserResultQuestion findByUserIdAndQuestionId(Long userId,Long questionId);
    void deleteByExamSetIdAndUserId(Long examSetId, Long userId);
    List<UserResultQuestion> findByUserIdAndCategoryAndExamSetId(Long userId,String category,Long examSetId);
    List<UserResultQuestion> findByUserIdAndExamSetId(Long userId,Long examSetId);

    void deleteByExamSetId(Long examSetId);
}
