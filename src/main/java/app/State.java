package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.DecimalFormat;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class State implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/State.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        DecimalFormat df = new DecimalFormat("#,###");
        String countryName = context.formParam("countryName");
                    String stateStartYear = context.formParam("stateStartYear");
                    String stateEndYear = context.formParam("stateEndYear");
                    String sortByTempCat = context.formParam("tempOpt");
                    String order = context.formParam("orderBy");
        String html = """
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Demeter Data</title>
            <link rel="stylesheet" href="state.css">
            <link rel="icon" type="image/x-icon" href="icon.ico">

            <!----------------------------------------------------SCRIPT-------------------------------------------->

                <script>
                    function updateEndYearOptions(startYearSelectId, endYearSelectId) {
                        var startYearSelect = document.getElementById(startYearSelectId);
                        var endYearSelect = document.getElementById(endYearSelectId);
                        var selectedStartYear = parseInt(startYearSelect.value);
                        
                        endYearSelect.innerHTML = '<option value="" selected disabled>Select a year</option>';
                        
                        var yearOptions = [];
                        for (var i = selectedStartYear; i <= 2013; i++) {
                            yearOptions.push(i);
                        }
                        
                        for (var i = 0; i < yearOptions.length; i++) {
                            var option = document.createElement('option');
                            option.value = yearOptions[i + 1];
                            option.text = yearOptions[i + 1];
                            endYearSelect.add(option);
                        }
                    }
                </script>
            
        </head>

        <!----------------------------------------------------BODY-------------------------------------------->
        
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
            <div class="search-button">
                <form action="/State.html" method="post">
                        <table class="option-table">
                            <tbody>
                                <tr>
                                    <td>Country</td>
                                    <td>
                                        <select id="countryName" name="countryName">
                                            <option value="" selected disabled>Select a country</option>""";
    JDBCConnection jdbc = new JDBCConnection();
    ArrayList<String> countryNameArrayList = jdbc.getCountryWithStates();
    for(String ctrnameString : countryNameArrayList){
        html = html + "<option>" + ctrnameString + "</option>";
    }
                        html = html + """
                                        </select>
                                    </td>
                                    
                                </tr>
                                <tr>
                                    <td>Year</td>
                                    <td>
                                        <select id="stateStartYear" name="stateStartYear" onchange="updateEndYearOptions('stateStartYear', 'stateEndYear')">
                                            <option value="" selected disabled>Select a year</option>""";
                                            ArrayList<Integer> stateYeArrayList = jdbc.getStateTempYear();
                                            for(int stateyear : stateYeArrayList){
                                                html = html + "<option>" + stateyear + "</option>";
                                            }
                                            
                                            html = html + """
                                        </select>
                                    </td>
                                    <td>To</td>
                                    <td>
                                        <select id="stateEndYear" name="stateEndYear">
                                            <option value="" selected disabled>Select a year</option>""";
                                                for(int stateyear : stateYeArrayList){
                                                    html = html + "<option>" + stateyear + "</option>";
                                                }

                                            html = html + """
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Temperature type</td>
                                    <td>""";
                                            
                                    html = html + """
                                        <select id="tempOpt" name="tempOpt">
                                            <option value="" disabled selected hidden>Option</option>
                                            <option value="stemp.AvgTemp" selected >Average Temperature</option>
                                            <option value="stemp.MinTemp">Minimum Temperature</option>
                                            <option value="stemp.maxTemp">Maximum Temperature</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Order by</td>
                                    <td>""";
                                            
                                            html = html + """
                                        <select id="orderBy" name="orderBy">
                                            <option value="" disabled selected hidden>Options</option>
                                            <option value=" DESC " selected>Descending</option>
                                            <option value=" ASC ">Ascending</option>
                                        </select>
                                    </td>
                                    
                                </tr>
                            </tbody>
                        </table>
                        <button type="submit" class="search-form">
                            Search</button> 
                </form>
                </div>
        
            </body-country>
        <!--------------------------OutPut--------------------------->
        
        <body>
        <table>
            <thead>
                <tr>
                    <th>Rank</th>
                    <th>State</th>
                    <th>Temperature in """; html = html + " " + stateStartYear + " " +
                    """
                        </th>
                        <th>Temperature in """; html = html + " "+stateEndYear + " " +
                        """
                            </th>
                    <th>Temperature difference</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    """;
                    System.out.println("Testing: Order is: " + order);
                    System.out.println("Testing: Column name is: " + sortByTempCat);
                    System.out.println();
                    if(countryName == null ||
                    stateStartYear == null ||
                    stateEndYear == null ||
                    countryName == "" ||
                    stateStartYear == "" ||
                    stateEndYear == "" ||
                    Integer.parseInt(stateEndYear) <= Integer.parseInt(stateStartYear)
                    )
                    {   
                        html = html + "<td>" + "Please select a valid value" + "</td>";
                        html = html + "<td>" + "Please select a valid value" + "</td>";
                        html = html + "<td>" + "Please select a valid value" + "</td>";
                        html = html + "<td>" + "Please select a valid value" + "</td>";
                        html = html + "<td>" + "Please select a valid value" + "</td>";
                        html = html + "</tr>";}
                    else{
                        if(outputStateTemp(countryName, Integer.parseInt(context.formParam("stateStartYear")), Integer.parseInt(context.formParam("stateEndYear")), sortByTempCat, order)== null ||
                        outputStateTemp(countryName, Integer.parseInt(context.formParam("stateStartYear")), Integer.parseInt(context.formParam("stateEndYear")), sortByTempCat, order) == ""){
                            html = html + "<th></th><th></th><th>NOTICE</th><td>NOTICE</th><th></th> <tr>";
                            html = html + "<td></td>";
                            html = html + "<td></td>";
                            html = html + "<td>" + "Australia's data only available from 1841 - 1970" + "</td>";
                            html = html + "<td>" + "Brazil's data only available from 1824-1897" + "</td>";
                            html = html + "<td></td>";
                            html = html + "</tr>"; 
                            html = html + "<td></td>";
                            html = html + "<td></td>";
                            html = html + "<td>" + "Canada's data only available from 1750 - 1853" + "</td>";
                            html = html + "<td>" + "china's data only available from 1816 - 1861" + "</td>";
                            html = html + "<td></td>";
                            html = html + "</tr>";
                            html = html + "<td></td>";
                            html = html + "<td></td>";
                            html = html + "<td>" + "India's data only available from 1769 - 1830" + "</td>";
                            html = html + "<td></td>";
                            html = html + "<td></td>";
                            html = html + "</tr>";
                            }   
                        
                        else{
                      html = html + outputStateTemp(countryName, Integer.parseInt(context.formParam("stateStartYear")), Integer.parseInt(context.formParam("stateEndYear")), sortByTempCat, order) ;
                        }
                       
                    }
                    html = html +""" 
                </tr>
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
        
        </html>
                """;
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
    public String outputStateTemp(String countryName,int sYear,int eYear, String sortByTempCat, String orderBy){
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<StateTempRelated> tempArrayList = jdbc.getStateTempArrayList(countryName, sYear, eYear, sortByTempCat, orderBy); //
       
       
        String zzz = "";
        int rank = 1;
        for (StateTempRelated temp : tempArrayList) {
            zzz = zzz + "<tr>" +
            "<td>" + rank + "</td>" +
            "<td>" + temp.getStateName() + "</td>"+ 
            "<td>" + temp.getStartTemp() +"</td>" +
            "<td>" + temp.getEndTemp() +"</td>" + 
            "<td>" + temp.getTempDif() + "</td>" + 
            "</tr>";
            rank++;
          
        }
        return zzz;
    }

}
