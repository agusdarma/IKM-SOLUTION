// IBRAHIM AZIZ 
// BHIMBIM
// http://www.ibrahimaziz.biz
// built for PT Emobile Indonesia - Official Website 2014
// http://www.emobile.com.sg


// INITIALIZATION

var Directory = ".";
var FullScreenSwitch = 0;
var CheckboxSlideClass = ".CheckboxSlide";
var CheckboxSlideForOthers = ".CheckboxSlideForOthers";
var CheckboxSlideForIE = ".CheckboxSlideForIE";
var InputSwitcherClass = ".InputSwitcher";
var InputSwitcherForOthers = ".InputSwitcherForOthers";
var InputSwitcherForIE = ".InputSwitcherForIE";
var LoadingSquareClass = ".LoadingSquare";
var InputFontValid = "#00964b";
var InputFontInvalid = "#be1e2d";


// BROWSER CHECK

function BrowserCheck(cssID, cssIE, cssIElte10, cssMoz, cssElse)
{
    if($.browser.msie)
    {
		if($.browser.version > 10)
		{
        	document.getElementById(cssID).setAttribute("href", Directory + "/Style/CSS/" + cssIE + ".css");
		}
		else
		{
			document.getElementById(cssID).setAttribute("href", Directory + "/Style/CSS/" + cssIElte10 + ".css");
		}
    }
	else if(!!navigator.userAgent.match(/Trident.*rv\:11\./) == true)
	{
		document.getElementById(cssID).setAttribute("href", Directory + "/Style/CSS/" + cssIE + ".css");
	}
    else if($.browser.mozilla)
    {
        document.getElementById(cssID).setAttribute("href", Directory + "/Style/CSS/" + cssMoz + ".css");
    }
    else
    {
        document.getElementById(cssID).setAttribute("href", Directory + "/Style/CSS/" + cssElse + ".css");
    }
}

function SVGCompatibility()
{
	$('img[src$=".svg"]').each(function() 
	{
		var $this = $(this); // this = img
		$this.attr('src', $this.attr('src').replace(/svg$/, 'png'));
	});
}

function RadioAndCheckboxForIE8()
{
	if($.browser.msie && $.browser.version < 9)
	{
		$('input[type=radio],[type=checkbox]').live('click', function()
		{
			$(this).trigger('change');
		});
	}
	else
	{
		
	}
}

function LabelForIE8(TriggerID, InputID)
{
	$(TriggerID).click(function()
	{
		if($(InputID).is(':checked'))
		{
			$(InputID).prop('checked', false);
		}
		else
		{
			$(InputID).prop('checked', true);
		}
	});
}


// THEME

function CSSArrayInitialization(ThemeArray)
{
	ThemeCSSArray = [];
	
	for(var i = 0; i < ThemeArray.length; i++)
	{
		ThemeCSSArray[i] = ThemeArray[i].replace(" ", "").toLowerCase();
		// alert("ThemeArray : " + ThemeArray[i] + " // " + "ThemeCSSArray : " + ThemeCSSArray[i]);
	}
	
	return ThemeCSSArray;
}

