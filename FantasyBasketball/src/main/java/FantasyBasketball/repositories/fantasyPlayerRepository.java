package FantasyBasketball.repositories;

import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/*
    CrudRepository already has the following functions:
        (in the form): function_name(parameter:parameter_type):return_type
        save(entity:S):S
        saveAll(entities:Iterable<S>):Iterable<S>
        findById(id:ID):Optional<T>
        existsById(id:ID):boolean
        findAll():Iterable<T>
        findAllById(ids:Iterable<ID>)Iterable<T>
        count():long
        deleteById(idLID):void
        delete(entity:T):void
        deleteAllById(ids:Iterable<? extends ID>):void
        deleteAll(entities:Iterable<? extends T>):void
        deleteAll():void

    All functions below extend the CrudRepository
 */

@Repository
public interface fantasyPlayerRepository extends CrudRepository<FantasyPlayer, Integer> {

    @Query(value = "select player_id from fantasy_player where " +
            "((client_id = :client_id) and\n" +
            "(league_id = :league_id) and\n" +
            "(team_id is NULL))", nativeQuery = true)
    List<Integer> getUndraftedPlayers(@Param("client_id") Integer client_id,
                                      @Param("league_id") Integer league_id);

    @Query(value = "select player_id from (select fantasy_player.player_id as player_id,\n" +
            "                        fantasy_player.client_id as client_id,\n" +
            "                        fantasy_player.league_id as league_id,\n" +
            "                        fantasy_player.team_id as team_id,\n" +
            "                        player_data.first_name as first_name,\n" +
            "                        player_data.last_name as last_name,\n" +
            "                        player_data.position as position,\n" +
            "                        player_data.nba_team as nba_team,\n" +
            "                        fantasy_player.ball_api_id as ball_api_id\n" +
            "                    from fantasy_player, player_data where fantasy_player.player_id = player_data.player_id) as a\n" +
                        "where team_id = :team_id", nativeQuery = true)
    List<Integer> getByTeamID(@Param("team_id") Integer team_id);

    @Query(value = "select * from (select fantasy_player.player_id as player_id,\n" +
                        "fantasy_player.client_id as client_id,\n" +
                        "fantasy_player.league_id as league_id,\n" +
                        "fantasy_player.team_id as team_id,\n" +
                        "player_data.first_name as first_name,\n" +
                        "player_data.last_name as last_name,\n" +
                        "player_data.position as position,\n" +
                        "player_data.nba_team as nba_team,\n" +
                        "fantasy_player.ball_api_id as ball_api_id\n" +
                    "from fantasy_player, player_data where fantasy_player.player_id = player_data.player_id) as a\n" +
                    "where \n" +
                        "((client_id = :client_id) and\n" +
                        "(league_id = :league_id) and\n" +
                        "(team_id is NULL) and\n" +
                        "(:first_name is NULL or first_name LIKE %:first_name%) and\n" +
                        "(:last_name is NULL or last_name LIKE %:last_name%) and\n" +
                        "(:nba_team is NULL or nba_team LIKE %:nba_team%) and\n" +
                        "(:position is NULL or position LIKE %:position%))", nativeQuery = true)
    List<FantasyPlayer> getAvailablePlayers(@Param("league_id") Integer league_id,
                                            @Param("client_id") Integer client_id,
                                            @Param("first_name") String first_name,
                                            @Param("last_name") String last_name,
                                            @Param("nba_team") String nba_team,
                                            @Param("position") String position);

    @Query(value = "select * from (select fantasy_player.player_id as player_id,\n" +
            "       fantasy_player.client_id as client_id,\n" +
            "       fantasy_player.league_id as league_id,\n" +
            "       fantasy_player.team_id as team_id,\n" +
            "       player_data.first_name as first_name,\n" +
            "       player_data.last_name as last_name,\n" +
            "       player_data.position as position,\n" +
            "       player_data.nba_team as nba_team,\n" +
            "       fantasy_player.ball_api_id as ball_api_id\n" +
            "       from fantasy_player, player_data where fantasy_player.player_id = player_data.player_id) as a\n" +
            "       where \n" +
            "               ((:player_id is NULL or player_id = :player_id) and\n" +
            "               (:client_id is NULL or client_id = :client_id) and\n" +
            "               (:league_id is NULL or league_id = :league_id) and\n" +
            "               (:team_id is NULL or team_id = :team_id) and\n" +
            "               (:first_name is NULL or first_name LIKE %:first_name%) and\n" +
            "               (:last_name is NULL or last_name LIKE %:last_name%) and\n" +
            "               (:nba_team is NULL or nba_team LIKE %:nba_team%) and\n" +
            "               (:position is NULL or position LIKE %:position%) and\n" +
            "               (:ball_api_id is NULL or ball_api_id=:ball_api_id))", nativeQuery = true)
    List<FantasyPlayer> findByTemplate(@Param("player_id") Integer player_id,
                                       @Param("client_id") Integer client_id,
                                       @Param("team_id") Integer team_id,
                                       @Param("league_id") Integer league_id,
                                       @Param("ball_api_id") Integer ball_api_id,
                                       @Param("first_name") String first_name,
                                       @Param("last_name") String last_name,
                                       @Param("nba_team") String nba_team,
                                       @Param("position") String position);

    @Transactional
    @Modifying
    @Query(value = "insert into fantasy_player values(:player_id, :ball_api_id, :team_id, :league_id, :client_id)",
            nativeQuery = true)
    void insertFantasyPlayer(@Param("player_id") Integer player_id,
                                            @Param("client_id") Integer client_id,
                                            @Param("team_id") Integer team_id,
                                            @Param("league_id") Integer league_id,
                                            @Param("ball_api_id") Integer ball_api_id);

    @Transactional
    @Modifying
    @Query(value = "update fantasy_player set team_id = :team_id where player_id = :player_id and league_id = :league_id and client_id = :client_id",
            nativeQuery = true)
    void updateFantasyPlayer(@Param("player_id") Integer player_id,
                             @Param("client_id") Integer client_id,
                             @Param("league_id") Integer league_id,
                             @Param("team_id") Integer team_id);

    @Transactional
    @Modifying
    @Query(value = "delete from fantasy_player \n" +
            "where \n" +
            "((player_id = :player_id) and\n" +
            "(client_id = :client_id) and\n" +
            "(league_id = :league_id))",
            nativeQuery = true)
    void deleteFantasyPlayer(@Param("player_id") Integer player_id,
                             @Param("client_id") Integer client_id,
                             @Param("league_id") Integer league_id);

}
