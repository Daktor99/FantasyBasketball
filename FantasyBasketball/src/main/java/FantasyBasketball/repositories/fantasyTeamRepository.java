package FantasyBasketball.repositories;

import FantasyBasketball.models.FantasyTeam;
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
public interface fantasyTeamRepository extends CrudRepository<FantasyTeam, Integer> {

    @Query(value = "select * from fantasy_team where ((:team_id is NULL or team_id = :team_id) and\n" +
            "                          (:client_id is NULL or client_id = :client_id) and\n" +
            "                          (:team_name is NULL or team_name LIKE :team_name) and\n" +
            "                          (:owner_id is NULL or owner_id = :owner_id) and\n" +
            "                          (:league_id is NULL or league_id = :league_id))",
            nativeQuery = true)
    List<FantasyTeam> findByTemplate(@Param("team_id") Integer team_id,
                                     @Param("client_id") Integer client_id,
                                     @Param("team_name") String team_name,
                                     @Param("owner_id") Integer owner_id,
                                     @Param("league_id") Integer league_id);

    @Query(value = "select team_id from fantasy_team where league_id = :league_id and client_id = :client_id", nativeQuery = true)
    List<Integer> findTeamsInLeague(@Param("league_id") Integer league_id,
                                    @Param("client_id") Integer client_id);

}