function ThemeSelector(ThemeName, CSSID, ThemeArray, ThemeCSSArray)
{
	JavaScriptCheckboxSlideClass = CheckboxSlideClass.substring(1);
	JQueryCheckboxSlideClass = CheckboxSlideClass;
	JavaScriptCheckboxSlideForOthersClass = CheckboxSlideForOthers.substring(1);
	JavaScriptCheckboxSlideForIEClass = CheckboxSlideForIE.substring(1);
	
	JavaScriptInputSwitcherClass = InputSwitcherClass.substring(1);
	JQueryInputSwitcherClass = InputSwitcherClass;
	JavaScriptInputSwitcherForOthersClass = InputSwitcherForOthers.substring(1);
	JavaScriptInputSwitcherForIEClass = InputSwitcherForIE.substring(1);
	
	if($.browser.msie)
	{
		if(document.querySelectorAll(JavaScriptCheckboxSlideClass) == null)
		{
			
		}
		else
		{
			$(JQueryCheckboxSlideClass).removeClass(JavaScriptCheckboxSlideForOthersClass);
			$(JQueryCheckboxSlideClass).addClass(JavaScriptCheckboxSlideForIEClass);
		}
		
		if(document.querySelectorAll(JavaScriptInputSwitcherClass) == null)
		{
			
		}
		else
		{
			$(JQueryInputSwitcherClass).removeClass(JavaScriptInputSwitcherForOthersClass);
			$(JQueryInputSwitcherClass).addClass(JavaScriptInputSwitcherForIEClass);
		}
	}
	else if(!!navigator.userAgent.match(/Trident.*rv\:11\./) == true)
	{
		if(document.querySelectorAll(JavaScriptCheckboxSlideClass) == null)
		{
			
		}
		else
		{
			$(JQueryCheckboxSlideClass).removeClass(JavaScriptCheckboxSlideForOthersClass);
			$(JQueryCheckboxSlideClass).addClass(JavaScriptCheckboxSlideForIEClass);
		}
		
		if(document.querySelectorAll(JavaScriptInputSwitcherClass) == null)
		{
			
		}
		else
		{
			$(JQueryInputSwitcherClass).removeClass(JavaScriptInputSwitcherForOthersClass);
			$(JQueryInputSwitcherClass).addClass(JavaScriptInputSwitcherForIEClass);
		}
	}
	else if($.browser.mozilla)
	{
		$("input:checkbox").css("display", "none");
		$("input:radio").css("display", "none");
	}
	else if($.browser.webkit)
	{
		$("input:checkbox").css("display", "none");
		$("input:radio").css("display", "none");
	}
	else if($.browser.opera)
	{
		$("input:checkbox").css("display", "none");
		$("input:radio").css("display", "none");
	}
	else if($.browser.safari)
	{
		$("input:checkbox").css("display", "none");
		$("input:radio").css("display", "none");
	}
	else
	{
		if(document.querySelectorAll(JavaScriptCheckboxSlideClass) == null)
		{
			
		}
		else
		{
			$(JQueryCheckboxSlideClass).removeClass(JavaScriptCheckboxSlideForOthersClass);
			$(JQueryCheckboxSlideClass).addClass(JavaScriptCheckboxSlideForIEClass);
		}
		
		if(document.querySelectorAll(JavaScriptInputSwitcherClass) == null)
		{
			
		}
		else
		{
			$(JQueryInputSwitcherClass).removeClass(JavaScriptInputSwitcherForOthersClass);
			$(JQueryInputSwitcherClass).addClass(JavaScriptInputSwitcherForIEClass);
		}
	}
	
	if($.browser.msie && $.browser.version < 9)
	{
		for(var i = 0; i < ThemeArray.length; i++)
		{
			if(ThemeName == ThemeArray[i])
			{
				document.getElementById(CSSID).setAttribute("href", Directory + "/Style/CSS/" + "mmbstheme-" + ThemeCSSArray[i] + "-png" + ".css");
				i = ThemeArray.length + 1;
			}
			else
			{
				document.getElementById(CSSID).setAttribute("href", Directory + "/Style/CSS/" + "mmbstheme-" + ThemeCSSArray[0] + "-png" + ".css");
			}
		}
	}
	else
	{
		for(var i = 0; i < ThemeArray.length; i++)
		{
			if(ThemeName == ThemeArray[i])
			{
				document.getElementById(CSSID).setAttribute("href", Directory + "/Style/CSS/" + "mmbstheme-" + ThemeCSSArray[i] + "-svg" + ".css");
				i = ThemeArray.length + 1;
			}
			else
			{
				document.getElementById(CSSID).setAttribute("href", Directory + "/Style/CSS/" + "mmbstheme-" + ThemeCSSArray[0] + "-svg" + ".css");
			}
		}
	}
}

