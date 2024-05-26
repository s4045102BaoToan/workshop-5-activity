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

public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";
    JDBCConnection jdbc = new JDBCConnection();
    @Override
    public void handle(Context context) throws Exception {
        
        DecimalFormat df = new DecimalFormat("#,###");
        String html = "<html>";
        html = html + """
            <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Demeter Data</title>
            <link rel="stylesheet" href="style.css">
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
                            <a href="test.html">Test</a>
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
                """;
                  
            html = html + "</div>";
                
            html = html + "<div class='Subtask'>";
            html = html + "<div class='tableInfo'>";

            ArrayList<globalTemp> gStart = getStartYear();
            ArrayList<globalTemp> gEnd = getEndYear();
            html = html + "    <h1 id='subtasktitle'>Data available from " + gStart.get(0).getYear() + " to " + gEnd.get(0).getYear();          

            html = html + "</h1>";
               
            html = html + "</div>";
            
            html = html + """
            <div class="subtask1A">
                <table class="table1">
                        <div class="global">Global Temperature</div><br>
                        <img src="1AIconTemp.png" class="pic" alt="" height="80" width="80">
            """;
            
            html = html + "    <tr>";
            html = html + "        <th>" + gStart.get(0).getYear() + "</th>";
            html = html + "        <th>" + gStart.get(0).getTemp() + "&deg;C" + "</th>";
            html = html + "    </tr>";
            html = html + "    <tr>";
            html = html + "        <th>" + gEnd.get(0).getYear() + "</th>";
            html = html + "        <th>" + gEnd.get(0).getTemp() + "&deg;C" + "</th>";
            html = html + "    </tr>";
        
            html = html + """
                </table>
                </div>
                """;

                //---------------------------------------------------------------------------------------------------------->

            html = html + "<div class='tableInfo'>";

            ArrayList<Population> gStart1 = StartYearPopul();
            ArrayList<Population> gEnd1 = EndYearPopul();
            html = html + "    <h1 id='subtasktitle'>Data available from " + gStart1.get(0).getYear() + " to " + gEnd1.get(0).getYear();          

            html = html + "</h1>";
               
            html = html + "</div>";
            
            html = html + """
            <div class="subtask1A">
                    <table class="table3">
                        <div class="global">Global population</div><br>
                            <img src="1AIconPop.png" class="pic" alt="" height="80" width="80">
                    """;
                            
            ArrayList<Population> startPopulations = StartYearPopul();
            ArrayList<Population> endPopulations = EndYearPopul();                    
        
            html = html + "    <tr>";
            html = html + "        <th>" + startPopulations.get(0).getYear() + "</th>";
            html = html + "        <th>" + df.format(startPopulations.get(0).getPopualtion()) + "</th>";
            html = html + "    </tr>";
            html = html + "    <tr>";
            html = html + "        <th>" + endPopulations.get(0).getYear() + "</th>";
            html = html + "        <th>" + df.format(endPopulations.get(0).getPopualtion()) + "</th>";
            html = html + "    </tr>";
        
            html = html + "    </table>";
            html = html + "</div>";
            html = html + """
            <div class="subtask1A">               
            </div>
            </div>
        
            <!---------------------------------------------------------------------------------------------------------->
        
            <div class="SubtaskStart">
                <img src="image.png" class="bear" alt="Logo" width=30% height=50%>
                <div class="mid">
                    <div class="in">Global temperature has been rising</div>
                    <div class="normal">We provide a database of records in the span of 260 years on global temperatures and population change.<br></div>
                
                <div class="link">For more information <a href="Global.html">Start now.</a></div>
                 
                </div>  
                </div>
                
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
        </html>
        
                """;


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }


    /**
     * Get the StartYear, StartTemp, EndYear, EndTemp in the database.
     */
    public ArrayList<globalTemp> getStartYear(){
        ArrayList<globalTemp> startYear = new ArrayList<globalTemp>(); 
        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)){
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                SELECT year, AvgLandTemp
                  FROM globaltemp
                  WHERE year = (select min(year) from globaltemp) 
            """;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {   
                int beginYear = results.getInt("year");
                double beginYearTemp = results.getDouble("AvgLandTemp");

                globalTemp gTemp = new globalTemp(beginYear, beginYearTemp);
                startYear.add(gTemp);
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return startYear;
    }

    public ArrayList<globalTemp> getEndYear(){
        ArrayList<globalTemp> endYear = new ArrayList<globalTemp>();
        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)){
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                SELECT year, AvgLandTemp
                  FROM globaltemp
                  WHERE year = (select max(year) from globaltemp)
            """;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int endyear = results.getInt("year");
                double endTemp = results.getDouble("AvgLandTemp");

                globalTemp gTemp = new globalTemp(endyear, endTemp);
                endYear.add(gTemp);
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return endYear;
    }

    //Get StartYear, Start Popualtion, EndYear, End Population in the database
    public ArrayList<Population> StartYearPopul() {
        ArrayList<Population> populations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                SELECT min(pyear) AS Year, population
                  FROM population
                  WHERE CountryID = (SELECT ID FROM Country WHERE CountryName = 'World');
                    """;

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int startYear = result.getInt("Year");
                long startPop = result.getLong("population");
                Population population = new Population(startYear, startPop);
                
                populations.add(population);
            }
        } catch (SQLException e) {
        // TODO: handle exception
            System.err.println(e.getMessage());
        }

        return populations;
    }

    public ArrayList<Population> EndYearPopul() {
        ArrayList<Population> populations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                SELECT max(pyear) AS Year, population
                  FROM population
                  WHERE CountryID = (SELECT ID FROM Country WHERE CountryName = 'World');
                    """;

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int endYear = result.getInt("Year");
                long endPop = result.getLong("population");
                Population population = new Population(endYear, endPop);
                
                populations.add(population);
            }
        } catch (SQLException e) {
        // TODO: handle exception
            System.err.println(e.getMessage());
        }

        return populations;
    }

}
