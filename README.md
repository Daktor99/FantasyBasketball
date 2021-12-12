# ASWE_TeamProject
# (Top section is from our first iteration. Please scroll down to see README contents for our second iteration.)

## 1. Documented API
   * ___Users___
      * `GET /users`
         * Description: This endpoint retrieves all users that match the template given in the query parameters. No parameters are required.
         * Sample Request: localhost:8080/users?user_id=1
         * Request Parameters:
            * `user_id` (Type: Integer)
            * `email` (Type: String)
            * `username` (Type: String)
            * `first_name` (Type: String)
            * `last_name` (Type: String)
      * `POST /users`
         * Description: This endpoint receives the details of a user to newly create. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * `{ "client_id": 1, "username": "fake", "email": "fake@gmail.com", "first_name": "fakefirstname", "last_name": "fakelastname" }`
      * `PUT /users`
         * Description: This endpoint receives the details of a user to update. The user must be provided as a JSON, sent in the body of the PUT request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * `{ "user_id": 100, "client_id": 1, "username": "fake", "email": "fake@gmail.com", "first_name": "fakefirstname", "last_name": "fakelastname" }`
      * `DELETE /users`
         * Description: This endpoint receives the user_id of a user to delete. The user_id must be provided as a query parameter.
         * Sample Request: localhost:8080/users?user_id=1
         * Request Parameters:
            * `user_id` (Type: Integer)
   * ___FantasyTeams___
      * `GET /fantasyTeams`
         * Description: This endpoint retrieves all teams that match the template given in the query parameters. No parameters are required.
         * Sample Request: localhost:8080/fantasyTeams?team_id=1
         * Request Parameters:
            * `team_id` (Type: Integer)
            * `team_name` (Type: String)
            * `owner_id` (Type: Integer)
            * `league_id` (Type: Integer)
      * `POST /fantasyTeams`
         * Description: This endpoint receives the details of a team to newly create. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * `{ "team_name: "fake", "owner_id": 4, "league_id": 1 }`
      * `PUT /fantasyTeams`
         * Description: This endpoint receives the details of a team to update. The user must be provided as a JSON, sent in the body of the PUT request. The JSON in the request body must provide the team_id, and all other fields are optional.
         * Sample Request Body:
            * `team_id` (Type: Integer)
            * `team_name` (Type: String)
            * `owner_id` (Type: Integer)
            * `league_id` (Type: Integer)
      * `DELETE /fantasyTeams`
         * Description: This endpoint receives the team_id of a team to delete. The team_id must be provided as a query parameter.
         * Sample Request: localhost:8080/fantasyTeams?team_id=11
         * Request Parameters:
            * `team_id` (Type: Integer)
   * ___FantasyStats___
      * `GET /fantasyStats`
         * Description: This endpoint retrieves all stats that match the template given in the query parameters. No parameters are required.
         * Sample Request: `localhost:8080/fantasyStats?stats_id=1`
         * Request Parameters:
            * `stats_id` (Type: Integer)
            * `player_id` (Type: Integer)
            * `schedule_id` (Type: Integer)
            * `league_id` (Type: Integer)
            * `three_points` (Type: Integer)
            * `two_points` (Type: Integer)
            * `free_throws` (Type: Integer)
            * `rebounds` (Type: Integer)
            * `assists` (Type: Integer)
            * `blocks` (Type: Integer)
            * `steals` (Type: Integer)
            * `turnovers` (Type: Integer)
            * `tot_points` (Type: Integer)
      * `POST /fantasyStats`
         * Description: This endpoint receives the details of a stats entry to newly create. The stat details must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * `{  
                  "player_id": 14928,
                  "schedule_id": 86,
                  "league_id": 1,
                  "three_points": 0,
                  "two_points": 0,
                  "free_throws": 0,
                  "rebounds": 1,
                  "assists": 3,
                  "blocks": 6,
                  "steals": 8,
                  "turnovers": 1,
                  "tot_points": 0 
               }`
      * `PUT /fantasyStats`
         * Description: This endpoint receives the details of a stats entry to update. The stat details must be provided as a JSON, sent in the body of the PUT request. The JSON in the request body must provide a stats_id, and any assortment of the other fields to modify.
         * Sample Request Body:
            * `{ 
                  "stats_id": 16,
                  "player_id": 14928,
                  "schedule_id": 86,
                  "league_id": 1,
                  "three_points": 1,
                  "two_points": 1,
                  "free_throws": 0,
                  "rebounds": 1,
                  "assists": 3,
                  "blocks": 6,
                  "steals": 8,
                  "turnovers": 1,
                  "tot_points": 5 
               }`
      * `DELETE /fantasyStats`
         * Description: This endpoint receives the stats_id AND the league_id of a stats entry to delete. The stats_id and league_id must be provided as a query parameter.
         * Sample Request: `localhost:8080/fantasyStats?stats_id=16&league_id=1`
         * Request Parameters:
            * `user_id` (Type: Integer)
            * `league_id` (Type: Integer)
   * ___FantasyPlayers___
       * `GET /fantasyPlayers`
         * Description: This endpoint retrieves all players that match the template given in the query parameters. No parameters are required.
         * Sample Request: localhost:8080/fantasyPlayers?player_id=1
         * Request Parameters:
            * `player_id` (Type: Integer)
            * `team_id` (Type: Integer)
            * `first_name` (Type: String)
            * `last_name` (Type: String)
            * `position` (Type: String)
            * `nba_team` (Type: String)
            * `league_id` (Type: Integer)
            * `client_id` (Type: Integer)
            * `ball_api_id` (Type: Integer)
      * `POST /fantasyPlayers`
         * Description: This endpoint receives the details of a players to newly create. The players must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * `{ "team_id: 0, "first_name": "snoop","last_name": "dogg", "position": "F","nba_team": "cali", "league_id": 1,"client_id":0}`
      * `PUT /fantasyPlayers`
         * Description: This endpoint receives the details of a players to update. The players must be provided as a JSON, sent in the body of the PUT request. The JSON in the request body must provide the player_id, and all other fields are optional.
         * Sample Request Body:
            * `player_id` (Type: Integer)
            * `team_id` (Type: Integer)
            * `first_name` (Type: String)
            * `last_name` (Type: String)
            * `position` (Type: String)
            * `nba_team` (Type: String)
            * `league_id` (Type: Integer)
            * `client_id` (Type: Integer)
            * `ball_api_id` (Type: Integer)
      * `DELETE /fantasyPlayers`
         * Description: This endpoint receives the player_id of a players to delete. The players_id must be provided as a query parameter.
         * Sample Request: localhost:8080/fantasyPlayers?player_id=11
         * Request Parameters:
            * `player_id` (Type: Integer)
   * ___FantasyLeagues___
      * `GET /fantasyLeagues`
         * Description: This endpoint retrieves all leagues that match the template given in the query parameters. No parameters are required.
         * Sample Request: localhost:8080/fantasyLeagues?league_start_date=2021-12-12
         * Request Parameters:
            * `league_id` (Type: Integer)
            * `league_name` (Type: String)
            * `admin_id` (Type: Integer)
            * `league_size` (Type: Integer)
            * `league_start_date` (Type: LocalDate)
            * `league_end_date` (Type: LocalDate)
      * `POST /fantasyLeagues`
         * Description: This endpoint receives the details of a league to newly create. The league must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * `{
                  "client_id": 1,
                  "league_name": "fake league",
                  "admin_id": 1,
                  "league_size": 10,
                  "league_start_date": "2021-12-12",
                  "league_end_date": "2021-12-31"
              }`
      * `PUT /fantasyLeagues`
         * Description: This endpoint receives the details of a league to update. The league must be provided as a JSON, sent in the body of the PUT request. The JSON in the request body must have a league_id field. Moreover, the only attribute of a league that may be modified after league creation is the league_name.
         * Sample Request Body:
            * `{
                  "league_id": 40,
                  "client_id": 1,
                  "league_name": "fake league NAME CHANGE",
                  "admin_id": 1,
                  "league_size": 10,
                  "league_start_date": "2021-12-12",
                  "league_end_date": "2021-12-31"
              }`
      * `DELETE /fantasyLeagues`
         * Description: This endpoint receives the league_id of a league to delete. The league_id must be provided as a query parameter.
         * Sample Request: localhost:8080/fantasyLeagues?league_id=40
         * Request Parameters:
            * `league_id` (Type: Integer)
   * ___FantasyGames___
      * `GET /fantasyGames`
           * Description: This endpoint retrieves all stat sheets that represent a player's contribution during one game that match the template given in the query parameters. No parameters are required (providing no parameters will return all stats).
           * Sample Request: localhost:8080/fantasyStats?league_id=1
           * Request Parameters:
              * `schedule_id` (Type: Integer)
              * `league_id` (Type: Integer)
              * `home_team_id` (Type: Integer)
              * `away_team_id` (Type: Integer)
              * `game_start_date` (Type: LocalDate)
              * `game_end_date` (Type: LocalDate)
              * `winner_id` (Type: Integer)
      * `POST /fantasyGames`
           * Description: This endpoint receives the details of a game to newly create. The game must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have at least the fields shown in the sample below.
           * Sample Request Body:
              * `{
                  "league_id": 1,
                  "home_team_id": 1,
                  "away_team_id": 3,
                  "game_start_date": "2021-12-08",
                  "game_end_date": "2021-12-15"
                 }`
      * `PUT /fantasyGames`
           * Description: This endpoint receives the details of a game to update. The game must be provided as a JSON, sent in the body of the PUT request. The JSON in the request body must have a schedule_id field.
           * Sample Request Body:
              * `{
                  "schedule_id": 101,
                  "league_id": 1,
                  "home_team_id": 1,
                  "away_team_id": 4,
                  "game_start_date": "2021-12-08",
                  "game_end_date": "2021-12-15"
                 }`
      * `DELETE /fantasyGames`
           * Description: This endpoint receives the schedule_id of a league to delete. The schedule_id must be provided as a query parameter.
           * Sample Request: localhost:8080/fantasyGames?schedule_id=40
           * Request Parameters:
              * `schedule_id` (Type: Integer)

