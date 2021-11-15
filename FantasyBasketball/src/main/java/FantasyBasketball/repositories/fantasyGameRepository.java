package FantasyBasketball.repositories;

import FantasyBasketball.models.FantasyGame;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
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
public interface fantasyGameRepository extends CrudRepository<FantasyGame, Integer> {

    @Query(value = "select * from fantasy_game where ((:schedule_id is NULL or schedule_id = :schedule_id) and\n" +
                                                "(:league_id is NULL or league_id = :league_id) and\n" +
                                                "(:client_id is NULL or client_id = :client_id) and\n" +
                                                "(:home_team_id is NULL or home_team_id = :home_team_id) and\n" +
                                                "(:away_team_id is NULL or away_team_id = :away_team_id) and\n" +
                                                "(:game_start_date is NULL or game_start_date = :game_start_date) and\n" +
                                                "(:game_end_date is NULL or game_end_date = :game_end_date) and\n" +
                                                "(:winner_id is NULL or winner_id = :winner_id))",
                                                nativeQuery = true)
    List<FantasyGame> findByTemplate(@Param("schedule_id")             Integer schedule_id,
                                     @Param("league_id")               Integer league_id,
                                     @Param("client_id")               Integer client_id,
                                     @Param("home_team_id")            Integer home_team_id,
                                     @Param("away_team_id")            Integer away_team_id,
                                     @Param("game_start_date")         LocalDate game_start_date,
                                     @Param("game_end_date")           LocalDate game_end_date,
                                     @Param("winner_id")               Integer winner_id);

    @Query(value = "select * from fantasy_game where :current_date between game_start_date and game_end_date",
                            nativeQuery = true)
    List<FantasyGame> findGamesGivenDate(@Param("current_date") LocalDate current_date);
}
