
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
                    regionFormsDiv.appendChild(submitButton);
                }
        
                function createYearLengthInputs(selectElement) {
                    var numberOfPeriods = selectElement.value;
                    var yearLengthsDiv = document.getElementById('yearLengths' + selectElement.id.replace('periods', ''));
        
                    // Clear any existing input boxes
                    yearLengthsDiv.innerHTML = '';
                    selectElement.name = 'Period length';
                    for (var i = 1; i <= numberOfPeriods; i++) {
                        var startingYearSelect = document.createElement('select');
                        startingYearSelect.name = 'startingYear' + i;
                        startingYearSelect.innerHTML = generateYearOptions();
                        yearLengthsDiv.appendChild(startingYearSelect);
                    }
                    var input = document.createElement('input');
                        input.type = 'number';
                        input.name = 'yearLength';
                        input.placeholder = 'Enter year length for period ';
                        yearLengthsDiv.appendChild(input);
                        yearLengthsDiv.appendChild(document.createElement('br'));
                }
        
                function generateYearOptions() {
                    var options = '<option value="" selected disabled>Select starting year</option>';
                    for (var year = 1900; year <= 2100; year++) {
                        options += '<option value="' + year + '">' + year + '</option>';
                    }
                    return options;
                }
            