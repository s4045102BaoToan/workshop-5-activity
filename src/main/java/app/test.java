
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
public class test implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/test.html";

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
        </body>
        
            
            <!----------------------------------------------------CONTENT------------------------------------------------------>
            
            <div class="test">
            <form action= '/test.html' method = 'post'>
                <label for ='regionNum'>Number of regions</label>
                <select id='regionNum' name = 'regionNum'>
                    <option value="" selected disabled>Select number of regions</option>
                    <option >1</option>
                    <option >2</option>
                    <option >3</option>
                    <option >4</option>
                    <option >5</option> 
                    
                </select>
                <button type="submit">Submit</button>
            </form>
        </div>
                    """;
                   
                    String regNum = context.formParam("regionNum");
                    if(regNum == null || regNum == "" ){
                        html += "<p></p>";
                    }
                    else {
                        html += "<div class='regions'>";
                    for(int i=0 ;i<Integer.parseInt(regNum);++i){
                        html += "<form action= '/test.html' method = 'post'>";
                        html+="<label for = 'regions'>Region<label>";
                        html += "<select id='regions' name='regions' >";
                        html += "<option value='' selected disabled>Select a region</option>";
                        html += "<option>Global</option>";
                        html += "<option>Country</option>";
                        html += "<option>State</option>";
                        html += "<option>City</option>";
                        html += "</select><br>";
                        html+="<label for='startyear'>Start year</label>";
                        html+= "<select id ='startyear' name = 'startyear'>";
                        html+="<option select disabled>Please select a start year<option>";
                        JDBCConnection jdbc = new JDBCConnection();
                        ArrayList<Integer> yearArray = jdbc.getYearNumbGlobalNotNull();
                        for(int year : yearArray){
                            html += "<option>"+ year +"</option>";
                        }
                        html +="</select><br>";
                        
                        html+="<label for='period_length'>Length of period </label>";
                        html += "<select id='period_length' name = 'period_length'>" ;
                        
                        html += "<option select disabled>Please choose the length of the period</option>";
                        html+="";
                        for(int z = 1; z < 6; z++){
                            html+="<option>"+ z +"</option>";
                        }
                        html += "</select><br>";
                        
                    }       
                    html += "</select>   <button type='submit'>Submit</button>"; 
                    html += "</form>";
                    html += "</div>";
                    }

                    String regions = context.formParam("regions");
                    String startYear = context.formParam("startyear");
                    String pLength = context.formParam("period_length");

                    System.out.println("Testing to get region :" + regions);
                    System.out.println("Testing to get starting year :" + startYear);
                    System.out.println("Testing to get period length :" + pLength);

                    System.out.println(outPutAVGTemp(regions, Integer.parseInt(startYear), Integer.parseInt(pLength)));

            html += html  + """
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
    public String outPutAVGTemp(String region, int startYear, int periodLength){
        JDBCConnection jdbc = new JDBCConnection();
        

        ArrayList<Integer> outputAVG = jdbc.getArrayListOfYearsAndTempInPeriod(region, startYear, periodLength);
        
        String zzz = "";
        for( int grr : outputAVG){
            zzz = zzz + String.valueOf(grr) + ", ";
        }
        return zzz;
    }

}
