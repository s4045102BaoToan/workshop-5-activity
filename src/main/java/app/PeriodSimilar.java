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

<div class='test'>
    <form action='/period.html' method='post'>
        <select id='test' name = 'test'>
            <option value="" selected disabled>Select a region</option>
            <option value="Country">Country</option>
            <option value="State">State</option>
            <option value="City">City</option>
        </select>
            <button type="submit">Submit</button>
    </form>
</div>
<div class ='test2'>""";
String a = context.formParam("test");
JDBCConnection jdbc = new JDBCConnection();
if(a == null){
    html += "<p>NO DATA</p>";
}
else if (a.equals("Country")){
    
    html += "<form action='/period.html' method='post'>";
    html +=    "<select id='country' name = 'country'>";
    html +=        "<option value='' selected disabled>Select a country</option>";
                    ArrayList<String> ctr = jdbc.getCountries();
                    for(int i = 0; i < ctr.size(); ++i){
                        html += "<option>" + ctr.get(i) + "</option>";
                    }
    html +=    "</select>";
    html +=     "<select id = 'startingYear' name ='startingYear'>";
                ArrayList<Integer> year = jdbc.getCountryYear();
                html += "<option value='' selected disabled>Select a starting year</option>";
                for(Integer years : year ){
                    html += "<option value=" + "'" + years + "'>" + years + "</option>"; 
                }
    html +=         "</select>";
    html +=        "<button type='submit'>Submit</button>";
    html +=    "</form>";
}
 else if (a.equals("State")){
    html += "<form action='/period.html' method='post'>";
    html +=    "<select id='state' name = 'state'>";
    html +=        "<option value='' selected disabled>Select a state</option>";
                    ArrayList<statee> sta = jdbc.getCountryState();
                    for( statee stas : sta){
                        html += "<option value=" + "'" + stas.getState() + "'>" + stas.getCountry() + " - " + stas.getState() + "</option>";
                    }
    html +=    "</select>";
    html +=        "<button type='submit'>Submit</button>";
    html +=    "</form>";
                }
 else if(a.equals("City")){
    html += "<form action='/period.html' method='post'>";
    html +=    "<select id='city' name = 'city'>";
    html +=        "<option value='' selected disabled>Select a city</option>";
                    ArrayList<cityy> cit = jdbc.getCountryCity();
                    for(cityy city : cit){
                        html += "<option value ='" + city.getCity() + "'>" + city.getCountry() + " - " + city.getCity() + "</option>";
                    }
    html +=    "</select>";
    html +=        "<button type='submit'>Submit</button>";
    html +=    "</form>";
 }
System.out.println(a);
html += """
</div>


            
            
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