package FantasyBasketball.services;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FantasyTeamService {

    @Autowired
    FantasyLeagueService FantasyLeague;

    @Autowired
    fantasyTeamRepository teamRepo;

    @Autowired
    fantasyLeagueRepository leagueRepo;

    @Autowired
    FantasyPlayerService playerService;

    // positions for pg & sg
    static final List<String> guardPositions = Arrays.asList("G", "G-F", "F-G");

    // positions for sf & pf
    static final List<String> forwardPositions = Arrays.asList("G-F", "F-G", "F", "F-C", "C-F");

    // positions for c
    static final List<String> centerPositions = Arrays.asList("F-C", "C-F", "C");

    // find by ID
    public List<FantasyTeam> getByID(Integer teamID) throws ResourceNotFoundException {
        Optional<FantasyTeam> result = teamRepo.findById(teamID);
        if (result.isPresent()) {
            FantasyTeam teamResult = result.get();
            return List.of(teamResult);
        }
        else {
            throw new ResourceNotFoundException("Team not found by ID in DB, cannot update");
        }
    }

    // get operation
    public List<FantasyTeam> getTeamsByTemplate(Integer team_id,
                                                Integer client_id,
                                                String team_name,
                                                Integer owner_id,
                                                Integer league_id) {
        List<FantasyTeam> teamsList =  teamRepo.findByTemplate(team_id,
                client_id,
                team_name,
                owner_id,
                league_id);
        return teamsList;
    }

    // helper function to check if the league is full or not yet to apply constraint to posting new team to a league
    public Boolean checkLeagueFull(FantasyLeague league) {
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
    public List<FantasyTeam> postTeam(FantasyTeam team) throws ResourceException {
        Integer leagueID = team.getLeagueID();
        Optional<FantasyBasketball.models.FantasyLeague> fantasyLeagueOptional = leagueRepo.findById(leagueID);
        if (fantasyLeagueOptional.isPresent()) {
            FantasyBasketball.models.FantasyLeague league = fantasyLeagueOptional.get();
            if (checkLeagueFull(league)) {
                throw new ResourceException("Cannot create another team. This league is already full.");
            }
        } else {
            throw new ResourceException("This league does not exist");
        }

        team.setTeamID(0);

        FantasyTeam result = teamRepo.save(team);
        List<FantasyTeam> resultList = Arrays.asList(result);
        return resultList;
    }

    public List<FantasyTeam> postMultipleTeams(List<FantasyTeam> teams) {
        Iterable<FantasyTeam> teamsIter = teamRepo.saveAll(teams);
        List<FantasyTeam> teamsList = (List<FantasyTeam>) teamsIter;
        return teamsList;
    }

    // put operation
    public List<FantasyTeam> updateTeam(FantasyTeam team) throws ResourceNotFoundException, ResourceException {

        Integer team_id = team.getTeamID();
        if (team_id == null) {
            throw new ResourceException("team_id is required.");
        }

        if(teamRepo.existsById(team.getTeamID())) {

            // pull team from DB
            List<FantasyTeam> dblist = teamRepo.findByTemplate(team.getTeamID(), team.getClientID(),
                    null, null, null);
            if (dblist.size() == 0) {
                throw new ResourceNotFoundException("You do not have access to this team and cannot make changes.");
            }

            FantasyTeam updatedTeam = updateValues(dblist.get(0), team);

            checkPutInputs(updatedTeam);

            return List.of(teamRepo.save(updatedTeam));
        } else {
            throw new ResourceNotFoundException("Team not found by ID in DB, cannot update");
        }
    }

    // delete operation
    public void deleteTeamById(Integer team_id) throws ResourceNotFoundException {
        Boolean exists = teamRepo.existsById(team_id);
        if(exists) {
            teamRepo.deleteById(team_id);
        } else {
            throw new ResourceNotFoundException("Team not found in DB, cannot delete");
        }
    }

    // Checking correct teamID and Length before posting
    public void checkPostInputs(FantasyTeam team) throws ResourceException {
        Integer teamID = team.getTeamID();
        // check to make sure teamID has been passed
        if (teamID != null) {
            throw new ResourceException("Do not provide team_id.");
        }

        String teamName = team.getTeamName();
        Boolean length = Boolean.FALSE;
        if (teamName.length() > 128)
            length = Boolean.TRUE;
        if (teamName.isBlank())
            length = Boolean.TRUE;

        if (length) {
            throw new ResourceException("Team name must be between 1-128 characters.");
        }
        Boolean owner = Boolean.FALSE;
        if (team.getOwnerID() == null)
            owner = Boolean.TRUE;
        if (owner) {
            throw new ResourceException("Please provide owner_id.");
        }
        Boolean league = Boolean.FALSE;
        if (team.getLeagueID() == null)
            league = Boolean.TRUE;
        if (league) {
            throw new ResourceException("Please provide league_id corresponding to team.");
        }

        // set numerical values to 0, to make sure values are not tampered with
        team.setTeamWins(0);
        team.setTeamLosses(0);
        team.setPointsScored(0);
        team.setPointsAgainst(0);

    }

    // Checking correct teamID and Length before putting
    public void checkPutInputs(FantasyTeam team) throws ResourceException {
        // check to make sure user provided a teamID
        if (team.getTeamID() == null) {
            throw new ResourceException("You have to provide teamID.");
        }
        String teamName = team.getTeamName();
        if (teamName.length() > 128 || teamName.isBlank()) {
            throw new ResourceException("Team name must be between 1-128 characters.");
        }
    }

    public List<Integer> getPlayersOnTeam(Integer teamID) {

        return playerService.getPlayerIDsByTeam(teamID);
    }

    public HashMap<Integer, String> getPlayerPositionMap(Integer teamID) {

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

    public void checkOwnerAndLeagueNotUpdated(Integer ownerID, Integer leagueID) throws ResourceException {
        if (ownerID != null) {
            throw new ResourceException("Cannot reassign team owner once team is created. Please delete team and create a new one.");
        } else if (leagueID != null) {
            throw new ResourceException("Cannot reassign team league once team is created. Please delete team and create a new one.");
        }
    }

    public void updatePG(FantasyTeam currentTeam,
                          HashMap<Integer, String> playerPositionMap,
                          List<Integer> teamPlayerList,
                          Integer playerID) throws ResourceException {

        // only if request is trying to change
        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = guardPositions.contains(position);

            if (!playerOnTeam) {
                throw new ResourceException("Point guard provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new ResourceException("This player cannot be assigned point guard because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + guardPositions);
            }

            currentTeam.setStartPG(playerID);
        }
    }

    public void updateSG(FantasyTeam currentTeam,
                          HashMap<Integer, String> playerPositionMap,
                          List<Integer> teamPlayerList,
                          Integer playerID) throws ResourceException {

        // only if request is trying to change
        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = guardPositions.contains(position);

            if (!playerOnTeam) {
                throw new ResourceException("Shooting guard provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new ResourceException("This player cannot be assigned shooting guard because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + guardPositions);
            }

            currentTeam.setStartSG(playerID);
        }
    }

    public void updateSF(FantasyTeam currentTeam,
                          HashMap<Integer, String> playerPositionMap,
                          List<Integer> teamPlayerList,
                          Integer playerID) throws ResourceException {

        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = forwardPositions.contains(position);

            if (!playerOnTeam) {
                throw new ResourceException("Small forward provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new ResourceException("This player cannot be assigned small forward because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + forwardPositions);
            }

            currentTeam.setStartSF(playerID);
        }

    }

    public void updatePF(FantasyTeam currentTeam,
                          HashMap<Integer, String> playerPositionMap,
                          List<Integer> teamPlayerList,
                          Integer playerID) throws ResourceException {


        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = forwardPositions.contains(position);

            if (!playerOnTeam) {
                throw new ResourceException("Power forward provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new ResourceException("This player cannot be assigned power forward because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + forwardPositions);
            }

            currentTeam.setStartPF(playerID);
        }
    }

    public void updateC(FantasyTeam currentTeam,
                         HashMap<Integer, String> playerPositionMap,
                         List<Integer> teamPlayerList,
                         Integer playerID) throws ResourceException {

        if (playerID != null) {

            String position = playerPositionMap.get(playerID);
            boolean playerOnTeam = teamPlayerList.contains(playerID);
            boolean correctPosition = centerPositions.contains(position);

            if (!playerOnTeam) {
                throw new ResourceException("Center provided does not belong to this team, check values and try again.");
            } else if (!correctPosition) {
                throw new ResourceException("This player cannot be assigned center because of their position. \n"
                        + "Please provide a player with one of the following positions: "
                        + centerPositions);
            }

            currentTeam.setStartC(playerID);
        }
    }

    public void updateBench1(FantasyTeam currentTeam,
                              List<Integer> teamPlayerList,
                              Integer playerID) throws ResourceException {

        if (playerID != null) {

            boolean playerOnTeam = teamPlayerList.contains(playerID);
            if (!playerOnTeam) {
                throw new ResourceException("Bench player 1 provided does not belong to this team, check values and try again.");
            }

            currentTeam.setBench1(playerID);
        }
    }

    public void updateBench2(FantasyTeam currentTeam,
                              List<Integer> teamPlayerList,
                              Integer playerID) throws ResourceException {

        if (playerID != null) {

            boolean playerOnTeam = teamPlayerList.contains(playerID);
            if (!playerOnTeam) {
                throw new ResourceException("Bench player 2 provided does not belong to this team, check values and try again.");
            }

            currentTeam.setBench2(playerID);
        }
    }

    public boolean checkDuplicatePlayers(List<Integer> playerList) {

        HashMap<Integer, Integer> playerMap = new HashMap<>();
        for(Integer id: playerList) {

            if (playerMap.containsKey(id)) {
                return true;
            } else if (id != null) {
                playerMap.put(id, 1);
            }

        }

        return false;
    }

    // takes a FantasyPlayer object and updates its values to match updatedTeam
    //      function will be used to help facilitate PUT operations, updating fields where they are not null
    //      checks:
    //          - ownerID and leagueID not reassigned
    //          - non-null values are updated
    //          - checks to make sure starter/bench players actually belong to current team
    //          - does not update wins, losses, or points because client should not change this,
    //                  and should be done automatically by backend server
    public FantasyTeam updateValues(FantasyTeam dbTeam, FantasyTeam updatedTeam) throws ResourceException {

        // should not be able to change ownerID or leagueID after team has been created
        //      if ownerID exists, raise exception
        checkOwnerAndLeagueNotUpdated(updatedTeam.getOwnerID(), updatedTeam.getLeagueID());

        // find all players that are drafted to this team
        List<Integer> teamPlayerIDs = getPlayersOnTeam(updatedTeam.getTeamID());
        HashMap<Integer, String> playerPositionMap = getPlayerPositionMap(updatedTeam.getTeamID());

        // should not update teamID, skip it

        // if team name is not null, update it
        if (updatedTeam.getTeamName() != null) {
            dbTeam.setTeamName(updatedTeam.getTeamName());
        }

        // for each player, only assign to current team
        //          if position is correct and player is on correct team
        updatePG(dbTeam, playerPositionMap, teamPlayerIDs, updatedTeam.getStartPG());
        updateSG(dbTeam, playerPositionMap, teamPlayerIDs, updatedTeam.getStartSG());
        updateSF(dbTeam, playerPositionMap, teamPlayerIDs, updatedTeam.getStartSF());
        updatePF(dbTeam, playerPositionMap, teamPlayerIDs, updatedTeam.getStartPF());
        updateC(dbTeam, playerPositionMap, teamPlayerIDs, updatedTeam.getStartC());
        updateBench1(dbTeam, teamPlayerIDs, updatedTeam.getBench1());
        updateBench2(dbTeam, teamPlayerIDs, updatedTeam.getBench2());

        // check if there are any duplicate players in the starting lineup
        // create a set to make sure there are no duplicate players

        List<Integer> playerList = Arrays.asList(dbTeam.getStartPG(),
                dbTeam.getStartSG(),
                dbTeam.getStartSF(),
                dbTeam.getStartPF(),
                dbTeam.getStartC(),
                dbTeam.getBench1(),
                dbTeam.getBench2());

        if (checkDuplicatePlayers(playerList)) {
            throw new ResourceException("Making this assignment will create duplicate players in the lineup, check values and try again.");
        }

        return dbTeam;
    }
}
