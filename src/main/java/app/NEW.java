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
public class NEW implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/NEW.html";

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
            <link rel="stylesheet" href="NEW.css">
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
            
            <script> 
            
            function createRegionForms() {
                var regionNumsSelect = document.getElementById('RegionNums');
                var numberOfRegions = regionNumsSelect.value;
                var regionFormsDiv = document.getElementById('regionForms');
    
                // Clear any existing forms
                regionFormsDiv.innerHTML = '';
                var form = document.createElement('form');
                    form.action = '/NEW.html';
                    form.method = 'post';
                    
                    var formDiv = document.createElement('div');
                    formDiv.className = 'REGS';

                for (var i = 0; i < numberOfRegions; i++) {
                
                    
                    var hiddenInput = document.createElement('input');
                    hiddenInput.type = 'hidden';
                    hiddenInput.name = 'regionNum';
                    hiddenInput.value = numberOfRegions;
                    form.appendChild(hiddenInput);
    
                    var regionSelect = document.createElement('select');
                    regionSelect.name = 'regionSelect' + i;
                    regionSelect.id = 'regionSelect' + i;
                    var regionOptions = ['Select a region', 'Global', 'Country', 'State', 'City'];

                    for (var j = 0; j < regionOptions.length; j++) {

                        var option = document.createElement('option');
                        option.value = regionOptions[j];
                        option.text = regionOptions[j];
                        if (j === 0) {
                            option.selected = true;
                            option.disabled = true;
                        }
                        regionSelect.appendChild(option);
                    }
                    form.appendChild(regionSelect);
    
                    var periodsDiv = document.createElement('div');
                    periodsDiv.className = 'periods';
    
                    var periodsLabel = document.createElement('label');
                    periodsLabel.htmlFor = 'periods';
                    periodsLabel.textContent = 'Number of Periods:';
                    periodsDiv.appendChild(periodsLabel);
    
                    var periodsSelect = document.createElement('select');
                    periodsSelect.name = 'periods';
                    periodsSelect.id = 'periods' + i;
                    periodsSelect.onchange = function () {
                        createYearLengthInputs(this);
                    };
                    var option = document.createElement('option')
                        option.value = '';
                        option.text = 'Please choose a number of periods';
                        option.disabled = true;
                        option.selected = true;
                        periodsSelect.appendChild(option);
                    for (var k = 1; k <= 5; k++) {
                        var option = document.createElement('option');
                        option.value = k;
                        option.text = k;
                        periodsSelect.appendChild(option);
                    }
                    periodsDiv.appendChild(periodsSelect);
    
                    var yearLengthsDiv = document.createElement('div');
                    yearLengthsDiv.id = 'yearLengths' + i;
                    periodsDiv.appendChild(yearLengthsDiv);
    
                    form.appendChild(periodsDiv);
                    formDiv.appendChild(form);
                    regionFormsDiv.appendChild(formDiv);
                }
    
                // Add a single submit button for all forms
                var submitButton = document.createElement('button');
                submitButton.type = 'submit';
                submitButton.textContent = 'Submit All';
                form.appendChild(submitButton);
                regionFormsDiv.appendChild(form);


                var input = document.createElement('input');
                    input.type = 'number';
                    input.name = 'yearLength';
                    input.id =  'yearLength';
                    input.placeholder = 'Enter year length';
                    form.appendChild(input);
                  
            }
            
            function createYearLengthInputs(selectElement) {
                var numberOfPeriods = selectElement.value;
                var yearLengthsDiv = document.getElementById('yearLengths' + selectElement.id.replace('periods', ''));
    
                // Clear any existing input boxes
                yearLengthsDiv.innerHTML = '';
                selectElement.name = 'periodLength' + selectElement.id.replace('periods', '');
                var parentRegionIndex = selectElement.id.replace('periods', '');
        
                
                for (var i = 1; i <= numberOfPeriods; i++) {
                   
                    var startingYearSelect = document.createElement('select');
                    startingYearSelect.name = 'startingYear' + parentRegionIndex + '_' + i;
                    startingYearSelect.id = 'startingYear' + parentRegionIndex + '_' + i;
                    startingYearSelect.innerHTML = generateYearOptions();
                    yearLengthsDiv.appendChild(startingYearSelect);
                }
            }
            
            function generateYearOptions() {
                var options = '<option value="" selected disabled>Select starting year</option>';
                for (var year =""";
                ArrayList<Integer> yearN = jdbc.getYearNumb();
                html += yearN.get(0)+"; year <= " + yearN.get(yearN.size()-1) +"; year++) {";
                    html += """
                    options += '<option value="' + year + '">' + year + '</option>';
                }
                return options;
            }
        
            </script>
            
      
      
            <div class='REGNUM'>
              
                    <label for='RegionNums'>Number of regions</label>
                    <form action = "/NEW.html" method="post">
                    <select id='RegionNums' name='RegionNums' onchange="createRegionForms()">
                        <option value="" selected disabled>Select number of regions</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                    </form>
            
            </div>
            
            <div id="regionForms">
                <!-- Region forms will be dynamically created here -->
            </div>
          """;
            String region1 = context.formParam("regionSelect0");
            String region2 = context.formParam("regionSelect1");
            String region3 = context.formParam("regionSelect2");
            String region4 = context.formParam("regionSelect3");
            String region5 = context.formParam("regionSelect4");
             

            String period0_1 = context.formParam("startingYear0_1");
            String period0_2 = context.formParam("startingYear0_2");
            String period0_3 = context.formParam("startingYear0_3");
            String period0_4 = context.formParam("startingYear0_4");
            String period0_5 = context.formParam("startingYear0_5");

            String period1_1 = context.formParam("startingYear1_1");
            String period1_2 = context.formParam("startingYear1_2");
            String period1_3 = context.formParam("startingYear1_3");
            String period1_4 = context.formParam("startingYear1_4");
            String period1_5 = context.formParam("startingYear1_5");

            String period2_1= context.formParam("startingYear2_1");
            String period2_2 = context.formParam("startingYear2_2");
            String period2_3 = context.formParam("startingYear2_3");
            String period2_4 = context.formParam("startingYear2_4");
            String period2_5 = context.formParam("startingYear2_5");

            String period3_1 = context.formParam("startingYear3_1");
            String period3_2 = context.formParam("startingYear3_2");
            String period3_3 = context.formParam("startingYear3_3");
            String period3_4 = context.formParam("startingYear3_4");
            String period3_5 = context.formParam("startingYear3_5");

            String period4_1 = context.formParam("startingYear4_1");
            String period4_2 = context.formParam("startingYear4_2");
            String period4_3 = context.formParam("startingYear4_3");
            String period4_4 = context.formParam("startingYear4_4");
            String period4_5 = context.formParam("startingYear4_5");

            String yearLength = context.formParam("yearLength");


          
            








            html +="""
            <!----------------------------------------------------------------------------------------------------------->
                <div class='output'>
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
