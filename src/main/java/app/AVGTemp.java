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
public class AVGTemp implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/AvgtempPopul.html";

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
            <link rel="stylesheet" href="avgtemp.css">
            <link rel="icon" type="image/x-icon" href="icon.ico">
            
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
        
            
            <!----------------------------------------------------CONTENT------------------------------------------------------>
            
            <body-country>
                <div class="container">        
                <form action="/AvgtempPopul.hmtl" method="post">
                    <div class="input">
                    <div class="search-button">
                            <table class="option-table">
                                <tbody>
                                    <tr>
                                        <td>Number of region</td>
                                        <td>
                                            <select name="Number of region">
                                                <option value="" selected disabled>Options</option>
                                                <option value="#">Option</option>
                                                
                                            </select>
                                        </td>
                                        <td>Number of period</td>
                                        <td>
                                            <select name="Number of period">
                                                <option value="#">Options</option>
                        
                                        </td>
                                    </tr>
        
                                    <tr>
                                        <td>Start year</td>
                                        <td>
                                            <select name="Start year">
                                                <option value="#">Choose period</option>
                                                """;
                                                    JDBCConnection jdbc = new JDBCConnection();
                                                    ArrayList<Integer> yearOption = jdbc.getGlobalYearTempPeriod();
                                                    for(int year : yearOption){
                                                        html = html + "<option>" + year + "</option>";
                                                    }

                                            html = html + """
                                                
                                            </select>
                                        </td>
        
                                        <td>Year length</td>
                                        <td>
                                            <select name="Year length">
                                                <option value="#">Choose period</option>
                                                
                                            </select>
                                        </td>
        
                                    </tr>
                                    <tr>
                                        <td>Sort by</td>
                                        <td>
                                            <select name="sort by">
                                                <option value="#">Global</option>
                                                <option value="#">Country</option>
                                                <option value="#">State</option>
                                                <option value="#">City</option>
                                                
                                            </select>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            
                            <button type="submit" class="search-form">
                            Search</button>
                            </div>
                            </div>
                    </form>
                        </div>
            </body-country>
        
        
            <!----------------------------------------------------------------------------------------------------------->
                <body>
                    <table>
                        <thead>
                            <tr>
                                <th>Rank</th>
                                <th>Region</th>
                                <th>Average Temperature</th>
                                <th>Average Population</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="rank"></td>
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

}
