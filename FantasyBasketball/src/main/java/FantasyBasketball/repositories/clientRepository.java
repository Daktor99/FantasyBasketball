package FantasyBasketball.repositories;

import FantasyBasketball.models.Client;
import FantasyBasketball.models.FantasyGame;
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
public interface clientRepository extends CrudRepository<Client, Integer> {

    @Query(value = "select * from client where ((:client_id is NULL or client_id = :client_id) and\n" +
            "(:email is NULL or email = :email) and\n" +
            "(:company_name is NULL or company_name = :company_name) and\n" +
            "(:client_name is NULL or client_name = :client_name))",
            nativeQuery = true)
    List<Client> findByTemplate(@Param("client_id")            Integer client_id,
                                     @Param("email")                String email,
                                     @Param("company_name")         String company_name,
                                     @Param("client_name")          String client_name);

}
