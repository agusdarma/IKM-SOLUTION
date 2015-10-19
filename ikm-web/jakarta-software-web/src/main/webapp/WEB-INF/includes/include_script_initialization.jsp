		<script type="text/javascript">
			// BASIC FUNCTION	
			
			function PreferenceInitialization(UserPreference, DefaultPreference)
			{
				if(UserPreference.length > 1)
				{
					CurrentPreference = UserPreference;
				}
				else
				{
					CurrentPreference = DefaultPreference;
				}
				
				return CurrentPreference;
			}
			
			
			// INITIALIZATION
			
			// THEME
			
			var NavigationCounter = 0;
			var UserTheme = '<s:property value="%{#session.LOGIN_KEY.userPreference.theme}"/>';
			var CurrentTheme = PreferenceInitialization(UserTheme, '<s:property value="@com.emobile.mmbs.web.data.WebConstants@DEFAULT_THEME"/>');
			var ThemeArray;
			var ThemeCSSArray;
			
			// FONT SIZE
			
			var UserFontSize = '<s:property value="%{#session.LOGIN_KEY.userPreference.fontSize}"/>';
			var CurrentFontSize = PreferenceInitialization(UserFontSize, '<s:property value="@com.emobile.mmbs.web.data.WebConstants@DEFAULT_FONTSIZE"/>');
			var FontSizeArray;
			var FontSizeCSSArray;
			
			// FONT FAMILY
			
			var UserFontFamily = '<s:property value="%{#session.LOGIN_KEY.userPreference.fontFamily}"/>';
			var CurrentFontFamily = PreferenceInitialization(UserFontFamily, '<s:property value="@com.emobile.mmbs.web.data.WebConstants@DEFAULT_FONTFAMILY"/>');
			var FontFamilyArray;
			var FontFamilyCSSArray;
			
			// LAYOUT ID
			
			var MessageResultID = "#MessageResult";
			var MessageDefault = "MessageDefault";
			var MessageState = "MessageState";
			var ResultSuccess = "ResultSuccess";
			var ResultFailure = "ResultFailure";
			var LoadingSquareClass = "#MessageLoading";
			
			// TIME STAMP
			
			Date.now = Date.now || function() { return +new Date; }; 
			var ProcessStart;
			var ProcessEnd;
			var ProcessTime;
			
			// AJAX
			
			var formParam;
			var resultObject;
			var resultSearch;
			var firstURL;
			var prevURL;
			var nextURL;
			var lastURL;
			var combinedPanel;
			var isValid = null;
			var messageSubject;
			var messageSuccess;
			var currentPage;
			var sortVariable;
			
			
			// FUNCTION
			
			function RefreshGreeting(ContainerGreeting)
			{
				var RAWDateTime = new Date();
				var ResultHour = RAWDateTime.getHours();
				var ResultGreeting;
				
				if(ResultHour >= 4 && ResultHour <	10)
				{
					// resultGreeting = "Good morning";
					ResultGreeting = '<s:text name="w.goodMorning"/>';
				}
				else if(ResultHour >= 10 && ResultHour < 13)
				{
					// resultGreeting = "Good day";
					ResultGreeting = '<s:text name="w.goodDay"/>';
				}
				else if(ResultHour >= 13 && ResultHour < 17)
				{
					// resultGreeting = "Good afternoon";
					ResultGreeting = '<s:text name="w.goodAfternoon"/>';
				}
				else if(ResultHour >= 17 && ResultHour < 21)
				{
					// resultGreeting = "Good evening";
					ResultGreeting = '<s:text name="w.goodEvening"/>';
				}
				else if((ResultHour >= 21 && ResultHour < 24) || (ResultHour >= 0 && ResultHour < 4))
				{
					// resultGreeting = "Good night";
					ResultGreeting = '<s:text name="w.goodNight"/>';
				}
				else
				{
					// resultGreeting = "Hello !";
					ResultGreeting = '<s:text name="w.hello"/>';
				}
				
				$(ContainerGreeting).text(ResultGreeting);
				tt=setTimeout('refreshGreeting("'+ContainerGreeting+'")', 3600000);
			}
			
			function MessageResult(MessageResult, MessageResultID)
			{
				if(MessageResult.length > 1)
				{
					$(MessageResultID).removeClass(MessageState);
					$(MessageResultID).addClass(ResultSuccess);
					$(MessageResultID).empty();
					$(MessageResultID).append(MessageResult);
				}
				else
				{
					$(MessageResultID).removeClass();
					$(MessageResultID).addClass(MessageDefault);
					$(MessageResultID).addClass(MessageState);
					$(MessageResultID).empty();
				}
			}
			
			function ButtonRequest(FormID, ButtonID, ProgressRequest, MessageResultID, URL)
			{
				$(ButtonID).click(function()
				{
					$(MessageResultID).removeClass();					
					ProcessStart = Date.now();
					$(ProgressRequest).show();
					formParam = $(FormID).serialize();
					
					// PROCESS
					
					$.ajax
					({
						type 		: 'POST', // define the type of HTTP verb we want to use (POST for our form)
						url 		: /* '<s:property value="%{remoteurl}" />' */ URL, // the url where we want to POST
						data 		: formParam, // our data object
					}).done(function(resultJson) 
					{
						if(resultJson.substr(2,7)=="DOCTYPE")
						{
							window.location.href='<s:property value="@com.emobile.mmbs.web.data.WebConstants@PATH_LOGIN"/>';
						}
						$(ProgressRequest).hide();
						resultObject = JSON.parse(resultJson);
						
						if(resultObject.rc == 0)
						{
							if(resultObject.type > 0)
							{
								window.location.href=resultObject.path;	
							}
							else
							{
								$(FormID).trigger("reset");
								ProcessEnd = Date.now();
								ProcessTime = (ProcessEnd - ProcessStart) / 1000;
								
								$(MessageResultID).addClass(MessageDefault);
								$(MessageResultID).addClass(ResultSuccess);
								$(MessageResultID).empty();
								$(MessageResultID).append(resultObject.message + ", <s:text name="l.processTook"/> " + ProcessTime + " <s:text name="l.seconds"/>");
							}
						}
						else
						{
							$(MessageResultID).addClass(MessageDefault);
							$(MessageResultID).addClass(ResultFailure);
							$(MessageResultID).empty();
							$(MessageResultID).append(resultObject.message);
						}
					});
					
					// TIMEOUT
					
					setTimeout(function()
					{
						$(ProgressRequest).hide();
					}, 
					15000);
					
					setTimeout(function()
					{
						$(LoadingSquareClass).append("<s:text name="l.loadingMessage" />");
						
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
					
					return false;
				});
			}
			
			function ButtonAuthorize(FormID, ButtonClass, ProgressRequest, MessageResultID, URL)
			{
				$(ButtonClass).click(function()
				{				
					if($(this).attr('id')=='btnApprove')
					{
						$('#mode').val('<s:property value="@com.emobile.mmbs.web.data.WebConstants@STAT_APPROVED" />');	
					}
					else
					{
						$('#mode').val('<s:property value="@com.emobile.mmbs.web.data.WebConstants@STAT_REJECTED" />');
					}
					
					$(MessageResultID).removeClass();
					ProcessStart = Date.now();
					$(ProgressRequest).show();
					formParam = $(FormID).serialize();
					
					$.ajax
					({
						type 		: 'POST', // define the type of HTTP verb we want to use (POST for our form)
						url 		: /* '<s:property value="%{remoteurl}" />' */ URL, // the url where we want to POST
						data 		: formParam, // our data object
					}).done(function(resultJson) 
					{
						if(resultJson.substr(2,7)=="DOCTYPE")
						{
							window.location.href='<s:property value="@com.emobile.mmbs.web.data.WebConstants@PATH_LOGIN"/>';
						}
						$(ProgressRequest).hide();
						resultObject = JSON.parse(resultJson);
						
						if(resultObject.rc == 0)
						{
							if(resultObject.type > 0)
							{
								window.location.href=resultObject.path;	
							}
							else
							{
								$(FormID).trigger("reset");
								ProcessEnd = Date.now();
								ProcessTime = (ProcessEnd - ProcessStart) / 1000;
								
								$(MessageResultID).addClass(MessageDefault);
								$(MessageResultID).addClass(ResultSuccess);
								$(MessageResultID).empty();
								$(MessageResultID).append(resultObject.message + ", <s:text name="l.processTook"/> " + ProcessTime + " <s:text name="l.seconds"/>");
							}
						}
						else
						{
							$(MessageResultID).addClass(MessageDefault);
							$(MessageResultID).addClass(ResultFailure);
							$(MessageResultID).empty();
							$(MessageResultID).append(resultObject.message);
						}
					});
					
					// TIMEOUT
					
					setTimeout(function()
					{
						$(ProgressRequest).hide();
					}, 
					15000);
					
					setTimeout(function()
					{
						$(LoadingSquareClass).append("<s:text name="l.loadingMessage" />");
						
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
					
					return false;
				});
			}	
			
			function isNumberKey(evt)
			{
				var charCode = (evt.which) ? evt.which : event.keyCode
				if (charCode > 31 && (charCode < 48 || charCode > 57))
					return false;
				return true;
			}
			
			function spaceNotAllowed(evt)
			{
				var charCode = (evt.which) ? evt.which : event.keyCode
				if (charCode == 32)
					return false;
				return true;
			}
        </script>