package FantasyBasketball.utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RobinRoundSchedulerTests {

    private final List<Integer> teamList = List.of(1, 2, 3, 4);
    private final LocalDate cur_date = LocalDate.now();
    private final robinRoundScheduler robinRoundScheduler = new robinRoundScheduler(teamList, cur_date, 2);

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        //tearDown function
    }

    @Test
    public void test_robin_round_Scheduler() {

        Hashtable<LocalDate, List<List<Integer>>> schedule = robinRoundScheduler.createSchedule();

        LocalDate test_date = (LocalDate) schedule.keySet().toArray()[0];

        List<List<Integer>> cur_matchup = schedule.get(test_date);

        Assertions.assertEquals(cur_matchup.get(0).get(0), 1);
        Assertions.assertEquals(cur_matchup.get(0).get(1), 4);
    }

}
