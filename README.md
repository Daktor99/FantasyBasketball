# ASWE_TeamProject

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
         * Description: This endpoint receives the details of a user to update. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
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
         * Description: This endpoint receives the details of a team to update. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must provide the team_id, and all other fields are optional.
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
   * ___FantasyPlayers___
   * ___FantasyLeagues___
      * `GET /fantasyLeagues`
         * Description: This endpoint retrieves all teams that match the template given in the query parameters. No parameters are required.
         * Sample Request: localhost:8080/fantasyLeagues?league_start_date=2021-12-12
         * Request Parameters:
            * `league_id` (Type: Integer)
            * `league_name` (Type: String)
            * `admin_id` (Type: Integer)
            * `league_size` (Type: Integer)
            * `league_start_date` (Type: LocalDate)
            * `league_end_date` (Type: LocalDate)
      * `POST /fantasyLeagues`
         * Description: This endpoint receives the details of a league to newly create. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
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
         * Description: This endpoint receives the details of a league to update. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have a league_id field. Moreover, the only attribute of a league that may be modified after league creation is the league_name.
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
      * `DELETE /fantasyTeams`
         * Description: This endpoint receives the league_id of a league to delete. The league_id must be provided as a query parameter.
         * Sample Request: localhost:8080/fantasyLeagues?league_id=40
         * Request Parameters:
            * `league_id` (Type: Integer)
   * ___FantasyGames___

## 2. System Tests Corresponding to API

## 3. Unit Tests

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
