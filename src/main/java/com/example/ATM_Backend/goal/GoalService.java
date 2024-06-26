package com.example.ATM_Backend.goal;

import com.example.ATM_Backend.Badge.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {
    @Autowired
    private final GoalRepository goalRepository;

    @Autowired
    private final BadgeService badgeService;

    public void saveOrUpdateGoal(Goal goal) {
        List<Goal> existingEntries = goalRepository.findByUserNameAndAppNameAndDate(
                goal.getUserName(), goal.getAppName(), goal.getDate());

        if (existingEntries.isEmpty()) { //기존행 없을때 post
            goalRepository.save(goal);
        } else { //기존 행 있을때 put
            Goal existingEntry = existingEntries.get(0);
            updateGoal(existingEntry, goal);
        }
        badgeService.checkAndAwardBadgesBasedOnGoalUsage();
    }

    private void updateGoal(Goal existingEntry, Goal newEntry) {
        existingEntry.setGoalTime(newEntry.getGoalTime());
        existingEntry.setHowLong(newEntry.getHowLong());
        existingEntry.setOnGoing(newEntry.getOnGoing());
        goalRepository.save(existingEntry);
    }

    public List<Goal> getGoalByUserName(String userName) {
        List<Goal> goals = goalRepository.findByUserName(userName);
        if (goals == null || goals.isEmpty()) {
            throw new IllegalArgumentException("No Goal entries found for userName: " + userName);
        }
        return goals;
    }

    public boolean deleteGoalByUserName(String userName) {
        List<Goal> goals = goalRepository.findByUserName(userName);
        if (!goals.isEmpty()) {
            goalRepository.deleteAll(goals);
            return true;
        }
        return false;
    }
}
