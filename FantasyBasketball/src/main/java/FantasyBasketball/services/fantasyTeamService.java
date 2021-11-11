package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.repositories.fantasyTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class fantasyTeamService {

    @Autowired
    private fantasyTeamRepository teamRepo;

    // find by ID
    public List<FantasyTeam> getByID(Integer teamID) throws resourceNotFoundException {
        Optional<FantasyTeam> result = teamRepo.findById(teamID);
        if (result.isPresent()) {
            FantasyTeam teamResult = result.get();
            return List.of(teamResult);
        }
        else {
            throw new resourceNotFoundException("Team not found by ID in DB, cannot update");
        }
    }

    // get operation
    public List<FantasyTeam> getTeamsByTemplate(Integer team_id,
                                                String team_name,
                                                Integer owner_id,
                                                Integer league_id) {
        return teamRepo.findByTemplate(team_id,
                team_name,
                owner_id,
                league_id);
    }

    // post operation
    public List<FantasyTeam> postTeam(FantasyTeam team) {
        team.setTeamID(0);
        FantasyTeam result = teamRepo.save(team);
        return List.of(result);
    }

    // put operation
    public List<FantasyTeam> updateTeam(FantasyTeam team) throws resourceNotFoundException {
        if(teamRepo.existsById(team.getTeamID())) {
            FantasyTeam result = teamRepo.save(team);
            return List.of(result);
        } else {
            throw new resourceNotFoundException("Team not found by ID in DB, cannot update");
        }
    }

    // delete operation
    public void deleteTeamById(Integer team_id) throws resourceNotFoundException {
        if(teamRepo.existsById(team_id)) {
            teamRepo.deleteById(team_id);
        } else {
            throw new resourceNotFoundException("User not found in DB, cannot delete");
        }
    }

    public void checkPostInputs(FantasyTeam team) throws resourceException {
        try {
            if (team.getTeamID() != null) {
                throw new resourceException("Do not provide team_id.");
            }
            String teamName = team.getTeamName();
            if (teamName.length() > 128 || teamName.isBlank()) {
                throw new resourceException("Team name must be between 1-128 characters.");
            }
        } catch (NullPointerException e) {
            throw new resourceException("Team formatted incorrectly please provide at least the following:\n" +
                                        "team_id,\n" +
                                        "team_name,\n" +
                                        "owner_id,\n" +
                                        "Optional parameters:\n" +
                                        "league_id,\n" +
                                        "start_pg_id,\n" +
                                        "start_sg_id,\n" +
                                        "start_sf_id,\n" +
                                        "start_pf_id,\n" +
                                        "start_c_id,\n" +
                                        "bench_1_id,\n" +
                                        "bench_2_id,\n" +
                                        "team_wins,\n" +
                                        "team_losses,\n" +
                                        "points_scored,\n" +
                                        "points_against");
        }
    }

    public void checkPutInputs(FantasyTeam team) throws resourceException {
        try {
            if (team.getTeamID() == null) {
                throw new resourceException("You have to provide teamID. " +
                                            "Team formatted incorrectly please provide at least the following:\n" +
                                            "team_id,\n" +
                                            "team_name,\n" +
                                            "owner_id,\n" +
                                            "Optional parameters:\n" +
                                            "league_id,\n" +
                                            "start_pg_id,\n" +
                                            "start_sg_id,\n" +
                                            "start_sf_id,\n" +
                                            "start_pf_id,\n" +
                                            "start_c_id,\n" +
                                            "bench_1_id,\n" +
                                            "bench_2_id,\n" +
                                            "team_wins,\n" +
                                            "team_losses,\n" +
                                            "points_scored,\n" +
                                            "points_against");
            }
            String teamName = team.getTeamName();
            if (teamName.length() > 128 || teamName.isBlank()) {
                throw new resourceException("Team name must be between 1-128 characters.");
            }
        } catch (NullPointerException e) {
            throw new resourceException("Team formatted incorrectly please provide at least the following:\n" +
                                        "team_id,\n" +
                                        "team_name,\n" +
                                        "owner_id,\n" +
                                        "Optional parameters:\n" +
                                        "league_id,\n" +
                                        "start_pg_id,\n" +
                                        "start_sg_id,\n" +
                                        "start_sf_id,\n" +
                                        "start_pf_id,\n" +
                                        "start_c_id,\n" +
                                        "bench_1_id,\n" +
                                        "bench_2_id,\n" +
                                        "team_wins,\n" +
                                        "team_losses,\n" +
                                        "points_scored,\n" +
                                        "points_against");
        }
    }
}