function ImageSelector(ThemeName, ImageID)
{
	$(ImageID).attr("src", $(ImageID).attr("src").replace(/Persian Blue|Snowy Owl|Vintage Afternoon/, ThemeName));
}

// FONT SIZE

function FontSizeSelector(FontSizeName, CSSID, FontSizeArray, FontSizeCSSArray)
{
	for(var i = 0; i < FontSizeArray.length; i++)
	{
		if(FontSizeName == FontSizeArray[i])
		{
			document.getElementById(CSSID).setAttribute("href", Directory + "/Style/CSS/" + "mmbsfontsize-" + FontSizeCSSArray[i] + ".css");
			i = FontSizeArray.length + 1;
		}
		else
		{
			document.getElementById(CSSID).setAttribute("href", Directory + "/Style/CSS/" + "mmbsfontsize-" + FontSizeCSSArray[1] + ".css");
		}
	}
}

// FONT FAMILY

function FontFamilySelector(FontFamilyName, CSSID, FontFamilyArray, FontFamilyCSSArray)
{
	for(var i = 0; i < FontFamilyArray.length; i++)
	{
		if(FontFamilyName == FontFamilyArray[i])
		{
			document.getElementById(CSSID).setAttribute("href", Directory + "/Style/CSS/" + "mmbsfontfamily-" + FontFamilyCSSArray[i] + ".css");
			i = FontFamilyArray.length + 1;
		}
		else
		{
			document.getElementById(CSSID).setAttribute("href", Directory + "/Style/CSS/" + "mmbsfontfamily-" + FontFamilyCSSArray[0] + ".css");
		}
	}
}


// DATE TIME

function GenerateDayName() 
{
	var rawDateTime = new Date();
	var dayArray = new Array("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday");
	
	for(var dayCounter = 1; dayCounter < 8; dayCounter++)
	{
		if(rawDateTime.getDay() == dayCounter)
		{
			dayName = dayArray[dayCounter];
			break;
		}
		else
		{
			
		}
	}
	
	return dayName;
}

function RefreshDateTime(dayName, containerDateTime)
{
	var rawDateTime = new Date();
	var resultDateTime = dayName + ", " + rawDateTime.getDate() + "/" + rawDateTime.getMonth() + "/" + rawDateTime.getFullYear() + ", " + rawDateTime.getHours() + ":" + rawDateTime.getMinutes() + ":" + rawDateTime.getSeconds();
	$(containerDateTime).text(resultDateTime);
	tt=setTimeout('refreshDateTime(dayName, "'+containerDateTime+'")', 1000);
}

function RefreshGreeting(containerGreeting)
{
	var rawDateTime = new Date();
	var resultHour = rawDateTime.getHours();
	var resultGreeting;
	
	if(resultHour >= 4 && resultHour <	10)
	{
		// resultGreeting = "Good morning";
		resultGreeting = '<s:text name="w.goodMorning"/>';
	}
	else if(resultHour >= 10 && resultHour < 13)
	{
		// resultGreeting = "Good day";
		resultGreeting = '<s:text name="w.goodDay"/>';
	}
	else if(resultHour >= 13 && resultHour < 17)
	{
		// resultGreeting = "Good afternoon";
		resultGreeting = '<s:text name="w.goodAfternoon"/>';
	}
	else if(resultHour >= 17 && resultHour < 21)
	{
		// resultGreeting = "Good evening";
		resultGreeting = '<s:text name="w.goodEvening"/>';
	}
	else if((resultHour >= 21 && resultHour < 24) || (resultHour >= 0 && resultHour < 4))
	{
		// resultGreeting = "Good night";
		resultGreeting = '<s:text name="w.goodNight"/>';
	}
	else
	{
		// resultGreeting = "Hello !";
		resultGreeting = '<s:text name="w.hello"/>';
	}
	
	$(containerGreeting).text(resultGreeting);
	tt=setTimeout('refreshGreeting("'+containerGreeting+'")', 3600000);
}


// FULL SCREEN

