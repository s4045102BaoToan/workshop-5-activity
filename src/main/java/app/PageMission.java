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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        JDBCConnection jdbc = new JDBCConnection();
        String html = "<html>";

        // Add some Head information
        html = html + """
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Demeter Data</title>
            <link rel="stylesheet" href="persona.css">
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
                    <a href="/mission.html">About</a>
                </div>
            </div>
            <div class="ppp">
                <h1>About us</h1>
                <h2>How our website address the social challenge</h2>
                <h3>
                Are you looking for information related to temperature and population but finding databases overwhelming ?<br>
                With our website, we offer a more user-friendly way to search, calculate and view trends in the available databases provided by
                <a href=https://www.kaggle.com/datasets/berkeleyearth/climate-change-earth-surface-temperature-data>kaggle</a> and 
                <a href=https://www.worldbank.org/en/home>The World Bank</a>.<br>
                From there, we hope to spread awareness and information to people about the reality of global warming, hopefully changing
                 people's views and lifestyle to minimize its impact one small step at a time.
                </h3>
            </div>
            <div class="personas">
                <h1>Our personas</h1>
            
                <div class="personabox">
                    <img src="persona2.png" id="persona1"height="270" width="270">
                    <img src="persona3.png" id="persona2" height="270" width="270">
                    <img src="persona1.png" id="persona3" height="270" width="270">
                </div>
               
                    <div class="titlebox" id="nameage">""";
                    
                        ArrayList<Persona> psns = jdbc.getPersonaInfo();
                        for(Persona psn : psns){
                            html = html + "<p1>-&nbsp;Name: " + psn.name + "<br><br>-&nbsp;Age: " + psn.age+ "<br><br>-&nbsp;Background: " + psn.background
                            + "<br><br>-&nbsp;Needs: " + psn.needs + "<br><br>-&nbsp;Goals: " + psn.goals +"</p1>" ;
                        }
                
                        html = html + """
                            
                        
                    </div>
            </div>
            <div class="team">
                <div class="designer-box">
                    <p>DESIGNERS</p>
                </div>""";
                html = html + """
                    <div class="members1">
                        """;
                    
                    ArrayList<Student> stds = jdbc.getSInfo();
                    ArrayList<String> a = new ArrayList<String>();
                    for (Student std : stds) {
                        a.add(std.getName());
                    }
                    ArrayList<String> b = new ArrayList<String>();
                    for (Student std : stds) {
                        b.add(std.getId());
                    }
                    for (int i = 0 ; i<a.size() ;++i){
                    html= html + """
                    <div class="rmit"><img src="rmit.png" id="rmit1" height="90" width="90"><br>
                            """;
                    html = html + "<p>Name: " + a.get(i) +"<br>ID: "+ b.get(i) + "</p>";                           
                    html = html +"""
                    </div>
                    """;
                    if (i==3){
                        break;
                    }
                    }
                    html = html + """
                            </div>
                            """;
                   
                        
                        html += """
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
                                <li><a href="/mission.html">Our mission</a></li>
                                <li><a href="/mission.html">Persona</a></li>
                                <li><a href="/mission.html">Team members</a></li>
                            </ul>
                        </div>
                        <p>&copy; 2024 Demeter Data. All rights reserved.</p>
                    </div>
                </div>
              
            </footer>
        </body>
                """;

        
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
