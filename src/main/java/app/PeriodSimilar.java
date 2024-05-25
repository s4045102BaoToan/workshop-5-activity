package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
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
public class PeriodSimilar implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/period.html";
    JDBCConnection jdbc = new JDBCConnection();

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + """
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Demeter Data</title>
            <link rel="stylesheet" href="period.css">
            <link rel="icon" type="image/x-icon" href="icon.ico">

<!------------------------------------------Script--------------------------------------------------------->

            <script>
                // Function to fetch and update states based on selected country
                function updateStates() {
                    var country = document.getElementById("country").value;
                    fetch('/getStates?country=' + country)
                        .then(response => response.json())
                        .then(states => {
                            var stateSelect = document.getElementById("state");
                            var citySelect = document.getElementById("city");
                            stateSelect.innerHTML = "<option value='' selected disabled>Select a state</option>";
                            citySelect.innerHTML = "<option value='' selected disabled>Select a city</option>";
                            states.forEach(state => {
                                stateSelect.innerHTML += "<option value='" + state + "'>" + state + "</option>";
                            });
                        });
                }

                // Function to fetch and update cities based on selected state
                function updateCities() {
                    var state = document.getElementById("state").value;
                    fetch('/getCities?state=' + state)
                        .then(response => response.json())
                        .then(cities => {
                            var citySelect = document.getElementById("city");
                            citySelect.innerHTML = "<option value='' selected disabled>Select a city</option>";
                            cities.forEach(city => {
                                citySelect.innerHTML += "<option value='" + city + "'>" + city + "</option>";
                            });
                        });
                }
            </script>
            
        </head>
        <body>
            <div class="top-menu">
                <div class="logo-and-name">
                    <a href="index.html" class="logo-container">
                        <img src="Logo.jpg" alt="Logo" height="100" width="100">
                    </a>
                    <a href="index.html" class="website-name">Demeter Data</a>
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
            
            <!---------------------------------------------------------------------------------------------------------->
        
                <body-country>
                    
                    <form action="/period.html" method="get">
                        <div class="input">
                            <div class="search-button">
                                <table class="option-table">
                                    <tbody>
                                        <tr>
                                            <td>Country</td>
                                            <td>
                                                <select name="country" id="country" onchange="updateStates()">
                                                    <option value="" selected disabled>Select a country</option>""";
                                                            ArrayList<String> ctrName = jdbc.getCountries();
                                                            for (String country : ctrName) {
                                                                html += "<option value='" + country + "'>" + country + "</option>";
                                                            }
                                                            html += """
                                                </select>
                                            </td>
                                        
                                            <td>State</td>
                                            <td>
                                            <select name="state" id="state" onchange="updateCities()">
                                            <option value="" selected disabled>Select a state</option>""";
                                            String selectedCountry = context.queryParam("country");
                                                    if (selectedCountry != null && !selectedCountry.isEmpty()) {
                                                        ArrayList<String> StaName = jdbc.getStates(selectedCountry);
                                                        for (String state : StaName) {
                                                            html += "<option value='" + state + "'>" + state + "</option>";
                                                        } 
                                                    }
                                        html += """
                                        </select>
                                    </td>
                                  
                                          
                            
                            
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Sort by</td>
                                            <td>
                                                <select name="sort by">
                                                    <option value="#">Option</option>
                                                    <option value="#">Averange population</option>
                                                    <option value="#">Averange temperature</option>
                                                </select>
                                                <td>City</td>
                                            <td>
                                            <select name="city" id="city">
                                            <option value="" selected disabled>Select a city</option>""";
                                            JDBCConnection jdbc = new JDBCConnection();
                                            String selectedState = context.queryParam("state");
                                            if (selectedState != null && !selectedState.isEmpty()) {
                                                ArrayList<String> cities = jdbc.getCities(selectedState);
                                                for (String city : cities) {
                                                    html += "<option value='" + city + "'>" + city + "</option>";
                                                }
                                            }
                                            html += """
                                        </select>    
                                    </td>
                                        </tr>
                                        <tr>
                                            <td>Number years period similiar</td>
                                            <td>
                                                <select name="Number years period similiar">
                                                    <option value="#">Option</option>
                                                    
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Start year</td>
                                            <td>
                                                <select name="Start Year">
                                                    <option value="" selected disabled>Select a year</option>""";
                                                ArrayList<Integer>PerYear = jdbc.getYearPeriod();
                                                for (Integer perYear : PerYear) {
                                                    html += "<option>" + perYear + "</option>";
                                                }         
                                                    //<option value="#">Option</option>
                                                    
                                                html += """
                                                </select>
                                                  
                                            </td>
                                            <td>Period length</td>
                                            <td>
                                                <select name="Year">
                                                    <option value="#">Option</option>
                            
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <button type="submit" class="search-form">Search</button>
                            </div>
                        </div>
                    </form>
                        
                </body-country>
            
            <!--------------------------------------------------out put--------------------------------------------------->
            <body>
                <table>
                    <h1>Country 1</h1>
                    <thead>
                        <tr>
                            <th>Country</th>
                            <th>State</th>
                            <th>City</th>
                            <th>Average Temperature</th>
                            <th>Average Population</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        
                    </tbody>
                    </table>
        
                    <table>
                    <h1>Country 2</h1>
                    <thead>
                        <tr>
                            <th>Country</th>
                            <th>State</th>
                            <th>City</th>
                            <th>Average Temperature</th>
                            <th>Average Population</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        
                    </tbody>
                </table>
        
                <table>
                    <h1>Country 3</h1>
                    <thead>
                        <tr>
                            <th>Country</th>
                            <th>State</th>
                            <th>City</th>
                            <th>Average Temperature</th>
                            <th>Average Population</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        
                    </tbody>
                    </table>
                    
                    <table>
                    <h1>Country 4</h1>
                    <thead>
                        <tr>
                            <th>Country</th>
                            <th>State</th>
                            <th>City</th>
                            <th>Average Temperature</th>
                            <th>Average Population</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        
                    </tbody>
                </table>
        
                <table>
                    <h1>Country 5</h1>
                    <thead>
                        <tr>
                            <th>Country</th>
                            <th>State</th>
                            <th>City</th>
                            <th>Average Temperature</th>
                            <th>Average Population</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        
                    </tbody>
                </table>
            </body> 
            
        
        
            <!---------------------------------------------------------------------------------------------------------->
        
            <footer>
                <div class="sitemap-footer">
                    <a href="index.html" class="logo-container-foot">
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

}
