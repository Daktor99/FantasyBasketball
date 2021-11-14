package FantasyBasketball.repositories;

import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.User;
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
public interface fantasyPlayerRepository extends CrudRepository<FantasyPlayer, Integer> {
    @Query(value = "select * from fantasy_player where ((:player_id is NULL or player_id = :player_id) and\n" +
            "                          (:team_id is NULL or team_id = :team_id) and\n" +
            "                          (:client_id is NULL or client_id = :client_id) and\n" +
            "                          (:position is NULL or position LIKE %:position%) and\n" +
            "                          (:first_name is NULL or first_name LIKE %:first_name%) and\n" +
            "                          (:last_name is NULL or last_name LIKE %:last_name%) and\n" +
            "                          (:nba_team is NULL or nba_team LIKE %:nba_team% ) and\n" +
            "                          (:league_id is NULL or league_id = :league_id))", nativeQuery = true)
    List<FantasyPlayer> findByTemplate(@Param("player_id") Integer player_id,
                                       @Param("client_id") Integer client_id,
                                       @Param("team_id") Integer team_id,
                                       @Param("position") String position,
                                       @Param("first_name") String first_name,
                                       @Param("last_name") String last_name,
                                       @Param("nba_team") String nba_team,
                                       @Param("league_id") Integer league_id);
}
