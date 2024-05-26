package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.Year;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE);
            // System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static final String DATABASE = "jdbc:sqlite:database/Finaldata.db";

   
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }
    public ArrayList<Student> getSInfo() {
        // Create the ArrayList of LGA objects to return
        ArrayList<Student> stds = new ArrayList<Student>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Student";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String id = results.getString("StudentID");
                String name = results.getString("StudentName");

                // Create a LGA Object
                Student std = new Student(id, name);

                // Add the lga object to the array
                stds.add(std);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return stds;
    }

    public ArrayList<Persona> getPersonaInfo() {
        // Create the ArrayList of LGA objects to return
        ArrayList<Persona> personas = new ArrayList<Persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select * from persona";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("Name");
                int age = results.getInt("Age");
                String background = results.getString("Background");
                String needs = results.getString("Needs");
                String goals = results.getString("Goals");

                // Create a LGA Object

                Persona persona = new Persona(name, age, background, needs, goals);
                // Add the lga object to the array
                personas.add(persona);
            }
            // all of bellow has to be used
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        // return statement
        return personas;
    }

    public ArrayList<globalTemp> getGlobalTemp() {
        ArrayList<globalTemp> globalTemps = new ArrayList<globalTemp>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                        select gt.year, gt.AvgOceanLandTemp
                        from globaltemp gt
                        where year = (select min (year) from globaltemp where AvgOceanLandTemp is not null)

                        UNION all
                    --select last year and temperature in globaltemp
                        SELECT YEAR, gt.AvgOceanLandTemp
                        FROM GLOBALTEMP gt
                        WHERE year = (select max(year) from GLOBALTEMP) and exists (
                            select gt.Year, gt.AvgOceanLandTemp
                            from globaltemp where gt.AvgOceanLandTemp is not null
                        );
                            """;

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int year = results.getInt("year");
                double temp = results.getDouble("AvgOceanLandTemp");

                // Create a LGA Object

                globalTemp globalTempObject = new globalTemp(year, temp);
                // Add the lga object to the array
                globalTemps.add(globalTempObject);
            }
            // all of bellow has to be used
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        // return statement
        return globalTemps;
    }

    public ArrayList<Integer> getYearNumb() {
        ArrayList<Integer> globalYearArrayList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "select DISTINCT year from globaltemp";
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                globalYearArrayList.add(results.getInt("year"));
            }

            statement.close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return globalYearArrayList;
    }
    public ArrayList<Integer> getYearNumbGlobalNotNull() {
        ArrayList<Integer> gYearNotNul = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "select * from globaltemp where avgoceanlandtemp != ''";
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                gYearNotNul.add(results.getInt("Year"));
            }

            statement.close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return gYearNotNul;
    }

    public ArrayList<Long> getPopChangeBySyearandEyear(int Syear, int Eyear) {
        ArrayList<Long> popChange = new ArrayList<>();
        String sql = """
                SELECT (select population from population where countryid = 204 and pyear = ?) as start,
                (select population from population where countryid = 204 and pyear = ?)as end;
                    """;

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(30);
            pstmt.setInt(1, Syear);
            pstmt.setInt(2, Eyear);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    popChange.add(rs.getLong("start"));
                    popChange.add(rs.getLong("end"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return popChange;
    }

    /*----------------------------------------------PAGE-COUNTRY------------------------------------------------------------------ */
    public ArrayList<String> getCountryName() {
        ArrayList<String> countryNameArrayList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT * FROM COUNTRY";
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                countryNameArrayList.add(results.getString("CountryName"));
            }

            statement.close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return countryNameArrayList;
    }

    /*--------------------------------------------------------------------------------- */
    public ArrayList<Integer> getCountryYear() {
        ArrayList<Integer> countryYearArrayList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                    SELECT DISTINCT CYEAR
                    FROM COUNTRYTEMP
                    ORDER BY CYEAR ASC;
                     """;

            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                countryYearArrayList.add(results.getInt("CYEAR"));
            }

            statement.close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return countryYearArrayList;
    }

    /*--------------------------------------------------------------------------------- */

    public ArrayList<Integer> getCountryYearPopul() {
        ArrayList<Integer> countryYearPopulArrayList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                    SELECT distinct PYEAR from population
                             """;

            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                countryYearPopulArrayList.add(results.getInt("PYEAR"));
            }

            statement.close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return countryYearPopulArrayList;
    }

    /*--------------------------------------------------------------------------------- */
    public ArrayList<String> getCountryWithStates() {
        ArrayList<String> countryWithStatesArrayList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "select distinct countryname from country ctr join state st on ctr.id = st.countryid";
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                countryWithStatesArrayList.add(results.getString("countryname"));
            }

            statement.close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return countryWithStatesArrayList;
    }

    public ArrayList<Integer> getStateTempYear() {
        ArrayList<Integer> stateYearArrayList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "select distinct stateyear from statetemp order by stateyear";
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                stateYearArrayList.add(results.getInt("stateyear"));
            }

            statement.close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return stateYearArrayList;
    }

    public ArrayList<StateTempRelated> getStateTempArrayList(String countryName, int sYear, int eYear,
            String sortByTempCat, String orderByTempDif) { //
        ArrayList<StateTempRelated> stateTempRelateds = new ArrayList<StateTempRelated>(); // turned into string
                                                                                           // arraylist
        String sql = """
                                SELECT
                    start.year,
                    start.countryname,
                    start.statename,
                    start.temp_start AS start_temp,
                    end.temp_end AS end_temp,
                    end.temp_end - start.temp_start AS temp_difference
                FROM
                    (SELECT
                        stemp.stateyear AS year,
                        ctr.countryname,
                        st.statename,
                        %s AS temp_start
                    FROM
                        StateTemp stemp
                    JOIN State st ON stemp.StateID = st.StateID
                    JOIN Country ctr ON ctr.ID = st.CountryID
                    WHERE
                        ctr.countryname = ?
                        AND stemp.stateyear = ?) AS start
                JOIN
                    (SELECT
                        stemp.stateyear AS year,
                        ctr.countryname,
                        st.statename,
                        %s AS temp_end
                    FROM
                        StateTemp stemp
                    JOIN State st ON stemp.StateID = st.StateID
                    JOIN Country ctr ON ctr.ID = st.CountryID
                    WHERE
                        ctr.countryname = ?
                        AND stemp.stateyear = ?) AS end
                ON
                    start.statename = end.statename
                ORDER BY temp_difference %s """;

        sql = String.format(sql, sortByTempCat, sortByTempCat, orderByTempDif);

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(30);

            // pstmt.setString(1, sortByTempCat);
            pstmt.setString(1, countryName);
            pstmt.setInt(2, sYear);
            // pstmt.setString(4, sortByTempCat);
            pstmt.setString(3, countryName);
            pstmt.setInt(4, eYear);
            // pstmt.setString(7, orderByTempDif);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String stateName = rs.getString("statename");
                    String startTemp = String.valueOf(mathRound(rs.getDouble("start_temp")));
                    String endTemp = String.valueOf(mathRound(rs.getDouble("end_temp")));
                    String tempDif = String.valueOf(mathRound(rs.getDouble("temp_difference")));
                    StateTempRelated temp = new StateTempRelated(stateName, startTemp, endTemp, tempDif);
                    stateTempRelateds.add(temp);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stateTempRelateds;
    }

    public double mathRound(double value) {
        // Multiply the value by 1000 to move the decimal point three places to the
        // right
        double multipliedValue = value * 1000;

        // Round the multiplied value to the nearest integer
        long roundedValue = Math.round(multipliedValue);

        // Divide the rounded value by 1000 to move the decimal point back to its
        // original position
        return roundedValue / 1000.0;
    }

    public ArrayList<Double> getTempChangeBySyearandEyear(int Syear, int Eyear) {
        ArrayList<Double> tempChange = new ArrayList<>();
        String sql = """
                SELECT (SELECT AvgLandTemp FROM GlobalTemp
                                                WHERE Year = ?) -
                            (SELECT AvgLandTemp FROM GlobalTemp
                                                WHERE Year = ?) AS tempDiff;
                    """;

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(30);
            pstmt.setInt(1, Eyear);
            pstmt.setInt(2, Syear);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tempChange.add(rs.getDouble("tempDiff"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tempChange;
    }

    public double getCountryChangeBySyearandEyear(int Syear, int Eyear, String country) {
        double CountryChange = 9999;
        String sql = """
                                select ROUND(end_temp - start_temp,3) as tempdif from
                (
                SELECT CO.id, CT.CYEAR, CT.avglandtemp as start_temp
                FROM COUNTRYTEMP CT
                JOIN COUNTRY CO ON CT.COUNTRYID = CO.ID
                where CO.COUNTRYNAME = ? and CT.cyear = ?
                )
                ,
                (SELECT CO.id, CT.CYEAR, CT.avglandtemp as end_temp
                FROM COUNTRYTEMP CT
                JOIN COUNTRY CO ON CT.COUNTRYID = CO.ID
                where CO.CountryName = ? and CT.cyear = ?);

                                    """;

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(30);
            pstmt.setInt(2, Syear);
            pstmt.setInt(4, Eyear);
            pstmt.setString(1, country);
            pstmt.setString(3, country);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                   CountryChange = rs.getDouble("tempdif");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return CountryChange;
    }

    public ArrayList<Integer> getCountrypopulChangeBySyearandEyear(int Syear, int Eyear, String country) {
        ArrayList<Integer> CountryPopul = new ArrayList<>();
        String sql = """
                                SELECT (end_population - start_population) as populdif from
                (
                                SELECT CO.id, CT.PYEAR, CT.population as start_population
                                FROM POPULATION CT
                                JOIN COUNTRY CO ON CT.COUNTRYID = CO.ID
                                where CO.CountryName = ? and CT.pyear = ?
                )
                ,
                                (SELECT CO.id, CT.PYEAR, CT.population as end_population
                                FROM POPULATION CT
                                JOIN COUNTRY CO ON CT.COUNTRYID = CO.ID
                                where CO.CountryName = ? and CT.pyear = ?);

                                    """;

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(30);
            pstmt.setInt(2, Syear);
            pstmt.setInt(4, Eyear);
            pstmt.setString(1, country);
            pstmt.setString(3, country);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CountryPopul.add(rs.getInt("populdif"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return CountryPopul;
    }

    ///// TEST for subtaks 3a global single period

    public ArrayList<Integer> getGlobalYearTempPeriod() {
        ArrayList<Integer> years = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                    select year, avglandtemp as avg_temp from globaltemp where avgoceanlandtemp = ''
                    union
                    select year, avgoceanlandtemp as avg_temp from globaltemp where avgoceanlandtemp != ''
                    ;
                        """;
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                years.add(results.getInt("year"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return years;
    }

    public ArrayList<Integer> getCityTempYear() {
        ArrayList<Integer> year = new ArrayList<>();
        String query = """
            SELECT DISTINCT CityYear
              FROM CityTemp
              ORDER BY CityYear
                """;

        try (Connection connection = connect()){
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            year.add(result.getInt("CityYear"));
        }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return year;
    }

    //Get City Temperature with start year temp, end year temp and temp diff
    public ArrayList<CityTemp> getCityTemp(String countryName, int startYear, int endYear, String sortBy, String orderBy) {
        DecimalFormat df = new DecimalFormat("#.###");
        ArrayList<CityTemp> cityTemps = new ArrayList<>();
        String query = """
            SELECT endYear.CityName AS CityName, 
                   startYear.CTemp AS firstTemp, 
                   endYear.CTemp AS secondTemp, 
                   endYear.CTemp - startYear.CTemp AS TempDiff
              FROM
              (SELECT ctT.CityYear, ct.CityName, %s AS CTemp
                FROM CityTemp ctT
                JOIN City ct ON ctT.CityID = ct.CityID
                WHERE ct.CountryID = (SELECT ID FROM Country WHERE CountryName =?)
                AND ctT.CityYear = ?) AS startYear
              JOIN
              (SELECT ctT.CityYear, ct.CityName, %s AS CTemp
                FROM CityTemp ctT
                JOIN City ct ON ctT.CityID = ct.CityID
                WHERE ct.CountryID = (SELECT ID FROM Country WHERE CountryName =?)
                AND ctT.CityYear = ?) AS endYear
              ON startYear.CityName = endYear.CityName
              ORDER BY TempDiff %s
                """;

        query = String.format(query, sortBy, sortBy, orderBy);

        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setQueryTimeout(30);

            statement.setString(1, countryName);
            statement.setInt(2, startYear);
            statement.setString(3, countryName);
            statement.setInt(4, endYear);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String cityName =result.getString("CityName");
                    double startTemp = Double.parseDouble(df.format(result.getDouble("firstTemp")));
                    double endTemp = Double.parseDouble(df.format(result.getDouble("secondTemp")));
                    double tempDiff = Double.parseDouble(df.format(result.getDouble("TempDiff")));

                    CityTemp cityTemp = new CityTemp(cityName, startTemp, endTemp, tempDiff);
                    cityTemps.add(cityTemp);
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return cityTemps;
    }

    //------------------------------Period-Similiar--------------------------------------------------//

    public ArrayList<Integer> getYearPeriod() {
        ArrayList<Integer> YearPeriodArrayList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                                SELECT DISTINCT CityYear
                                FROM CityTemp
                                ORDER BY CityYear;
                            """;
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                YearPeriodArrayList.add(results.getInt("CityYear"));
            }

            statement.close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return YearPeriodArrayList;
    }

    public ArrayList<String> getCountries() {
        ArrayList<String> countries = new ArrayList<>();
        Connection connection = null;
        try  {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet resultSet = statement.executeQuery("SELECT  countryname FROM country");
            while (resultSet.next()) {
                countries.add(resultSet.getString("countryname"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return countries;
    }

    public ArrayList<String> getStates(String countryname) {
        ArrayList<String> states = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                SELECT statename
                  FROM STATE CO
                  JOIN COUNTRY ST ON CO.CountryID = ST.ID
                  WHERE countryname = ?;;
            """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, countryname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                states.add(resultSet.getString("statename"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return states;
    }

    public ArrayList<String> getCities(String statename) {
        ArrayList<String> cities = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                SELECT cityname 
                  FROM city CO 
                  JOIN Country ST ON CO.CountryID = ST.ID
                  WHERE Country = ?;
            """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, statename);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cities.add(resultSet.getString("cityname"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return cities;
    }

    public ArrayList<year_temp> getArrayListOfYearsAndTempInPeriod(String region, int startYear, int periodLength){
        ArrayList<year_temp> yeartempObjs = new ArrayList<>();
        Connection connection = null;
        for(int i = 0; i < periodLength; i++){
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                select year, avgoceanlandtemp from globaltemp where avgoceanlandtemp != '' and year = ?;
            """;
            PreparedStatement ppstm = connection.prepareStatement(query);
            ppstm.setInt(1, startYear + i);
            ResultSet resultSet = ppstm.executeQuery();
            while (resultSet.next()) {
                int year = resultSet.getInt("year");
                double temp = resultSet.getDouble("avgoceanlandtemp");
                year_temp ytobj = new year_temp(year, temp);
                yeartempObjs.add(ytobj);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
        return yeartempObjs;
    }
    }