function ModeFullScreen(elementID, buttonID) 
{
	$(buttonID).click(function()
	{
		if(FullScreenSwitch == 0)
		{
			if(elementID.requestFullscreen) 
			{
				elementID.requestFullscreen();
			} 
			else if(elementID.mozRequestFullScreen) 
			{
				elementID.mozRequestFullScreen();
			} 
			else if(elementID.webkitRequestFullscreen) 
			{
				elementID.webkitRequestFullscreen();
			} 
			else if(elementID.msRequestFullscreen) 
			{
				elementID.msRequestFullscreen();
			}
			
			FullScreenSwitch = 1;
			if($.browser.msie && $.browser.version < 9)
			{
				$(buttonID).attr("src", Directory + "/Resource/Icon/icon_fullscreen_active.png");
			}
			else
			{
				$(buttonID).attr("src", Directory + "/Resource/Icon/icon_fullscreen_active.svg");
			}
		}
		else
		{
			if(document.exitFullscreen) 
			{
				document.exitFullscreen();
			} 
			else if(document.mozCancelFullScreen) 
			{
				document.mozCancelFullScreen();
			} 
			else if(document.webkitExitFullscreen) 
			{
				document.webkitExitFullscreen();
			}
			
			FullScreenSwitch = 0;
			if($.browser.msie && $.browser.version < 9)
			{
				$(buttonID).attr("src", Directory + "/Resource/Icon/icon_fullscreen_inactive.png");
			}
			else
			{
				$(buttonID).attr("src", Directory + "/Resource/Icon/icon_fullscreen_inactive.svg");
			}
		}
	});
}


// NAVIGATION

function NavigationEffect(InputID, NavigationItemID)
{
	if($(InputID).is(':checked'))
	{
		$(NavigationItemID).css("display", "block");
	}
	else
	{
		$(NavigationItemID).css("display", "none");
	}
}

function NavigationFixed(ContainerNavigation)
{
	if($(this).scrollTop() > $(ContainerNavigation).height() + 40) 
	{
		$("body").css("margin-top", $(ContainerNavigation).height() + 40);
		$(ContainerNavigation).css("position", "fixed");
		$(ContainerNavigation).css("top", "0px");
		$(ContainerNavigation).css("left", "50px");
		$(ContainerNavigation).css("right", "50px");
		$(ContainerNavigation).css("width", "auto");
		$(ContainerNavigation).css("-webkit-transition", "none");
	} 
	else
	{
		$("body").css("margin-top", 0);
		$(ContainerNavigation).css("position", "relative");
		$(ContainerNavigation).css("top", "0px");
		$(ContainerNavigation).css("left", "0px");
		$(ContainerNavigation).css("right", "0px");
		$(ContainerNavigation).css("width", "100%");
		$(ContainerNavigation).css("-webkit-transition", "none");
	}
}

function ButtonToTopState(ButtonID)
{
	$(ButtonID).removeClass();
	
	if($(this).scrollTop() > ($(window).height() / 2))
	{
		$(ButtonID).addClass("ToTopShow");
	}
	else
	{
		$(ButtonID).addClass("ToTopDefault");
	}
}

function ButtonToTop(ButtonID)
{
	$(ButtonID).mousedown(function()
	{
		/* JQUERY SCROLL TO */
		$("body").ScrollTo
		({
			duration: 500,
			durationMode: 'ease-out'
		});
	});
}

function NavigationNeutralizer(InputName)
{
	$("input:radio[name='"+InputName+"']").each(function(i) 
	{
		this.checked = false;
	});
}


/* PROGRESS LOAD */

function ProgressPage(ProgressLayout, OpenState)
{
	if(OpenState == 1)
	{
		/* JQUERY SCROLL TO */
		$("body").ScrollTo
		({
			duration: 1000,
			durationMode: 'all'
		});
		
		/* JQUERY SMOOTH SCROLL */
		/*$.scrollTo
		(
			$("body"), 1000
		);*/
	}
	else
	{
		
	}
	
	setTimeout(function()
	{
		$(ProgressLayout).css("margin-top", "-100%");
		$(ProgressLayout).css("opacity", "0");
	}, 
	0);
	
	setTimeout(function()
	{
		$(ProgressLayout).css("display", "none");
	}, 
	2000);
}

