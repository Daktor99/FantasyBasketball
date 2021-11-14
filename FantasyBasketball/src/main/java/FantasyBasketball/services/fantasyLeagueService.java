package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class fantasyLeagueService {

    @Autowired
    userRepository userRepo;

    @Autowired
    fantasyLeagueRepository leagueRepo;

    // helper function: get clientID
    // TODO: Get clientID from something like... session?
    public Integer getClientID() {
        return 0;
    }

    // helper function: find by leagueID
    public List<FantasyLeague> getLeaguesByID(Integer leagueID) throws resourceNotFoundException {
        Optional<FantasyLeague> result = leagueRepo.findById(leagueID);
        if (result.isPresent()) {
            FantasyLeague fantasyLeagueResult = result.get();
            return List.of(fantasyLeagueResult);
        } else {
            throw new resourceNotFoundException("League not found by ID in DB.");
        }
    }

    // get operation
    public List<FantasyLeague> getLeaguesByTemplate(Integer league_id,
                                                    Integer client_id,
                                                    String league_name,
                                                    Integer admin_id,
                                                    Integer league_size,
                                                    LocalDate league_start_date,
                                                    LocalDate league_end_date) {
        return leagueRepo.findByTemplate(league_id,
                client_id,
                league_name,
                admin_id,
                league_size,
                league_start_date,
                league_end_date);
    }

    // helper function: check if admin is valid user
    public Boolean checkAdmin(Integer adminID) {
        Optional<User> result = userRepo.findById(adminID);
        if (result.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // helper function: check is given start and end dates for league are valid
    public Boolean checkDates(LocalDate league_start_date, LocalDate league_end_date) throws resourceException {
        LocalDate today = LocalDate.now();
        if (league_start_date.compareTo(today) >= 0) {
            if (league_start_date.compareTo(league_end_date) < 0) {
                if (DAYS.between(league_start_date, league_end_date) >= 14) {
                    return Boolean.TRUE;
                } else {
                    throw new resourceException("Attempted league duration is less than 2 weeks.");
                }
            } else {
                throw new resourceException("Attempted leagueStartDate is after leageEndDate.");
            }
        } else {
            throw new resourceException("Attempted leagueStartDate occurs in the past.");
        }
    }

    // post operation
    public List<FantasyLeague> postLeagues(FantasyLeague fantasyLeague) throws resourceException {
        fantasyLeague.setLeagueID(0);

        //This client_id will be updated later
        fantasyLeague.setClientID(1);

        if (checkAdmin(fantasyLeague.getAdminID())) {
            if (checkDates(fantasyLeague.getLeagueStartDate(), fantasyLeague.getLeagueEndDate())) {
                FantasyLeague result = leagueRepo.save(fantasyLeague);
                return List.of(result);
            } else {
                throw new resourceException("LeagueStartDate and/or LeagueEndDate are invalid.");
            }
        } else {
            throw new resourceException("The adminID provided is not a valid userID.");
        }
    }

    // helper function: check if two given fantasy leagues are equivalent except for league name
    public Boolean checkEqualWithoutLeagueName(FantasyLeague referenceLeague, FantasyLeague compareLeague) {
        if (referenceLeague.getLeagueID().equals(compareLeague.getLeagueID()) &&
                referenceLeague.getClientID().equals(compareLeague.getClientID()) &&
                referenceLeague.getAdminID().equals(compareLeague.getAdminID()) &&
                referenceLeague.getLeagueSize().equals(compareLeague.getLeagueSize()) &&
                referenceLeague.getLeagueStartDate().equals(compareLeague.getLeagueEndDate()) &&
                referenceLeague.getLeagueEndDate().equals(compareLeague.getLeagueEndDate())
        ) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // put operation
    public List<FantasyLeague> updateLeagues(FantasyLeague fantasyLeague) throws resourceNotFoundException, resourceException {
        if (leagueRepo.existsById(fantasyLeague.getLeagueID())) {

            //This client_id will be updated later
            fantasyLeague.setClientID(1);

            FantasyLeague result = leagueRepo.save(fantasyLeague);
            FantasyLeague referenceLeague = leagueRepo.save(fantasyLeague);
            if (checkEqualWithoutLeagueName(referenceLeague, fantasyLeague)) {
                return List.of(result);
            } else {
                throw new resourceException("Given FantasyLeague attempts to change field other than league_name.");
            }
        } else {
            throw new resourceNotFoundException("League not found by ID in DB, cannot update.");
        }
    }

    // delete operation
    public void deleteLeagues(Integer league_id) throws resourceNotFoundException {
        if(leagueRepo.existsById(league_id)) {
            leagueRepo.deleteById(league_id);
        } else {
            throw new resourceNotFoundException("League not found in DB, cannot delete");
        }
    }
    // TODO: ONLY admin should be allowed to delete
    // TODO: How to know if user is currently admin?

    // helper function: checking valid string
    public boolean checkIfInvalid(String string) {
        return string.length() > 128 || string.isBlank();
    }

    // check and sanitize inputs
    private void checkInputs(FantasyLeague fantasyLeague) throws resourceException {
        // NOTE: max league size is hardcoded to 14 for now
        Integer max = 14;
        try {
            if (checkIfInvalid(fantasyLeague.getLeagueName())) {
                throw new resourceException("League name is invalid.");
            } else if (fantasyLeague.getLeagueSize() < 0 && fantasyLeague.getLeagueSize() > max) {
                // TODO: MAKE SURE DIVISIBLE BY 2!
                throw new resourceException("League size is invalid.");
            }
        } catch (NullPointerException e) {
            throw new resourceException("League formatted incorrectly please provide the following:\n" +
                    "league_id, league_name, admin_id, league_size, league_start_date, league_end_date.");
        }
    }

    // check post inputs
    public void checkPostInputs(FantasyLeague fantasyLeague) throws resourceException {
        if (fantasyLeague.getLeagueID() != null) {
            throw new resourceException("Do not provide league_id.");
        }
        checkInputs(fantasyLeague);
    }

    // check put inputs
    public void checkPutInputs(FantasyLeague fantasyLeague) throws resourceException {
        if (fantasyLeague.getLeagueID() != null) {
            throw new resourceException("League formatted incorrectly please provide the following:\n" +
                    "league_name, admin_id, league_size, league_start_date, league_end_date");
        }
        checkInputs(fantasyLeague);
    }

}
