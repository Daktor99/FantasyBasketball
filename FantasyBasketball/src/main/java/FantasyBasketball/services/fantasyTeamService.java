package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class fantasyTeamService {

    @Autowired
    fantasyLeagueService FantasyLeague;

    @Autowired
    fantasyTeamRepository teamRepo;

    @Autowired
    fantasyLeagueRepository leagueRepo;

    @Autowired
    private fantasyPlayerService playerService;

    // positions for pg & sg
    private static final List<String> guardPositions = Arrays.asList("G", "G-F", "F-G");

    // positions for sf & pf
    private static final List<String> forwardPositions = Arrays.asList("G-F", "F-G", "F", "F-C", "C-F");

    // positions for c
    private static final List<String> centerPositions = Arrays.asList("F-C", "C-F", "C");

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
                                                Integer client_id,
                                                String team_name,
                                                Integer owner_id,
                                                Integer league_id) {
        return teamRepo.findByTemplate(team_id,
                client_id,
                team_name,
                owner_id,
                league_id);
    }

    // helper function to check if the league is full or not yet to apply constraint to posting new team to a league
    public Boolean checkLeagueFull(FantasyBasketball.models.FantasyLeague league) {
        Integer leagueID = league.getLeagueID();
        Integer leagueClientID = league.getClientID();
        Integer leagueSize = league.getLeagueSize();

        List<Integer> teamsInLeague = teamRepo.findTeamsInLeague(leagueID, leagueClientID);
        Integer currentLeagueSize = teamsInLeague.size();

        if (leagueSize > currentLeagueSize) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    // post operation
    public List<FantasyTeam> postTeam(FantasyTeam team) throws resourceException{
        Integer leagueID = team.getLeagueID();
        Optional<FantasyBasketball.models.FantasyLeague> fantasyLeagueOptional = leagueRepo.findById(leagueID);
        FantasyBasketball.models.FantasyLeague league = fantasyLeagueOptional.get();

        if (checkLeagueFull(league)) {
            throw new resourceException("Cannot create another team. This league is already full.");
        };

        team.setTeamID(0);

        // TODO: This client_id will be updated later
        team.setClientID(1);
        FantasyTeam result = teamRepo.save(team);
        return List.of(result);
    }

    // put operation
    public List<FantasyTeam> updateTeam(FantasyTeam team) throws resourceNotFoundException, resourceException {


        if(teamRepo.existsById(team.getTeamID())) {

            // pull team from DB
            FantasyTeam dbTeam = teamRepo.findByTemplate(team.getTeamID(),
                    null, null, null, null).get(0);

            FantasyTeam updatedTeam = updateValues(dbTeam, team);
            //This client_id will be updated later
            team.setClientID(1);

            checkPutInputs(updatedTeam);

            FantasyTeam result = teamRepo.save(updatedTeam);
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

    // Checking correct teamID and Length before posting
    public void checkPostInputs(FantasyTeam team) throws resourceException {
        try {

            // check to make sure teamID has been passed
            if (team.getTeamID() != null) {
                throw new resourceException("Do not provide team_id.");
            }

            String teamName = team.getTeamName();
            if (teamName.length() > 128 || teamName.isBlank()) {
                throw new resourceException("Team name must be between 1-128 characters.");
            }
        } catch (NullPointerException e) {
            throw new resourceException("Post to team formatted incorrectly please provide the following:\n" +
                                        "team_name,\n" +
                                        "owner_id,\n" +
                                        "league_id,\n");
        }
    }

    // Checking correct teamID and Length before putting
    public void checkPutInputs(FantasyTeam team) throws resourceException {
        try {

            // check to make sure user provided a teamID
            if (team.getTeamID() == null) {
                throw new resourceException("You have to provide teamID.");
            }
            String teamName = team.getTeamName();
            if (teamName.length() > 128 || teamName.isBlank()) {
                throw new resourceException("Team name must be between 1-128 characters.");
            }
        } catch (NullPointerException e) {
            throw new resourceException("Team formatted incorrectly please provide at least the following:\n" +
                                        "client_id,\n" +
                                        "team_id,\n" +
                                        "team_name,\n" +
                                        "owner_id,\n" +
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

    private List<Integer> getPlayersOnTeam(Integer teamID) {
        return playerService.getPlayerIDsByTeam(teamID);
    }

    private HashMap<Integer, String> getPlayerPositionMap(Integer teamID) {

        HashMap<Integer, String> playerPositionMap = new HashMap<>();
        List<FantasyPlayer> playerList =
                playerService.getPlayerByTemplate(null,
                        null,
                        teamID,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        for (FantasyPlayer player: playerList) {
            playerPositionMap.put(player.getPlayerID(), player.getPosition());
        }

        return playerPositionMap;
    }

    private void checkOwnerAndLeagueNotUpdated(Integer ownerID, Integer leagueID) throws resourceException {
        if (ownerID != null) {
            throw new resourceException("Cannot reassign team owner once team is created. Please delete team and create a new one.");
        } else if (leagueID != null) {
            throw new resourceException("Cannot reassign team league once team is created. Please delete team and create a new one.");
        }
    }

    private void updatePG(FantasyTeam currentTeam,
                          HashMap<Integer, String> playerPositionMap,
                          List<Integer> teamPlayerList,
                          Integer playerID) throws resourceException {

        // only if request is trying to change
        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = guardPositions.contains(position);

            if (!playerOnTeam) {
                throw new resourceException("Point guard provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new resourceException("This player cannot be assigned point guard because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + guardPositions);
            }

            currentTeam.setStartPG(playerID);
        }
    }

    private void updateSG(FantasyTeam currentTeam,
                          HashMap<Integer, String> playerPositionMap,
                          List<Integer> teamPlayerList,
                          Integer playerID) throws resourceException {

        // only if request is trying to change
        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = guardPositions.contains(position);

            if (!playerOnTeam) {
                throw new resourceException("Shooting guard provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new resourceException("This player cannot be assigned shooting guard because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + guardPositions);
            }

            currentTeam.setStartSG(playerID);
        }
    }

    private void updateSF(FantasyTeam currentTeam,
                          HashMap<Integer, String> playerPositionMap,
                          List<Integer> teamPlayerList,
                          Integer playerID) throws resourceException {

        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = forwardPositions.contains(position);

            if (!playerOnTeam) {
                throw new resourceException("Small forward provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new resourceException("This player cannot be assigned small forward because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + forwardPositions);
            }

            currentTeam.setStartSF(playerID);
        }
    }

    private void updatePF(FantasyTeam currentTeam,
                          HashMap<Integer, String> playerPositionMap,
                          List<Integer> teamPlayerList,
                          Integer playerID) throws resourceException {


        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = forwardPositions.contains(position);

            if (!playerOnTeam) {
                throw new resourceException("Power forward provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new resourceException("This player cannot be assigned power forward because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + forwardPositions);
            }

            currentTeam.setStartPF(playerID);
        }
    }

    private void updateC(FantasyTeam currentTeam,
                         HashMap<Integer, String> playerPositionMap,
                         List<Integer> teamPlayerList,
                         Integer playerID) throws resourceException {

        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = centerPositions.contains(position);

            if (!playerOnTeam) {
                throw new resourceException("Center provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new resourceException("This player cannot be assigned center because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + centerPositions);
            }

            currentTeam.setStartC(playerID);
        }
    }

    private void updateBench1(FantasyTeam currentTeam,
                              List<Integer> teamPlayerList,
                              Integer playerID) throws resourceException {

        if (playerID != null) {

            boolean playerOnTeam = teamPlayerList.contains(playerID);
            if (!playerOnTeam) {
                throw new resourceException("Bench player 1 provided does not belong to this team, check values and try again.");
            }

            currentTeam.setBench1(playerID);
        }
    }

    private void updateBench2(FantasyTeam currentTeam,
                              List<Integer> teamPlayerList,
                              Integer playerID) throws resourceException {

        if (playerID != null) {

            boolean playerOnTeam = teamPlayerList.contains(playerID);
            if (!playerOnTeam) {
                throw new resourceException("Bench player 2 provided does not belong to this team, check values and try again.");
            }

            currentTeam.setBench2(playerID);
        }
    }

    // takes a FantasyPlayer object and updates its values to match updatedTeam
    //      function will be used to help facilitate PUT operations, updating fields where they are not null
    //      checks:
    //          - ownerID and leagueID not reassigned
    //          - non-null values are updated
    //          - checks to make sure starter/bench players actually belong to current team
    //          - does not update wins, losses, or points because client should not change this,
    //                  and should be done automatically by backend server
    public FantasyTeam updateValues(FantasyTeam dbTeam, FantasyTeam updatedTeam) throws resourceException {

        // should not be able to change ownerID or leagueID after team has been created
        //      if ownerID exists, raise exception
        Integer ownerID = updatedTeam.getOwnerID();
        Integer leagueID = updatedTeam.getLeagueID();
        checkOwnerAndLeagueNotUpdated(ownerID, leagueID);

        // find all players that are drafted to this team
        Integer teamID = updatedTeam.getTeamID();
        List<Integer> teamPlayerIDs = getPlayersOnTeam(teamID);
        HashMap<Integer, String> playerPositionMap = getPlayerPositionMap(teamID);

        // should not update teamID, skip it

        // if team name is not null, update it
        String teamName = updatedTeam.getTeamName();
        if (teamName != null) {
            dbTeam.setTeamName(teamName);
        }

        // get values for players passed in
        Integer start_pg = updatedTeam.getStartPG();
        Integer start_sg = updatedTeam.getStartSG();
        Integer start_sf = updatedTeam.getStartSF();
        Integer start_pf = updatedTeam.getStartPF();
        Integer start_c = updatedTeam.getStartC();
        Integer bench_1 = updatedTeam.getBench1();
        Integer bench_2 = updatedTeam.getBench2();

        // for each player, only assign to current team
        //          if position is correct and player is on correct team
        updatePG(dbTeam, playerPositionMap, teamPlayerIDs, start_pg);
        updateSG(dbTeam, playerPositionMap, teamPlayerIDs, start_sg);
        updateSF(dbTeam, playerPositionMap, teamPlayerIDs, start_sf);
        updatePF(dbTeam, playerPositionMap, teamPlayerIDs, start_pf);
        updateC(dbTeam, playerPositionMap, teamPlayerIDs, start_c);
        updateBench1(dbTeam, teamPlayerIDs, bench_1);
        updateBench2(dbTeam, teamPlayerIDs, bench_2);

        // check if there are any duplicate players in the starting lineup
        // create a set to make sure there are no duplicate players
        Set<Integer> playerSet = new HashSet<>(Arrays.asList(start_pg, start_sg, start_sf, start_pf, start_c, bench_1, bench_2));
        boolean hasDuplicatePlayers = (playerSet.size() !=  7);

        if (hasDuplicatePlayers) {
            throw new resourceException("Making this assignment will create duplicate players in the lineup, check values and try again.");
        }


        return dbTeam;
    }
}
