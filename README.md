# ASWE_TeamProject

1. Documented API
   * Users
      * GET /users
         * (This endpoint retrieves all users that match the template given in the query parameters.)
         * Query Parameters:
            * 
   * FantasyTeam
      * 
   * FantasyStats
   * FantasyPlayer
   * FantasyLeague
   * FantasyGame

2. System Tests Corresponding to API

3. Unit Tests

4. Style Compliant

5. Build, Run, Test Instructions

    * First, make sure you have installed Maven.
        * (If you are on a Mac, you can try "mvn -v" to see what version of Maven you already have installed. Otherwise, you can run "brew install maven" to install.)
    * Next, execute the following command to install all the necessary dependencies to execute FantasyBasketballApplication: (Run these in /FantasyBasketball.)
        * mvn install
    * Finally, run the following command to run the FantasyBasketballApplication:
        * mvn spring-boot:run
