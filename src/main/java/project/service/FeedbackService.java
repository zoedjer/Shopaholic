package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dbaccess.FeedbackTable;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    private FeedbackTable feedbackTable;
    
    public void createFeedback(long userId, String message) {
	feedbackTable.insert(userId, message);
    }
}
