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
        String html = """
            
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Demeter Data</title>
            <link rel="stylesheet" href="period.css">
            <link rel="icon" type="image/x-icon" href="icon.ico">

<!------------------------------------------Script--------------------------------------------------------->

<style>
                .hidden {
                    display: none;
                }
            </style>

            <script>
            document.addEventListener("DOMContentLoaded", function() {
                const countrySelect = document.getElementById("country");
                const stateSelect = document.getElementById("state");
                const citySelect = document.getElementById("city");
                const stateContainer = document.getElementById("state-container");
                const cityContainer = document.getElementById("city-container");
            
                function updateStates() {
                    const selectedCountry = countrySelect.value;
                    if (selectedCountry) {
                        stateContainer.classList.remove("hidden");
                        cityContainer.classList.add("hidden");
                        fetchData(`/getStates?country=${selectedCountry}`, function(states) {
                            stateSelect.innerHTML = "<option value='' selected disabled>Select a state</option>";
                            citySelect.innerHTML = "<option value='' selected disabled>Select a city</option>";
                            states.forEach(state => {
                                stateSelect.innerHTML += `<option value='${state}'>${state}</option>`;
                            });
                        });
                    }
                }
            
                function updateCities() {
                    const selectedCountry = countrySelect.value;
                    const selectedState = stateSelect.value;
                    if (selectedState) {
                        cityContainer.classList.remove("hidden");
                        fetchData(`/getCities?country=${selectedCountry}&state=${selectedState}`, function(cities) {
                            citySelect.innerHTML = "<option value='' selected disabled>Select a city</option>";
                            cities.forEach(city => {
                                citySelect.innerHTML += `<option value='${city}'>${city}</option>`;
                            });
                        });
                    }
                }
            
                countrySelect.addEventListener("change", function() {
                    updateStates();
                    updateCities();
                });
                stateSelect.addEventListener("change", updateCities);
            });
            
            function fetchData(url, callback) {
                fetch(url)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => callback(data))
                    .catch(error => console.error('Error fetching data:', error));
            }
            
            
            
            <!------------------------------------------Year----------------------->
            
            function updatePeriodLength(startYearSelectId, periodLengthSelectId) {
                var startYearSelect = document.getElementById(startYearSelectId);
                var selectedStartYear = parseInt(startYearSelect.value);
                var periodLengthSelect = document.getElementById(periodLengthSelectId);
                var periodLengthOptions = [5, 10, 15]; // Độ dài các khoảng thời gian tương đương
            
                // Xóa các tùy chọn trước đó
                periodLengthSelect.innerHTML = "";
            
                // Tạo các tùy chọn mới dựa trên năm bắt đầu và các khoảng thời gian tương đương
                periodLengthOptions.forEach(function(period) {
                    if (selectedStartYear + period <= new Date().getFullYear()) {
                        var option = document.createElement("option");
                        option.text = period;
                        option.value = period;
                        periodLengthSelect.add(option);
                    }
                });
            }

            <!---------------------------------------------------------------------------->

            function updatePeriodSimilarOptions(startYearSelectId, periodYearSelectId, periodSimilarSelectId) {
                var startYearSelect = document.getElementById(startYearSelectId);
                var periodYearSelect = document.getElementById(periodYearSelectId);
                var periodSimilarSelect = document.getElementById(periodSimilarSelectId);
            
                var selectedStartYear = startYearSelect.value ? parseInt(startYearSelect.value) : new Date().getFullYear(); // Sử dụng năm hiện tại nếu không có năm bắt đầu được chọn
                var selectedPeriodYear = periodYearSelect.value ? parseInt(periodYearSelect.value) : 0; // Sử dụng 0 nếu không có năm kết thúc được chọn
            
                // Clear previous options
                periodSimilarSelect.innerHTML = "";
            
                // Add options based on selected start year and period year
                for (var i = selectedStartYear + selectedPeriodYear; i >= selectedStartYear; i--) {
                    var option = document.createElement("option");
                    option.text = i - selectedStartYear;
                    option.value = i - selectedStartYear;
                    periodSimilarSelect.add(option);
                }
            }

            window.onload = function() {
                updatePeriodSimilarOptions('startYear', 'periodYear', 'periodSimilarSelect');
            };
            
            
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
                                                <select name="country" id="country" onchange="updateStates('country', 'state')">
                                                    <option value="" selected disabled>Select a country</option>""";
                                                            ArrayList<String> ctrName = jdbc.getCountries();
                                                            for (String country : ctrName) {
                                                                html += "<option value='" + country + "'>" + country + "</option>";
                                                            }
                                                            html += """
                                                </select>
                                            </td>
                                        </tr>
                                        <tr id="state-container" class="hidden">
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
                                    </tr>
                                    <tr id="city-container" class="hidden">
                                          
                            
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
                                       
                                            <td>Sort by</td>
                                            <td>
                                                <select name="sort by">
                                                    <option value="#">Option</option>
                                                    <option value="#">Averange population</option>
                                                    <option value="#">Averange temperature</option>
                                                </select>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td>Start year</td>
                                            <td>
                                            <select id="startYear" name="Start Year" onchange="updatePeriodLength('startYear', 'periodYear'); updatePeriodSimilarOptions('startYear', 'periodYear', 'periodSimilarSelect')">
                                                    <option value="" selected disabled>Select a year</option>""";
                                                ArrayList<Integer>StarYear = jdbc.getYearPeriod();
                                                for (Integer perYear : StarYear) {
                                                    html += "<option>" + perYear + "</option>";
                                                }         
                                                    //<option value="#">Option</option>
                                                    
                                                html += """
                                                </select>
                                                  
                                            </td>
                                            <td>Number years period similiar</td>
                                            <td>
                                            <select id="periodYear" name="Period Year" onchange="updatePeriodSimilarOptions('startYear', 'periodYear', 'periodSimilarSelect')">
                                                    <option value="" selected disabled>Select a year</option>""";
        
                                                    ArrayList<Integer>PerYear = jdbc.getYearPeriod();
                                                for (Integer perYear : PerYear) {
                                                    html += "<option>" + perYear + "</option>";
                                                }         
                                                    //<option value="#">Option</option>
                                                    
                                                html += """
                                                
                                                </select>
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
            <div>
            <table>
                <h1>Results</h1>
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
                   
                </tbody>
            </table>
        </div>
        
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