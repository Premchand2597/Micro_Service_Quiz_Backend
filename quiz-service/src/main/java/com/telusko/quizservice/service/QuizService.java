package com.telusko.quizservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.quizservice.dao.QuizDao;
import com.telusko.quizservice.feign.QuizInterface;
import com.telusko.quizservice.model.QuestionWrapper;
import com.telusko.quizservice.model.Quiz;
import com.telusko.quizservice.model.Response;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    @CircuitBreaker(name = "questionService", fallbackMethod = "createQuizFallback")
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
    
    public ResponseEntity<String> createQuizFallback(String category, int numQ, String title, Throwable ex) {
        return new ResponseEntity<>("Question service unavailable. Cannot create quiz now.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @CircuitBreaker(name = "questionService", fallbackMethod = "getQuizQuestionsFallback")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
          Quiz quiz = quizDao.findById(id).get();
          List<Integer> questionIds = quiz.getQuestionIds();
          ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
          return questions;
    }
    
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestionsFallback(Integer id, Throwable ex) {
        return new ResponseEntity<>(List.of(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @CircuitBreaker(name = "questionService", fallbackMethod = "calculateResultFallback")
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
    
    public ResponseEntity<Integer> calculateResultFallback(Integer id, List<Response> responses, Throwable ex) {
        return new ResponseEntity<>(0, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @CircuitBreaker(name = "questionService", fallbackMethod = "getAllQuestionsForTestingFallback")
    public ResponseEntity<List<QuestionWrapper>> getAllQuestionsForTesting() {
          ResponseEntity<List<QuestionWrapper>> allQuestions = quizInterface.getAllQuestions();
          return allQuestions;
    }
    
    public ResponseEntity<List<QuestionWrapper>> getAllQuestionsForTestingFallback(Throwable ex) {
        return new ResponseEntity<>(List.of(), HttpStatus.SERVICE_UNAVAILABLE);
    }
    
}
