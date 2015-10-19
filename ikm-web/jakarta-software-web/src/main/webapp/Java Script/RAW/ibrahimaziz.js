// IBRAHIM AZIZ 
// BHIMBIM
// http://www.ibrahimaziz.biz


// BROWSER CHECK

function browserCheck(cssID, cssIE, cssMoz, cssElse)
{
    if ($.browser.msie)
    {
        document.getElementById(cssID).setAttribute("href", "CSS/" + cssIE + ".css");
    }
    else if($.browser.mozilla)
    {
        document.getElementById(cssID).setAttribute("href", "CSS/" + cssMoz + ".css");
    }
    else
    {
        document.getElementById(cssID).setAttribute("href", "CSS/" + cssElse + ".css");
    }
}


// DATE TIME

function generateDayName() 
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

function refreshDateTime(dayName, containerDateTime)
{
	var rawDateTime = new Date();
	var resultDateTime = dayName + ", " + rawDateTime.getDate() + "/" + rawDateTime.getMonth() + "/" + rawDateTime.getFullYear() + ", " + rawDateTime.getHours() + ":" + rawDateTime.getMinutes() + ":" + rawDateTime.getSeconds();
	$(containerDateTime).text(resultDateTime);
	tt=setTimeout('refreshDateTime(dayName, "'+containerDateTime+'")', 1000);
}


// FULL SCREEN

function enterFullScreen(elementID, buttonID) 
{
	if(fullScreenSwitch == 0)
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
		
		fullScreenSwitch = 1;
		$(buttonID).attr("src", "../Resources/Icon/icon_fullscreen_active.svg");
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
		
		fullScreenSwitch = 0;
		$(buttonID).attr("src", "../Resources/Icon/icon_fullscreen_inactive.svg");
	}
}