## 2. System Tests Corresponding to API

## 3. Unit Tests
  * The unit tests are located in src/test/java/FantasyBasketball, and test coverage is more than 90%

## 4. Style Compliant

   * Our team decided to use SonarLint to be able to view bug smells in addition to IntelliJ's built in style checker. We had also marked Sonarlint to ignore a few bugs that were appearing due to code in our project that was still underdevelopment and were necessary (for example: variables that were required to be snake case instead of camel case to communicate effectively with our Amazon RDS MySQL instance).

   * ![Screen Shot 2021-11-15 at 11 12 37 PM](https://user-images.githubusercontent.com/76623695/141914002-162d9f16-5a04-4bc0-9e6e-889a5acfbf3e.png)

## 5. Build, Run, Test Instructions

   * First, make sure you have installed Maven.
       * (If you are on a Mac, you can try `mvn -v` to see what version of Maven you already have installed. Otherwise, you can run `brew install maven` to install.)
   * Next, execute the following command to install all the necessary dependencies to execute FantasyBasketballApplication: (Run these in /FantasyBasketball.)
       * `mvn install`
   * Finally, run the following command to run the FantasyBasketballApplication:
       * `mvn spring-boot:run`
   * (The above instructions were inspired by the following source: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

## 1. Client

## 2. Continuous Integration

## 3. Coverage

## 4. Bug Finder

## 5. Documented API (Updated)

