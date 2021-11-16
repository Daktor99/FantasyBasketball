# ASWE_TeamProject

1. Documented API
   * ___Users___
      * _GET /users_
         * Description: This endpoint retrieves all users that match the template given in the query parameters. No parameters are required.
         * Sample Request: localhost:8080/users?user_id=1
         * Request Parameters:
            * user_id (Type: Integer)
            * email (Type: String)
            * username (Type: String)
            * first_name (Type: String)
            * last_name (Type: String)
      * _POST /users_
         * Description: This endpoint receives the details of a user to newly create. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * { "client_id": 1, "username": "fake", "email": "fake@gmail.com", "first_name": "fakefirstname", "last_name": "fakelastname" }
      * _PUT /users_
         * Description: This endpoint receives the details of a user to update. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * { "user_id": 100, "client_id": 1, "username": "fake", "email": "fake@gmail.com", "first_name": "fakefirstname", "last_name": "fakelastname" }
      * _DELETE /users_
         * Description: This endpoint receives the user_id of a user to delete. The user_id must be provided as a query parameter.
         * Sample Request: localhost:8080/users?user_id=1
         * Request Parameters:
            * user_id (Type: Integer)
   * ___FantasyTeams___
      * _GET /fantasyTeams_
         * Description: This endpoint retrieves all teams that match the template given in the query parameters. No parameters are required.
         * Sample Request: localhost:8080/fantasyTeams?team_id=1
         * Request Parameters:
            * team_id (Type: Integer)
            * team_name (Type: String)
            * owner_id (Type: Integer)
            * league_id (Type: Integer)
      * _POST /fantasyTeams_
         * Description: This endpoint receives the details of a team to newly create. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * team_name (Type: String)
            * owner_id (Type: Integer)
            * league_id (Type: Integer)
      * _PUT /fantasyTeams_
         * Description: This endpoint receives the details of a team to update. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must provide the team_id, and all other fields are optional.
         * Sample Request Body:
            * team_id (Type: Integer)
            * team_name (Type: String)
            * owner_id (Type: Integer)
            * league_id (Type: Integer)
      * _DELETE /fantasyTeams_
         * Description: This endpoint receives the team_id of a team to delete. The team_id must be provided as a query parameter.
         * Sample Request: localhost:8080/fantasyTeams?team_id=11
         * Request Parameters:
            * team_id (Type: Integer)
   * ___FantasyStats___
   * ___FantasyPlayers___
   * ___FantasyLeagues___
      * _GET /fantasyLeagues_
         * Description: This endpoint retrieves all teams that match the template given in the query parameters. No parameters are required.
         * Sample Request: localhost:8080/fantasyTeams?team_id=1
         * Request Parameters:
            * team_id (Type: Integer)
            * team_name (Type: String)
            * owner_id (Type: Integer)
            * league_id (Type: Integer)
      * _POST /fantasyLeagues_
         * Description: This endpoint receives the details of a team to newly create. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must have fields exactly identical to those which are shown in the sample below.
         * Sample Request Body:
            * team_name (Type: String)
            * owner_id (Type: Integer)
            * league_id (Type: Integer)
      * _PUT /fantasyLeagues_
         * Description: This endpoint receives the details of a team to update. The user must be provided as a JSON, sent in the body of the POST request. The JSON in the request body must provide the team_id, and all other fields are optional.
         * Sample Request Body:
            * team_id (Type: Integer)
            * team_name (Type: String)
            * owner_id (Type: Integer)
            * league_id (Type: Integer)
      * _DELETE /fantasyTeams_
         * Description: This endpoint receives the team_id of a team to delete. The team_id must be provided as a query parameter.
         * Sample Request: localhost:8080/fantasyTeams?team_id=11
         * Request Parameters:
            * team_id (Type: Integer)
   * FantasyGame

2. System Tests Corresponding to API

3. Unit Tests

4. Style Compliant

Our team decided to use SonarLint to be able to view bug smells in addition to IntelliJ's built in style checker. We had also marked Sonarlint to ignore a few bugs that were appearing due to code in our project that was still underdevelopment and were necessary (for example: variables that were required to be snake case instead of camel case to communicate effectively with our Amazon RDS MySQL instance).

![Screen Shot 2021-11-15 at 11 12 37 PM](https://user-images.githubusercontent.com/76623695/141914002-162d9f16-5a04-4bc0-9e6e-889a5acfbf3e.png)

5. Build, Run, Test Instructions

    * First, make sure you have installed Maven.
        * (If you are on a Mac, you can try "mvn -v" to see what version of Maven you already have installed. Otherwise, you can run "brew install maven" to install.)
    * Next, execute the following command to install all the necessary dependencies to execute FantasyBasketballApplication: (Run these in /FantasyBasketball.)
        * mvn install
    * Finally, run the following command to run the FantasyBasketballApplication:
        * mvn spring-boot:run
    * (The above instructions were inspired by the following source: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)