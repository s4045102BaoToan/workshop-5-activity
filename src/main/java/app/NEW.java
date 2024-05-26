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
            
            <title>Select Number of Regions and Periods</title>
            <script>
                function createRegionForms() {
                    var regionNumsSelect = document.getElementById('RegionNums');
                    var numberOfRegions = regionNumsSelect.value;
                    var regionFormsDiv = document.getElementById('regionForms');
        
                    // Clear any existing forms
                    regionFormsDiv.innerHTML = '';
        
                    for (var i = 0; i < numberOfRegions; i++) {
                        var formDiv = document.createElement('div');
                        formDiv.className = 'REGS';
        
                        var form = document.createElement('form');
                        form.action = '/AvgtempPopul.html';
                        form.method = 'post';
        
                        var hiddenInput = document.createElement('input');
                        hiddenInput.type = 'hidden';
                        hiddenInput.name = 'regionNum';
                        hiddenInput.value = numberOfRegions;
                        form.appendChild(hiddenInput);
        
                        var regionSelect = document.createElement('select');
                        regionSelect.name = 'Regions';
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
                }
        
                function createYearLengthInputs(selectElement) {
                    var numberOfPeriods = selectElement.value;
                    var yearLengthsDiv = document.getElementById('yearLengths' + selectElement.id.replace('periods', ''));
        
                    // Clear any existing input boxes
                    yearLengthsDiv.innerHTML = '';
        
                    for (var i = 1; i <= numberOfPeriods; i++) {
                        var startingYearSelect = document.createElement('select');
                        startingYearSelect.name = 'startingYear' + i;
                        startingYearSelect.innerHTML = generateYearOptions();
                        yearLengthsDiv.appendChild(startingYearSelect);
        
                        var input = document.createElement('input');
                        input.type = 'number';
                        input.name = 'yearLength' + i;
                        input.placeholder = 'Enter year length for period ' + i;
                        yearLengthsDiv.appendChild(input);
                        yearLengthsDiv.appendChild(document.createElement('br'));
                    }
                }
        
                function generateYearOptions() {
                    var options = '<option value="" selected disabled>Select starting year</option>';
                    for (var year = 1900; year <= 2100; year++) {
                        options += '<option value="' + year + '">' + year + '</option>';
                    }
                    return options;
                }
            </script>
            <div class='REGNUM'>
            <form action='/AvgtempPopul.html' method='post' onsubmit="createRegionForms(); return false;">
                <label for='RegionNums'>Number of regions</label>
                <select id='RegionNums' name='RegionNums' onchange="createRegionForms()">
                    <option value="" selected disabled>Select number of regions</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
                <button type='submit'>Submit</button>
            </form>
        </div>
        <div id="regionForms">
            <!-- Region forms will be dynamically created here -->
        </div>
        
        
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
