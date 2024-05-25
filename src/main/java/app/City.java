package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class City implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/City.html";

    @Override
    public void handle(Context context) throws Exception {
        String countryName = context.formParam("getCountry");
        String startYear = context.formParam("startYear");
        String endYear = context.formParam("endYear");
        String sortBy = context.formParam("sortBy");
        String orderBy = context.formParam("orderBy");

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html
                + """
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Demeter Data</title>
                            <link rel="stylesheet" href="city.css">
                            <link rel="icon" type="image/x-icon" href="icon.ico">

                            <script>
                                function populateEndYearOptions() {
                                    var startYearSelect = document.getElementById("startYear");
                                    var endYearSelect = document.getElementById("endYear");
                                    var startYear = startYearSelect.value;

                                    endYearSelect.innerHTML = "";

                                    for (var i+1 = startYear; i <= 2013; i++) {
                                        var option = document.createElement("option");
                                        option.text = i;
                                        option.value = i;
                                        endYearSelect.add(option);
                                    }
                                }
                            </script>

                        </head>

                        <body>
                            <div class="top-menu">
                                <div class="logo-and-name">
                                    <a href="/" class="logo-container">
                                        <img src="Logo.jpg" alt="Logo" height="100" width="100">
                                    </a>
                                    <a href="/" class="website-name">Demeter Data</a>
                                </div>
                                <div class="menu-buttons">
                                    <div class="dropdown">
                                        <button class="dropbtn">Basic</button>
                                        <div class="basic-content">
                                            <a href="Global.html">Global</a>
                                            <a href="Country.html">Country</a>
                                            <a href="State.html">State</a>
                                            <a href="City.html">City</a>
                                        </div>
                                    </div>
                                    <div class="dropdown">
                                        <button class="dropbtn">Advance</button>
                                        <div class="advance-content">
                                            <a href="AvgtempPopul.html">Temperature &amp; population changes</a>
                                            <a href="period.html">Periods with similar temperature &amp; population</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="about">
                                    <a href="mission.html">About</a>
                                </div>
                            </div>
                            <div class="menuSpace">
                            </div>
                        </body>

                            <!---------------------------------------------------------------------------------------------------------->
                            <div class="nav-container">
                                <div class="navigation-tab">
                                    <a href="Global.html" class="navigation" id="glo">GLOBAL</a>
                                    <a href="Country.html" class="navigation" id="coun">COUNTRY</a>
                                    <a href="State.html" class="navigation" id="sta">STATE</a>
                                    <a href="City.html" class="navigation" id="city">CITY</a>
                                </div>
                            </div>
                            <body-country>
                            <div class="container">
                                <div class="input">
                                    <div class="search-button">
                                        <form action="/City.html" method="post">
                                        <table class="option-table">
                                            <tbody>
                                                <tr>
                                                    <td><label for='getCountry'>Country</label></td>
                                                    <td>
                                                        <select id='getCountry' name='getCountry'>
                                                            <option value="" selected disabled>Select a country</option>
                                """;

        ArrayList<String> ctrName = getCountryName();
        for (String ctrNames : ctrName) {
            html = html + "<option>" + ctrNames + "</option>";
        }
        html = html + "                    </select>";
        html = html + "             </td>";

        html = html + """
                                </tr>
                                <tr>
                                    <td><label for='startYear'>Year</td>
                                    <td>
                                        <select id='startYear' name='startYear' onchange='populateEndYearOptions()'>
                                            <option value="" selected disabled>Select a year</option>
                """;

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Integer> yearNum = jdbc.getCityTempYear();
        for (int yearNumb : yearNum) {
            html = html + "<option>" + yearNumb + "</option>";
        }
        html = html + "</select>";
        html = html + "</td>";

        html = html + """
                                    <td><label for='endYear'>To</label></td>
                                    <td>
                                        <select id='endYear' name="endYear">
                                            <option value="" selected disabled>Select a year</option>
                """;
        
        for (int yearNumb : yearNum) {
            html = html + "<option>" + yearNumb + "</option>";
        }      
        html = html + "                   </select>";
        html = html + "             </td>";
        html = html + "         </tr>";

        html = html + """
                                <tr>
                                    <td><label for='sortBy'>Sort by</label></td>
                                    <td>

                                        <select id='sortBy' name='sortBy'>
                                            <option value="" selected disabled>Option</option>
                                            <option value="AvgTemp" selected >Average Temperature</option>
                                            <option value="MinTemp">Minimum Temperature</option>
                                            <option value="MaxTemp">Maximum Temperature</option>
                                        </select>
                                    </td>
                                </tr>
                """;

        html = html + """
                                <tr>
                                    <td><label for='orderBy'>Order by</label></td>
                                    <td>
                                        <select id='orderBy' name='orderBy'>
                                            <option value="" selected disabled>Option</option>
                                            <option value="Asc" selected>Ascending</option>
                                            <option value="Desc">Descending</option>
                                        </select>
                                    </td>
                                </tr>
                """;

        html = html + """
                                    </tbody>
                                </table>
                            <button type="submit" class="search-form">Search</button>
                            </form>
                        </div>
                    </div>
                </div>
                    </body-country>
                        """;

        html = html + """
                <!--------------------------OutPut--------------------------->
                <body>
                    <table>
                        <thead>
                            <tr>
                                <th>Rank</th>
                                <th>City</th>
                    """;

        if (startYear == null) {
            html = html + "     <th>Temperature in start year</th>";
        } else {
            html = html + "     <th>Temperature in " + startYear + "</th>";
        }

        if (endYear == null) {
            html = html + "     <th>Temperature in end year</th>";
        } else {
            html = html + "     <th> Temperature in " + endYear + "</th>";
        }

        html = html + """
                            <th>Temperature difference</th>
                        </tr>
                    </thead>
                    <tbody>
                """;

        if (countryName == null ||
                startYear == null ||
                endYear == null ||
                sortBy == null ||
                orderBy == null) {
            html = html + "<tr>";
            html = html + "<td>No Data</td>";
            html = html + "<td>Please select a Country</td>";
            html = html + "<td>Please select a first year</td>";
            html = html + "<td>Please select a last year</td>";
            html = html + "<td>No Data</td>";
            html = html + "</tr>";
        } else {
            int availableYear = yearAvailable(countryName);
            if (Integer.parseInt(startYear) < availableYear) {
            html = html + "<tr>";
            html = html + "<td colspan='5'><h3>There is no data available for " + countryName + " in " + startYear + ". The earliest available year is " + availableYear + "</h3></td>";
            html = html + "</tr>";
            } else {
            html = html + outPutCityTemp(countryName, Integer.parseInt(startYear), Integer.parseInt(endYear), sortBy,
                    orderBy);
            }
        }
        html = html
                + """
                                    </tbody>
                                </table>
                            </body>
                            <!---------------------------------------------------------------------------------------------------------->

                            <footer>
                                <div class="sitemap-footer">
                                    <a href="/" class="logo-container-foot">
                                        <img src="Logobottom.png" alt="Logo" height="100" width="100">
                                    </a>
                                    <div class="sitemap-section">
                                        <h3>Basic</h3>
                                        <div class="sbasic-content">
                                            <ul>
                                                <li><a href="Global.html">Global</a></li>
                                                <li><a href="Country.html">Country</a></li>
                                                <li><a href="State.html">State</a></li>
                                                <li><a href="City.html">City</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="sitemap-section">
                                        <h3>Advance</h3>
                                        <div class="sadvance-content">
                                            <ul>
                                                <li><a href="AvgtempPopul.html">Temperature changes <br>in different time periods</a></li>
                                                <li><a href="period.html">Periods with similar <br>population and temperature</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="sitemap-section">
                                        <h3>About us</h3>
                                        <div class="aboutus-content">
                                            <ul>
                                                <li><a href="mission.html">Our mission</a></li>
                                                <li><a href="mission.html">Persona</a></li>
                                                <li><a href="mission.html">Team members</a></li>
                                            </ul>
                                        </div>
                                        <p>&copy; 2024 Demeter Data. All rights reserved.</p>
                                    </div>
                                </div>

                            </footer>

                        </body>

                        </html>
                                """;

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    // Get out put City Temperature
    private String outPutCityTemp(String countryName, int startYear, int endYear, String sortBy, String orderBy) {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<CityTemp> cityTemp = jdbc.getCityTemp(countryName, startYear, endYear, sortBy, orderBy);
        String html = "";
        for (int i = 0; i < cityTemp.size(); i++) {
            CityTemp cityTemps = cityTemp.get(i);
            String rank = getOrdinal(i + 1);
            html = html + "<tr>";
            html = html + "<td>" + rank + "</td>";
            html = html + "<td>" + cityTemps.getCityName() + "</td>";
            html = html + "<td>" + cityTemps.getStartTemp() + "</td>";
            html = html + "<td>" + cityTemps.getEndTemp() + "</td>";
            html = html + "<td>" + cityTemps.getTempDiff() + "</td>";
            html = html + "</tr>";
        }
        return html;
    }

    // Get Country based on City (some Country dont have City)
    private ArrayList<String> getCountryName() {
        ArrayList<String> countryName = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                    SELECT DISTINCT CountryName
                      FROM Country ct
                      JOIN City cty ON ct.ID = cty.CountryID
                        """;

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                countryName.add(result.getString("CountryName"));
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return countryName;
    }

    // Get Ordinal Number
    private String getOrdinal(int number) {
        if (number % 100 >= 11 && number % 100 <= 13) {
            return number + "th";
        }
        switch (number % 10) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }

    //Get first year available for each CityTemp
    private int yearAvailable (String countryName) {
        int getYearAvail = 0;
        String query = """
            SELECT min(CityYear) AS Year
              FROM CityTemp ctT
              JOIN City ct ON ct.CityID = ctT.CityID
              JOIN Country ctr ON ct.CountryID = ctr.ID
              WHERE ctr.CountryName = ?;
                """;

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
                PreparedStatement statement = connection.prepareStatement(query)){
            statement.setQueryTimeout(30);

            statement.setString(1, countryName);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    getYearAvail = result.getInt("Year");
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return getYearAvail;
    }
}