function ProgressInitialization(ProgressID)
{
	JavaScriptID = ProgressID.substring(1);
	JQueryID = ProgressID;
	
	if(document.getElementById(JavaScriptID) == null)
	{
		
	}
	else
	{
		$(JQueryID).hide();
	}
}

function ButtonRequest(ButtonID, ProgressRequest)
{
	$(ButtonID).click(function()
	{
		$(ProgressRequest).show();
		setTimeout(function()
		{
			$(ProgressRequest).hide();
		}, 
		15000);
	});
	
	setTimeout(function()
	{
		$(LoadingSquareClass).empty();
		$(LoadingSquareClass).append("Process took longer than usual, press ESC to cancel.");
		
		$(document).keyup(function(e) 
		{
			if (e.keyCode == 27)
			{
				$(ProgressRequest).hide();
			}
			else
			{
				
			}
		});
	}, 
	5000);
}


// MESSAGE RESULT

function MessageResult(MessageID, MessageStatus, ButtonID)
{
	$(ButtonID).click(function()
	{
		$(MessageID).removeClass();
		if(MessageStatus == 1)
		{
			$(MessageID).addClass("ResultSuccess");
		}
		else
		{
			$(MessageID).addClass("ResultFailure");
		}
	});
}


// INPUT

function CSSDropDown(ThemeArray, SelectID)
{
	var SelectItem = '';
	
	for(var i = 0; i < ThemeArray.length; i++)
	{
		SelectItem += '<option value="' + ThemeArray[i] + '">' + ThemeArray[i] + '</option>';
	}
	
	$(SelectID).append(SelectItem);
}

function DropDownSelector(SelectID, ValueSelected)
{
	$(SelectID + ' option[value="' + ValueSelected + '"]').prop('selected', true);
}

function DateTimeInitialization(DateClass, TimeClass, DateTimeClass)
{
	DateJavaScriptClass = DateClass.substring(1);
	DateJQueryClass = DateClass;
	TimeJavaScriptClass = TimeClass.substring(1);
	TimeJQueryClass = TimeClass;
	DateTimeJavaScriptClass = DateTimeClass.substring(1);
	DateTimeJQueryClass = DateTimeClass;
	
	if(document.querySelectorAll(DateJavaScriptClass) == null)
	{
		
	}
	else
	{
		jQuery(DateJQueryClass).datetimepicker
		({
			timepicker:false,
			format:'d-m-Y'
		});
	}
	
	if(document.querySelectorAll(TimeJavaScriptClass) == null)
	{
		
	}
	else
	{
		jQuery(TimeJQueryClass).datetimepicker
		({
			datepicker:false,
			format:'H:i:s'
		});
	}
	
	if(document.querySelectorAll(DateTimeJavaScriptClass) == null)
	{
		
	}
	else
	{
		jQuery(DateTimeJQueryClass).datetimepicker
		({
			format:'d-m-Y H:i:s'
		});
	}
}

function InputSwitcher(InputName)
{
	$("input[name='"+InputName+"']").each(function(i) 
	{
		this.checked = false;
		$(this).parent().css("background-color", InputFontInvalid);
		$(this).parent().parent().next().next().attr("disabled", "disabled");
	});
	
	$("input[name='"+InputName+"']").change(function()
	{
		$("input[name='"+InputName+"']").each(function(i) 
		{
			if($(this).is(':checked'))
			{
				$(this).parent().css("background-color", InputFontValid);
				$(this).parent().parent().next().next().removeAttr("disabled");
				$(this).parent().parent().next().next().attr("required", "required");
			}
			else
			{
				$(this).parent().css("background-color", InputFontInvalid);
				$(this).parent().parent().next().next().removeAttr("required");
				$(this).parent().parent().next().next().attr("disabled", "disabled");
			}
		});
	});
}