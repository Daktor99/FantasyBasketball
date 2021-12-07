package FantasyBasketball.utilities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class robinRoundScheduler {

    private final List<Integer> teamList;
    private final LocalDate startDate;
    private final Integer weeksOfPlay;

    // constructor
    public robinRoundScheduler(List<Integer> teamList, LocalDate startDate, Integer weeksOfPlay) {
        this.teamList = new ArrayList<>(teamList);
        this.startDate = startDate;
        this.weeksOfPlay = weeksOfPlay;
    }

    // Getting first half of the team list
    public List<Integer> getFirstHalf(List<Integer> teamList) {
        return teamList.subList(0, teamList.size() / 2);
    }

    // Getting second half of the team list
    public List<Integer> getSecondHalf(List<Integer> teamList) {
        List<Integer> result = teamList.subList(teamList.size() / 2, teamList.size());
        Collections.reverse(result);
        return result;
    }

    // Updating first half for match-ups
    public List<Integer> updateFirstHalf(List<Integer> firstHalf, Integer new_element) {

        // shift all elements to the right except for first element
        for (int i = firstHalf.size() - 1; i >= 1; i--) {
            firstHalf.set(i, firstHalf.get(i - 1));
        }

        // set second element to new_int (from secondHalf)
        firstHalf.set(1, new_element);

        return firstHalf;
    }

    // Updating second half for match-ups
    public List<Integer> updateSecondHalf(List<Integer> secondHalf, Integer new_element) {

        // shift all elements to the right except for first element
        for (int i = 0; i < secondHalf.size() - 1; i++) {
            secondHalf.set(i, secondHalf.get(i + 1));
        }

        // set second element to new_int (from secondHalf)
        secondHalf.set(secondHalf.size() - 1, new_element);

        return secondHalf;
    }

    // Creating unique match-ups
    public List<List<Integer>> createMatchups(List<Integer> firstHalf, List<Integer> secondHalf) {

        // Initialize a list of match ups
        List<List<Integer>> matchupList = new ArrayList<>();

        // Loop through the elements of two lists and create match ups
        for (int i = 0; i < firstHalf.size(); i++) {

            // Initialize a list of elements from first and second half that create a match up
            List<Integer> matchup = new ArrayList<>();
            matchup.add(firstHalf.get(i));
            matchup.add(secondHalf.get(i));

            matchupList.add(matchup);
        }
        return matchupList;
    }

    // This function combine all the functions above to create a schedule
    public Hashtable<LocalDate, List<List<Integer>>> createSchedule() {

        // Split the list of teams into first and second half
        List<Integer> firstHalf = getFirstHalf(this.teamList);
        List<Integer> secondHalf = getSecondHalf(this.teamList);

        // Initialize a Hashtable for schedule where key=week number, value=list of match ups
        Hashtable<LocalDate, List<List<Integer>>> schedule = new Hashtable<>();

        // Initialize a list of match ups for each week
        List<List<Integer>> matchupList;

        //Assign a list of match ups for each week
        for (int i = 0; i < this.weeksOfPlay; i++) {

            // Create match ups
            matchupList = createMatchups(firstHalf, secondHalf);

            // Add a list of match ups for each week
            schedule.put(startDate.plusWeeks(i), matchupList);

            // Update teams in both first and second for next week
            Integer last_elem_in_first = firstHalf.get(firstHalf.size() - 1);
            Integer first_elem_in_second = secondHalf.get(0);

            firstHalf = updateFirstHalf(firstHalf, first_elem_in_second);
            secondHalf = updateSecondHalf(secondHalf, last_elem_in_first);

        }
        return schedule;
    }

}