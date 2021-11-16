package FantasyBasketball.repositories;

import FantasyBasketball.models.FantasyStats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
public interface fantasyStatsRepository extends CrudRepository<FantasyStats, Integer> {
    @Query(value = "select * from fantasy_stats where (" +
            "                           (:stats_id is NULL or stats_id = :stats_id) and\n " +
            "                           (:player_id is NULL or player_id = :player_id) and\n" +
            "                           (:schedule_id is NULL or schedule_id = :schedule_id) and\n" +
            "                           (:client_id is NULL or client_id = :client_id) and\n" +
            "                           (:league_id is NULL or league_id = :league_id) and\n" +
            "                           (:threeP is NULL or three_points = :threeP) and\n" +
            "                           (:twoP is NULL or two_points = :twoP) and\n" +
            "                           (:freeThrows is NULL or free_throws = :freeThrows) and\n" +
            "                           (:rebounds is NULL or rebounds = :rebounds) and\n" +
            "                           (:assists is NULL or assists = :assists) and\n" +
            "                           (:blocks is NULL or blocks = :blocks) and\n" +
            "                           (:steals is NULL or steals = :steals) and\n" +
            "                           (:turnovers is NULL or turnovers = :turnovers) and\n" +
            "                           (:totPoints is NULL or tot_points = :totPoints))", nativeQuery = true)
    List<FantasyStats> findByTemplate(
            @Param("stats_id") Integer stats_id,
            @Param("player_id") Integer player_id,
            @Param("schedule_id") Integer schedule_id,
            @Param("client_id") Integer client_id,
            @Param("league_id") Integer league_id,
            @Param("threeP") Integer threeP,
            @Param("twoP") Integer twoP,
            @Param("freeThrows") Integer freeThrows,
            @Param("rebounds") Integer rebounds,
            @Param("assists") Integer assists,
            @Param("blocks") Integer blocks,
            @Param("steals") Integer steals,
            @Param("turnovers") Integer turnovers,
            @Param("totPoints") Integer totPoints);

    @Query(value = "select * from fantasy_stats where ((:player_id is NULL or player_id = :player_id) and\n" +
            "                           (:schedule_id is NULL or schedule_id = :schedule_id) and\n" +
            "                           (:league_id is NULL or league_id = :league_id) and\n" +
        "                               (client_id = :client_id) )", nativeQuery = true)
    List<FantasyStats> findByIDs(@Param("player_id") Integer player_id,
                                 @Param("schedule_id") Integer schedule_id,
                                 @Param("league_id") Integer league_id,
                                 @Param("client_id") Integer client_id);

}
