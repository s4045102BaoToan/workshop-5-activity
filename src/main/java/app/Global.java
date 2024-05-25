package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class Global implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/Global.html";
    
    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        JDBCConnection jdbc = new JDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###");
        
        String html = "<html>";

        // Add some Head information
        html = html + """
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Demeter Data</title>
            <link rel="stylesheet" href="global.css">
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
                        
                        for (var i = 1; i < yearOptions.length; i++) {
                            var option = document.createElement('option');
                            option.value = yearOptions[i];
                            option.text = yearOptions[i];
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
                            <a href="/Global.html">Global</a>
                            <a href="/Country.html">Country</a>
                            <a href="/State.html">State</a>
                            <a href="/City.html">City</a>
                        </div>
                    </div>
                    <div class="dropdown">
                        <button class="dropbtn">Advance</button>
                        <div class="advance-content">
                            <a href="/AvgtempPopul.html">Temperature &amp; population changes</a>
                            <a href="/period similar.html">Periods with similar temperature &amp; population</a>
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
            <div class="nav-container">
                <div class="navigation-tab">
                    <a href="/Global.html" class="navigation" id="glo">GLOBAL</a>
                    <a href="/Country.html" class="navigation" id="coun">COUNTRY</a>
                    <a href="/State.html" class="navigation" id="sta">STATE</a>
                    <a href="/City.html" class="navigation" id="city">CITY</a>
                </div>
            </div>
            <body-country>
                <div class="container">        
                    <div class="input">
                        <div class="search-button">
                            <table class="option-table">
                            <form action='/Global.html' method='post'>
                                <tbody>
                                    <tr>
                                        <td <label for ='start_year'>Year</label></td>
                                        <td>
                                            <select id='start_year' name='start_year' onchange="updateEndYearOptions('start_year', 'end_year')">
                                                <option value="" selected disabled>Select a year</option>""";
                                                html = html + "";
                                                ArrayList<Integer> yearNumb = getPopYear();
                                                for(int yearNums : yearNumb){
                                                    html = html + "<option>" + yearNums + "</option>";
                                                }
                                                html = html + """                                                                  
                                            </select>
                                        </td>
                                        <td <label for ='end_year'>To</label></td>
                                        <td> """;
                                       
                                            html = html + """

                                                <select id='end_year' name='end_year'>
                                                <option value="" selected disabled>Select a year</option>""";
                                            for(int yearNums : yearNumb){
                                                html = html + "<option>" + yearNums + "</option>";
                                            }
                                            html = html + """    
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <button type="submit" class="search-form">Search</button>
                            </div>
                        </div>
                        </form>
                        </div>
            </body-country>
        
        
            <!------->
            <div class="output-global-pop">
                <h1 class="From">From</h1>
                    <div class="innerFlexbox1">""";
                    String start_year = context.formParam("start_year");
                    String end_year = context.formParam("end_year");
                    if(start_year == null || start_year == ""){
                        html = html + "<h1>Choose a starting year</h1>";
                    }
                    else if(Integer.parseInt(start_year) > Integer.parseInt(end_year)){
                        html = html + "<h1>Invalid year</h1>";
                    }
                    else{
                       html = html + "<h1>" + start_year + "</h1>";
                    }
                        html = html + """
                        <img src="arrow.png" class="arrow" width="40px" height="20px">""";
                    if(end_year == null || end_year == ""){
                        html = html + "<h1>Choose an ending year</h1>";
                    }     
                    else if(Integer.parseInt(end_year) < Integer.parseInt(start_year)){
                        html = html + "<h1>Invalid year</h1>";
                    }
                    else{  
                     html = html + "<h1>" + end_year + "</h1>";
                    }
                     html = html + """
                    </div>
        
                    <div class="containerOuterMost2-3">
                        <div class="containerFlex2-3">
                        </div>
                        <div class="containerFlex2-3">
                            <div class="innerFlexbox2">
                                <img src="popicon.png" height="50" width="50">
                                <h1 id="AvgCchange">Avg <br>population</br> change</h1>
                            </div>
                                <div class="innerFlexbox3">""";
                               
                                if (start_year == "" || start_year == null || end_year == "" || end_year == null|| Integer.parseInt(start_year) > Integer.parseInt(end_year)|| Integer.parseInt(end_year) < Integer.parseInt(start_year)) {
                                    html = html + "<h1 class='avgValue'> No Results</h1>";
                                } else {
                                    String formattedNumber = df.format(outputPopChange(Integer.parseInt(context.formParam("start_year")),Integer.parseInt(context.formParam("end_year"))));
                                    html = html + "<h1 class='avgValue'>"+ formattedNumber
                                     + "</h1>";
                                     System.out.println(outputPopChange(1960,2013));
                                }
                                    html = html +"""  
                                </div>
                            </div>
                    </div>
            </div>
            <body-country>
                <div class="container">        
                    <div class="input">
                        <div class="search-button">
                            <table class="option-table">
                            <form action='/Global.html' method='post'>
                                <tbody>
                                    <tr>
                                        <td <label for ='Tstart_year'>Year</label></td>
                                        <td>
                                            <select id='Tstart_year' name='Tstart_year' onchange="updateEndYearOptions('Tstart_year', 'Tend_year')">
                                                <option value="" selected disabled>Select a year</option>""";
                                                html = html + "";
                                                ArrayList<Integer> year = jdbc.getYearNumb();
                                                for(int yearNums : year){
                                                    html = html + "<option>" + yearNums + "</option>";
                                                }
                                                html = html + """                                                                  
                                            </select>
                                        </td>
                                        <td <label for ='Tend_year'>To</label></td>
                                        <td> """;
                                       
                                            html = html + """

                                                <select id='Tend_year' name='Tend_year'>
                                                <option value="" selected disabled>Select a year</option>""";
                                            for(int yearNums : year){
                                                html = html + "<option>" + yearNums + "</option>";
                                            }
                                            html = html + """    
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <button type="submit" class="search-form">Search</button>
                            </div>
                        </div>
                        </form>
                        </div>
            </body-country>
        
            <div class="output-global-temp">
                <h1 class="From">From</h1>
                    <div class="innerFlexbox1">""";
                    String Tstart_year = context.formParam("Tstart_year");
                    String Tend_year = context.formParam("Tend_year");
                    if(Tstart_year == null || Tstart_year == ""){
                        html = html + "<h1>Choose a starting year</h1>";
                    }
                    else if(Integer.parseInt(Tstart_year) > Integer.parseInt(Tend_year)){
                        html = html + "<h1>Invalid year</h1>";
                    }
                    else{
                       html = html + "<h1>" + Tstart_year + "</h1>";
                    }
                        html = html + """
                        <img src="arrow.png" class="arrow" width="40px" height="20px">""";
                        if(Tend_year == null || Tend_year == ""){
                            html = html + "<h1>Choose an ending year</h1>";
                        }     
                        else if(Integer.parseInt(Tend_year) < Integer.parseInt(Tstart_year)){
                            html = html + "<h1>Invalid year</h1>";
                        }
                        else{  
                         html = html + "<h1>" + Tend_year + "</h1>";
                        }
                        
                            html = html + """
                     </div>
        
                    <div class="containerOuterMost2-3">
                        <div class="containerFlex2-3">
                        <div class="innerFlexbox2">
                            <img src="tempicon.png" height="50" width="50">
                            <h1 id="AvgCchange">Avg &#176C </br>change</h1>
                        </div>
                            <div class="innerFlexbox3">""";
                            if (Tstart_year == "" || Tstart_year == null || Tend_year == "" || Tend_year == null|| Integer.parseInt(Tstart_year) > Integer.parseInt(Tend_year)|| Integer.parseInt(Tend_year) < Integer.parseInt(Tstart_year)) {
                                html = html + "<h1 class='avgValue'> No Results</h1>";
                            } else {
                                String formattedNumber1 = df.format(outputTempChange(Integer.parseInt(context.formParam("Tstart_year")),Integer.parseInt(context.formParam("Tend_year"))));
                                html = html + "<h1 class='avgValue'>"+ formattedNumber1 + "&#176 C"
                                 + "</h1>";
                            }
                                html = html + """
                            </div>
                        </div>
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
                                <li><a href="/Global.html">Global</a></li>
                                <li><a href="/Country.html">Country</a></li>
                                <li><a href="/State.html">State</a></li>
                                <li><a href="/City.html">City</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="sitemap-section">
                        <h3>Advance</h3>
                        <div class="sadvance-content">
                            <ul>
                                <li><a href="/AvgtempPopul.html">Temperature changes <br>in different time periods</a></li>
                                <li><a href="/period.html">Periods with similar <br>population and temperature</a></li>
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

    //Get output for population change and temperature change
    public long outputPopChange(int sYear, int eYear){     
        JDBCConnection jdbc = new JDBCConnection();                   
        ArrayList<Long> popChange  = jdbc.getPopChangeBySyearandEyear(sYear, eYear);
        long pop = popChange.get(1) - popChange.get(0);
        return pop;
    }
    public double outputTempChange(int sYear, int eYear){
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Double> tempChange = jdbc.getTempChangeBySyearandEyear(sYear,eYear);
        return tempChange.get(0);
    }

    //Get StartYear and EndYear for population (it diferrent to getYearNumb method because data for population start from 1960)
    public ArrayList<Integer> getPopYear() {
        ArrayList<Integer> getYear = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                SELECT DISTINCT pyear AS year
                  FROM population
                    """;

            ResultSet result = statement.executeQuery(query);
                
            while (result.next()) {
                getYear.add(result.getInt("year"));
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return getYear;
    }
    
}
