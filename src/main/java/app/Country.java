package app;

import java.util.ArrayList;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.text.DecimalFormat;

public class Country implements Handler {

    public static final String URL = "/Country.html";

    public void handle(Context context) throws Exception {
        DecimalFormat df = new DecimalFormat("#,###");
        String html = "<html>";

        html += """
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Demeter Data</title>
                <link rel="stylesheet" href="country.css">
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
                            option.value = yearOptions[i];
                            option.text = yearOptions[i];
                            endYearSelect.add(option);
                        }
                    }
                </script>

                <!----------------------------------------------------HEADER-------------------------------------------->

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
                                <a href="/period.html">Periods with similar temperature &amp; population</a>
                            </div>
                        </div>
                    </div>
                    <div class="about">
                        <a href="/mission.html">About</a>
                    </div>
                </div>
                <div class="menuSpace"></div>
                <div class="nav-container">
                    <div class="navigation-tab">
                        <a href="/Global.html" class="navigation" id="glo">GLOBAL</a>
                        <a href="/Country.html" class="navigation" id="coun">COUNTRY</a>
                        <a href="/State.html" class="navigation" id="sta">STATE</a>
                        <a href="/City.html" class="navigation" id="city">CITY</a>
                    </div>
                </div>

                <!----------------------------------------------------INPUT-TABLE-1-------------------------------------------->

                <div class="container">
                    <form action="/Country.html" method="post">
                        <div class="input">
                            <div class="search-button">
                                <table class="option-table">
                                    <tbody>
                                        <tr>
                                            <td>Country</td>
                                            <td>
                                                <select id='country' name='country'>
                                                    <option value="" selected disabled>Select a country</option>""";
        
                                                            JDBCConnection jdbc = new JDBCConnection();
                                                            ArrayList<String> ctrName = jdbc.getCountryName();
                                                            for (String CtrName : ctrName) {
                                                                html += "<option>" + CtrName + "</option>";
                                                            }
                                                            
                                                            html += """
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Year</td>
                                            <td>
                                                <select id='start_year' name='start_year' onchange="updateEndYearOptions('start_year', 'end_year')">
                                                    <option value="" selected disabled>Select a year</option>""";
        
                                                            ArrayList<Integer> ctrYear = jdbc.getCountryYearPopul();
                                                            for (Integer CtrYear : ctrYear) {
                                                                html += "<option>" + CtrYear + "</option>";
                                                            }
                                                            
                                                            html += """
                                                </select>
                                            </td>
                                            <td>To</td>
                                            <td>
                                                <select id='end_year' name='end_year'>
                                                    <option value="" selected disabled>Select a year</option>""";
        
                                                            ArrayList<Integer> ctrYear1 = jdbc.getCountryYearPopul();
                                                            for (Integer CtrYear : ctrYear1) {
                                                            html += "<option>" + CtrYear + "</option>";
                                                                }
                                                                
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
                </div>""";
                
                //----------------------------------------------------OUTPUT-TABLE-1--------------------------------------------//

        String country = context.formParam("country");
        String start_year = context.formParam("start_year");
        String end_year = context.formParam("end_year");

        html += "<div class='output-global-pop'>";
        if (country == null || country.isEmpty()) {
            html += "<h1 class='From'>Choose a country</h1>";
        } else {
            html += "<h1 class='From'>" + country + "</h1>";
        }
        html += "<div class='innerFlexbox1'>";
        if (start_year == null || start_year.isEmpty()) {
            html += "<h1>Choose a starting year</h1>";
        } else if (Integer.parseInt(start_year) > Integer.parseInt(end_year)) {
            html += "<h1>Invalid year</h1>";
        } else {
            html += "<h1>" + start_year + "</h1>";
        }
        html += "<img src='arrow.png' class='arrow' width='40px' height='20px'>";
        if (end_year == null || end_year.isEmpty()) {
            html += "<h1>Choose an ending year</h1>";
        } else if (Integer.parseInt(end_year) < Integer.parseInt(start_year)) {
            html += "<h1>Invalid year</h1>";
        } else {
            html += "<h1>" + end_year + "</h1>";
        }
        html += """
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
        if (start_year == null || start_year.isEmpty() || end_year == null || end_year.isEmpty() || Integer.parseInt(start_year) > Integer.parseInt(end_year) || Integer.parseInt(end_year) < Integer.parseInt(start_year) || country == null || country.isEmpty()) {
            html += "<h1 class='avgValue'> No Results</h1>";
        } else {
            html += "<h1 class='avgValue'>" + df.format(getCountryPopul(country, Integer.parseInt(context.formParam("start_year")), Integer.parseInt(context.formParam("end_year")))) + "</h1>";
        }
        html += """
                        </div>
                    </div>
                </div>
            </div>""";

            
            //----------------------------------------------------INPUT-TABLE-2--------------------------------------------//

        html += """
                <div class="container">
                    <form action="/Country.html" method="post">
                        <div class="input">
                            <div class="search-button">
                                <table class="option-table">
                                    <tbody>
                                        <tr>
                                            <td>Country</td>
                                            <td>
                                                <select id='country2' name='country2'>
                                                    <option value="" selected disabled>Select a country</option>""";
                                                        ArrayList<String> ctrName1 = jdbc.getCountryName();
                                                        for (String CtrName1 : ctrName1) {
                                                            html += "<option>" + CtrName1 + "</option>";
                                                        }
                                                            html += """
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Year</td>
                                            <td>
                                                <select id='start_year2' name='start_year2' onchange="updateEndYearOptions('start_year2', 'end_year2')">
                                                    <option value="" selected disabled>Select a year</option>""";
                                                        ArrayList<Integer> ctrYear2 = jdbc.getCountryYear();
                                                        for (Integer CtrYear2 : ctrYear2) {
                                                            html += "<option>" + CtrYear2 + "</option>";
                                                        }
                                                        html += """
                                                </select>
                                            </td>
                                            <td>To</td>
                                            <td>
                                                <select id='end_year2' name='end_year2'>
                                                    <option value="" selected disabled>Select a year</option>""";
                                                        ArrayList<Integer> ctrYear3 = jdbc.getCountryYear();
                                                        for (Integer CtrYear3 : ctrYear3) {
                                                            html += "<option>" + CtrYear3 + "</option>";
                                                        }
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
                </div>""";

                //----------------------------------------------------OUTPUT-TABLE-2--------------------------------------------//

        String country2 = context.formParam("country2");
        String start_year2 = context.formParam("start_year2");
        String end_year2 = context.formParam("end_year2");

        html += "<div class='output-global-temp'>";
        if (country2 == null || country2.isEmpty() || country2 == "") {
            html += "<h1 class='From'>Choose a country</h1>";
        } else {
            html += "<h1 class='From'>" + country2 + "</h1>";
        }
        html += "<div class='innerFlexbox1'>";
        if (start_year2 == null || start_year2.isEmpty()) {
            html += "<h1>Choose a starting year</h1>";
        } else if (Integer.parseInt(start_year2) > Integer.parseInt(end_year2)) {
            html += "<h1>Invalid year</h1>";
        } else {
            html += "<h1>" + start_year2 + "</h1>";
        }
        html += "<img src='arrow.png' class='arrow' width='40px' height='20px'>";
        if (end_year2 == null || end_year2.isEmpty()) {
            html += "<h1>Choose an ending year</h1>";
        } else if (Integer.parseInt(end_year2) < Integer.parseInt(start_year2)) {
            html += "<h1>Invalid year</h1>";
        } else {
            html += "<h1>" + end_year2 + "</h1>";
        }
        html += """
                </div>
                <div class="containerOuterMost2-3">
                    <div class="containerFlex2-3">
                        <div class="innerFlexbox2">
                            <img src="tempicon.png" height="50" width="50">
                            <h1 id="AvgCchange">Avg &#176 C </br>change</h1>
                        </div>
                        <div class="innerFlexbox3">""";
        if (start_year2 == null || start_year2.isEmpty() || end_year2 == null || end_year2.isEmpty() || Integer.parseInt(start_year2) > Integer.parseInt(end_year2) || Integer.parseInt(end_year2) < Integer.parseInt(start_year2) || country2 == null || country2.isEmpty()) {
            html += "<h1 class='avgValue'> No Results</h1>";
        } 
        else {
            if(getCountryoutput(country2, Integer.parseInt(context.formParam("start_year2")), Integer.parseInt(context.formParam("end_year2"))) == 9999 ){
                html += "<h1 class='avgValue'>" + "No data in these years</h1>" ;
            }
            else{
                String aaa = df.format(getCountryoutput(country2, Integer.parseInt(context.formParam("start_year2")), Integer.parseInt(context.formParam("end_year2"))));
            html += "<h1 class='avgValue'>" + aaa + "&#176 C</h1>";
            System.out.println(getCountryoutput(country2, Integer.parseInt(context.formParam("start_year2")), Integer.parseInt(context.formParam("end_year2"))));
        }
    }
        html += """
                        </div>
                    </div>
                </div>
            </div>

            <!----------------------------------------------------FOOTER-------------------------------------------->  

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
        </html>""";

        context.html(html);
    }

    public double getCountryoutput(String country, int startyear, int endyear) {
        JDBCConnection jdbc = new JDBCConnection();
        double ad = jdbc.getCountryChangeBySyearandEyear(startyear, endyear, country);
        return ad;
    }

    public int getCountryPopul(String country, int startyear, int endyear) {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Integer> as = jdbc.getCountrypopulChangeBySyearandEyear(startyear, endyear, country);
        return as.get(0);
    }
}
