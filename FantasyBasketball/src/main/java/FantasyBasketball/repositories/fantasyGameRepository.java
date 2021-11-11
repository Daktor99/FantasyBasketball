package FantasyBasketball.repositories;

import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query(value = "select * from user where ((:schedule_id is NULL or schedule_id = :schedule_id) and\n" +
                                            "(:league_id is NULL or league_id = :league_id) and\n" +
                                            "(:home_team_id is NULL or home_team_id = :home_team_id) and\n" +
                                            "(:away_team_id is NULL or away_team_id = :away_team_id) and\n" +
                                            "(:game_start_date is NULL or game_start_date = :game_start_date))" +
                                            "(:game_end_date is NULL or game_end_date = :game_end_date) and\n" +
                                            "(:winner_id is NULL or winner_id = :winner_id) and\n" +
                                            "(:home_points is NULL or home_points = :home_points) and\n" +
                                            "(:away_points is NULL or away_points = :away_points) and\n" +
                                            "(:home_start_pg_id is NULL or home_start_pg_id = :home_start_pg_id) and\n" +
                                            "(:home_start_sg_id is NULL or home_start_sg_id = :home_start_sg_id) and\n" +
                                            "(:home_start_sf_id is NULL or home_start_sf_id = :home_start_sf_id) and\n" +
                                            "(:home_start_pf_id is NULL or home_start_pf_id = :home_start_pf_id) and\n" +
                                            "(:home_start_c_id is NULL or home_start_c_id = :home_start_c_id) and\n" +
                                            "(:home_bench_1_id is NULL or home_bench_1_id = :home_bench_1_id) and\n" +
                                            "(:home_bench_2_id is NULL or home_bench_2_id = :home_bench_2_id) and\n" +
                                            "(:away_start_pg_id is NULL or away_start_pg_id = :away_start_pg_id) and\n" +
                                            "(:away_start_sg_id is NULL or away_start_sg_id = :away_start_sg_id) and\n" +
                                            "(:away_start_sf_id is NULL or away_start_sf_id = :away_start_sf_id) and\n" +
                                            "(:away_start_pf_id is NULL or away_start_pf_id = :away_start_pf_id) and\n" +
                                            "(:away_start_c_id is NULL or away_start_c_id = :away_start_c_id) and\n" +
                                            "(:away_bench_1_id is NULL or away_bench_1_id = :away_bench_1_id) and\n" +
                                            "(:away_bench_2_id is NULL or away_bench_2_id = :away_bench_2_id) and\n",
                                            nativeQuery = true)
    List<User> findByTemplate(@Param("schedule_id")      Integer schedule_id,
                              @Param("league_id")        Integer league_id,
                              @Param("home_team_id")     Integer home_team_id,
                              @Param("away_team_id")     Integer away_team_id,
                              @Param("game_start_date")  Date game_start_date,
                              @Param("game_end_date")    Date game_end_date,
                              @Param("winner_id")        Integer winner_id,
                              @Param("home_points")      Integer home_points,
                              @Param("away_points")      Integer away_points,
                              @Param("home_start_pg_id") Integer home_start_pg_id,
                              @Param("home_start_sg_id") Integer home_start_sg_id,
                              @Param("home_start_sf_id") Integer home_start_sf_id,
                              @Param("home_start_pf_id") Integer home_start_pf_id,
                              @Param("home_start_c_id")  Integer home_start_c_id,
                              @Param("home_bench_1_id")  Integer home_bench_1_id,
                              @Param("home_bench_2_id")  Integer home_bench_2_id,
                              @Param("away_start_pg_id") Integer away_start_pg_id,
                              @Param("away_start_sg_id") Integer away_start_sg_id,
                              @Param("away_start_sf_id") Integer away_start_sf_id,
                              @Param("away_start_pf_id") Integer away_start_pf_id,
                              @Param("away_start_c_id")  Integer away_start_c_id,
                              @Param("away_bench_1_id")  Integer away_bench_1_id,
                              @Param("away_bench_2_id")  Integer away_bench_2_id);
}
