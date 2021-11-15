package FantasyBasketball.repositories;

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
        findById(id:T):Optional<T>
        existsById(id:ID):boolean
        findAll():Iterable<T>
        findAllById(ids:Iterable<ID>)Iterable<T>
        count():long
        deleteById(id:ID):void
        delete(entity:T):void
        deleteAllById(ids:Iterable<? extends ID>):void
        deleteAll(entities:Iterable<? extends T>):void
        deleteAll():void

    All functions below extend the CrudRepository
 */

@Repository
public interface userRepository extends CrudRepository<User, Integer> {

    @Query(value = "select * from user where ((:user_id is NULL or user_id = :user_id) and\n" +
            "                          (:client_id is NULL or client_id = :client_id) and\n" +
            "                          (:email is NULL or email = :email) and\n" +
            "                          (:username is NULL or username LIKE %:username%) and\n" +
            "                          (:first_name is NULL or first_name LIKE %:first_name%) and\n" +
            "                          (:last_name is NULL or last_name LIKE %:last_name%))", nativeQuery = true)
    List<User> findByTemplate(@Param("user_id") Integer user_id,
                              @Param("client_id") Integer client_id,
                              @Param("email") String email,
                              @Param("username") String username,
                              @Param("first_name") String first_name,
                              @Param("last_name") String last_name);
}
