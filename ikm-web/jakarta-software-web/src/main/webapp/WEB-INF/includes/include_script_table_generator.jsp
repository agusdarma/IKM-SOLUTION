		<script type="text/javascript">
			function DirectSubmit(FormID, ButtonID, ProgressID, DivID, URL)
			{
				$(FormID).submit(function(event)
				{
					event.preventDefault();
					
			        if(isValid === false)
			        {
			            isValid = null;
			            return false;
			        }
			        
			        $(ButtonID).trigger('click');
					GenerateTable(FormID, ProgressID, DivID, URL);
				});
				
				$(ButtonID).click(function(event)
				{
					isValid = $(FormID)[0].checkValidity();
					
			        if(false === isValid)
			        {
			            return true;
			        }
			        
			        event.preventDefault();
					GenerateTable(FormID, ProgressID, DivID, URL);
				});
			}
			
			function ajaxPost(formParam, FormID, ProgressRequest, divID, URL, messageSubject)
			{
				$(MessageResultID).removeClass(MessageState);
				ProcessStart = Date.now();
				$(ProgressRequest).show();
				
				$.ajax
				({
					type 		: 'POST', // define the type of HTTP verb we want to use (POST for our form)
					url 		: URL, // the url where we want to POST
					data 		: formParam, // our data object
				}).done(function(resultJson) 
				{
					if(resultJson.substr(2,7)=="DOCTYPE")
					{
						window.location.href='<s:property value="@com.emobile.mmbs.web.data.WebConstants@PATH_LOGIN_EXPIRED"/>';
					}
					
					$(ProgressRequest).hide();
					resultSearch = JSON.parse(resultJson);
					createPanelTop(FormID, divID, resultSearch, ProgressRequest, URL);
					createTable(FormID, divID, resultSearch, ProgressRequest, URL);
					createPanelBottom(FormID, divID, resultSearch, ProgressRequest, URL);
					
					ProcessEnd = Date.now();
					ProcessTime = (ProcessEnd - ProcessStart) / 1000;
					
					$(MessageResultID).addClass(ResultSuccess);
					$(MessageResultID).empty();
					
					if(messageSubject == "GenerateTable")
					{
						if(resultSearch.totalRow > 0)
						{
							messageSuccess= "<s:text name="l.prefixSearch" />" +resultSearch.totalRow+ " <s:text name="l.postfixSearch"/>" + ProcessTime + " <s:text name="l.seconds"/>";
						}
						else
						{
							messageSuccess="<s:text name="l.dataNotFound" />,  <s:text name="l.processTook"/> "+ProcessTime+" <s:text name="l.seconds"/>";
						}
					}
					else if(messageSubject == "navigateTable")
					{
						messageSuccess = "<s:text name="l.prefixChangePage" />" + ProcessTime + " <s:text name="l.seconds"/>";
					}
					else if(messageSubject == "sortTable")
					{
						messageSuccess = "<s:text name="l.prefixSort" />" + sortVariable + "<s:text name="l.postFixOther"/>" + ProcessTime + " <s:text name="l.seconds"/>";
					}
					else if(messageSubject == "jumpTable")
					{
						messageSuccess = "<s:text name="l.prefixJumpPage" />" + currentPage + "<s:text name="l.postFixOther"/>" + ProcessTime + " <s:text name="l.seconds"/>";
					}
					else
					{
						
					}
					
					$(MessageResultID).append(messageSuccess);
 				})
			}
		
			function GenerateTable(FormID, ProgressRequest, divID, URL)
			{
				formParam = $(FormID).serialize();
				messageSubject = "GenerateTable";
				ajaxPost(formParam, FormID, ProgressRequest, divID, URL, messageSubject);				
				return false;
			}
			
			function GenerateTableNoProcessTime(FormID, ProgressRequest, divID, URL)
			{
				formParam = $(FormID).serialize();
				messageSubject = "GenerateTable";
				ajaxPostNoProcessTime(formParam, FormID, ProgressRequest, divID, URL, messageSubject);				
				return false;
			}
			
			function navigateTable(FormID, currentPage, ProgressRequest, divID, rowModifier, URL)
			{
				formParam="currentPage="+currentPage+"&tableActivity="+1+"&rowModifier="+rowModifier;
				messageSubject = "navigateTable";
				
				ajaxPost(formParam, FormID, ProgressRequest, divID, URL, messageSubject);
			}
			
			function sortTable(FormID, currentPage, ProgressRequest, divID, rowModifier, SortVariable, URL)
			{
				sortVariable = SortVariable;
				formParam="currentPage="+currentPage+"&tableActivity="+1+"&rowModifier="+rowModifier+"&sortVariable="+sortVariable+"&sortActivity=1";
				messageSubject = "sortTable";
				
				ajaxPost(formParam, FormID, ProgressRequest, divID, URL, messageSubject);
			}
			
			function jumpTable(FormID, searchId, ProgressRequest, divID, rowModifier, totalPage, URL)
			{
				currentPage = document.getElementById(searchId).value;
				
				if(currentPage > totalPage)
				{
					$(MessageResultID).removeClass(MessageState)
					$(MessageResultID).addClass(ResultFailure);
					$(MessageResultID).empty();
					$(MessageResultID).append("Cannot go to requested page, current maximum page is : " + totalPage);
				}
				else
				{
	 				formParam="currentPage="+currentPage+"&tableActivity="+1+"&rowModifier="+rowModifier;
	 				messageSubject = "jumpTable";
	 				
	 				ajaxPost(formParam, FormID, ProgressRequest, divID, URL, messageSubject);
				}
			}
			
			function createTable(FormID, divID, resultSearch, progressRequest, URL)
			{
				var listLink=resultSearch.linkTable;
				var linkGenerate="";
				var bodyContent=JSON.parse(resultSearch.bodyContent);
				var body=JSON.parse(resultSearch.body);
				var sortVar=JSON.parse(resultSearch.sortVariable);
				var header=JSON.parse(resultSearch.header);
				var contentDiv=document.getElementById(divID);
				var tableID=divID+"Table";
				contentDiv.innerHTML+="<div class='TableContainer'><table id="+tableID+"></table></div>";
				var contentTable=document.getElementById(tableID);
				var tbHead="<thead><tr>";
				for(var i=0; i < header.length; i++)
				{
					if(sortVar[i]==undefined || sortVar[i].length==0)
					{
						tbHead+="<th>"+header[i]+"</a></th>";
					}
					else
					{
						if(resultSearch.chosenSortVariable==sortVar[i])
						{
							if(resultSearch.sortOrder==0)
							{
								tbHead+="<th><a href=\"javascript:void(0)\" onclick=\"sortTable('"+FormID+"', 1,'"+progressRequest+"','"+divID+"', "+resultSearch.rowPerPage+", '"+sortVar[i]+"', '"+URL+"')\">"+header[i]+"</a> <div class='ShapeIsoscelesHorn'></div></th>";
							}
							else
							{
								tbHead+="<th><a href=\"javascript:void(0)\" onclick=\"sortTable('"+FormID+"', 1,'"+progressRequest+"','"+divID+"', "+resultSearch.rowPerPage+", '"+sortVar[i]+"', '"+URL+"')\">"+header[i]+"</a> <div class='ShapeIsoscelesTail'></div></th>";
							}
						}
						else
						{
							tbHead+="<th><a href=\"javascript:void(0)\" onclick=\"sortTable('"+FormID+"', 1,'"+progressRequest+"','"+divID+"', "+resultSearch.rowPerPage+", '"+sortVar[i]+"', '"+URL+"')\">"+header[i]+"</a></th>";
						}
					}
				}
				tbHead+="</tr></thead>";
				var bodyTemp="";

				var tbBody="<tbody>";
				for (var i=0; i < bodyContent.length; i++)
				{
					bodyTemp="";
					bodyTemp+="<tr>";						
					for (var j=0; j < body.length; j++)
					{						
						for(var a=0; a < listLink.length; a++)
						{
							if(listLink[a].linkLocation==body[j])
							{
								for(var b=0; b < listLink[a].linkKey.length; b++)
								{	
									if(b==0)
									{
										linkGenerate=listLink[a].link+"?";
									}
									else
									{
										linkGenerate+="&";
									}
									linkGenerate+=listLink[a].linkKey[b]+"="+bodyContent[i][listLink[a].linkValue[b]];
								}//end b
								break;
							}
						}//end a
						if(linkGenerate.length > 0)
						{
							if(bodyContent[i][body[j]]===undefined)
							{
								bodyTemp+="<td><a href='./"+linkGenerate+"'>  </a></td>";
							}
							else
							{
								bodyTemp+="<td><a href='./"+linkGenerate+"'>"+bodyContent[i][body[j]]+"</a></td>";
							}										
						}
						else
						{
							if(bodyContent[i][body[j]]===undefined)
							{
								bodyTemp+="<td>  </td>";
							}
							else
							{
								bodyTemp+="<td>"+bodyContent[i][body[j]]+"</td>";
							}
						}
						linkGenerate="";
					} 
					bodyTemp+="</tr>";
					tbBody+=bodyTemp;
				}
				
				tbBody+="</tbody>";
				var tbFoot="<tfoot><tr><td colspan='" + body.length + "'>" + resultSearch.footerPage + " | " + resultSearch.footerRecord + "</td><tr></tfoot>";
				contentTable.innerHTML=tbHead+tbBody+tbFoot;
				//bikin tfoot di variable dan digabung dengan s:text
			}
			
			function createPanelTop(FormID, divID, resultSearch, progressRequest, URL)
			{
				var panelTop = document.getElementById(divID);
				var panelTopID = divID + "NavigateTop";
				panelTop.innerHTML = "";
				panelTop.innerHTML += 
				"<div id=" + panelTopID + " class='TableControl'>"+
					"<div class='TableControlRow'>"+
						"<div id='tcl' class='TableControlLeft'>"+
           				"</div>"+
			            "<div id='tcc' class='TableControlCenter'>"+
			            "</div>"+
			            "<div id='tcr' class='TableControlRight'>"+
			            "</div>"+
			        "</div>"+
				"</div>";
				
				/* var controlLeft=document.querySelectorAll("TableControlLeft");
				var controlCenter=document.querySelectorAll("TableControlCenter");
				var controlRight=document.querySelectorAll("TableControlRight"); */
				var controlLeft=document.getElementById("tcl");
				var controlCenter=document.getElementById("tcc");
				var controlRight=document.getElementById("tcr");
				
				var panelPaging="";
				var nextPage=resultSearch.currentPage+1;
				var prevPage=resultSearch.currentPage-1;
				var lastPage=resultSearch.totalPage;
				
				
				// NAVIGATE BUTTON
				
				if(resultSearch.panelPosition==0)
				{
					panelPaging=panelSinglePage();
				}
				if(resultSearch.panelPosition==1)
				{
					panelPaging=panelFirstPage(FormID, nextPage, lastPage, progressRequest, divID, resultSearch.rowPerPage, URL);	
				}
				if(resultSearch.panelPosition==2)
				{
					panelPaging=panelMiddlePage(FormID, prevPage, nextPage, lastPage, progressRequest, divID, resultSearch.rowPerPage, URL);	
				}
				if(resultSearch.panelPosition==3)
				{
					panelPaging=panelLastPage(FormID, prevPage, progressRequest, divID, resultSearch.rowPerPage, URL);	
				}
				
				// JUMP TO PAGE
				
				var textInputId=panelTopID+"searchInput";
				var searchText="Jump to : " + "<input type='number' id="+textInputId+" class='ShortLine' min='1' max='"+resultSearch.totalPage+"'/>"; 
				var buttonSearch="<input type='button' onClick=\"jumpTable('"+FormID+"', '"+textInputId+"','"+progressRequest+"','"+divID+"', "+resultSearch.rowPerPage+", "+resultSearch.totalPage+", '"+URL+"')\" class='ButtonPrimary' value='GO'>";
				var panelJumpPage=searchText+buttonSearch;
				
				// COMPOSE
				
				if(resultSearch.useExport==true)
				{
					var panelExport=panelExports(FormID, URL);
					controlLeft.innerHTML=panelPaging + panelExport;
				}
				else
				{
					controlLeft.innerHTML=panelPaging;
				}
				
				
				controlCenter.innerHTML=resultSearch.title;
				controlRight.innerHTML=panelJumpPage;
				
				if(resultSearch.panelPosition==0)
				{
					$("#tcr .ShortLine").prop("disabled", true);
				}
			}
			
			function createPanelBottom(FormID, divID, resultSearch, progressRequest, URL)
			{
				var panelBottom = document.getElementById(divID);
				var panelBottomID = divID + "NavigateBottom";
				
				panelBottom.innerHTML += 
				"<div id=" + panelBottomID + " class='TableControl'>"+
					"<div class='TableControlRow'>"+
						"<div id='bcl' class='TableControlLeft'>"+
           				"</div>"+
			            "<div id='bcc' class='TableControlCenter'>"+
			            "</div>"+
			            "<div id='bcr' class='TableControlRight'>"+
			            "</div>"+
			        "</div>"+
				"</div>";
				
				/* var controlLeft=document.querySelectorAll("TableControlLeft");
				var controlCenter=document.querySelectorAll("TableControlCenter");
				var controlRight=document.querySelectorAll("TableControlRight"); */
				var controlLeft=document.getElementById("bcl");
				var controlCenter=document.getElementById("bcc");
				var controlRight=document.getElementById("bcr");
				
				// SHOW ITEM PER PAGE
				
				var rowArray=JSON.parse(resultSearch.panelRowPerPage);
				var panelRowModifier="";
				for (var i=0; i < rowArray.length; i++)
				{
					panelRowModifier+="<input type='button' onclick=\"navigateTable('"+FormID+"', 1,'"+progressRequest+"','"+divID+"',"+rowArray[i]+", '"+URL+"')\" class='ButtonLimit' value="+rowArray[i]+">";
				}
				
				// COMPOSE
				
				controlLeft.innerHTML="";
				controlCenter.innerHTML="";
				controlRight.innerHTML="Show item per page : " + panelRowModifier;
			}
			
			function panelSinglePage()
			{
				firstURL="<img src='./Resource/JQuery/JMesa/firstPageDisabled.png' alt='PT Emobile Indonesia - MMBS, Button First Page' class='TableButtonDisable'>";
				prevURL="<img src='./Resource/JQuery/JMesa/prevPageDisabled.png' alt='PT Emobile Indonesia - MMBS, Button Previous Page' class='TableButtonDisable'>";
				nextURL="<img src='./Resource/JQuery/JMesa/nextPageDisabled.png' alt='PT Emobile Indonesia - MMBS, Button Next Page' class='TableButtonDisable'>";
				lastURL="<img src='./Resource/JQuery/JMesa/lastPageDisabled.png' alt='PT Emobile Indonesia - MMBS, Button Last Page' class='TableButtonDisable'>";
				combinedPanel = firstURL + prevURL + nextURL + lastURL;
				
				return combinedPanel;
			}
			
			function panelFirstPage(FormID, nextPage, lastPage, progressRequest, divID, rowModifier, URL)
			{
				firstURL = "<img src='./Resource/JQuery/JMesa/firstPageDisabled.png' alt='PT Emobile Indonesia - MMBS, Button First Page' class='TableButtonDisable'>";
				prevURL = "<img src='./Resource/JQuery/JMesa/prevPageDisabled.png' alt='PT Emobile Indonesia - MMBS, Button Previous Page' class='TableButtonDisable'>";
				nextURL = "<img src='./Resource/JQuery/JMesa/nextPage.png' alt='PT Emobile Indonesia - MMBS, Button Next Page' onclick=\"navigateTable('"+FormID+"', "+nextPage+",'"+progressRequest+"','"+divID+"', "+rowModifier+", '"+URL+"')\">";
				lastURL = "<img src='./Resource/JQuery/JMesa/lastPage.png' alt='PT Emobile Indonesia - MMBS, Button Last Page' onclick=\"navigateTable('"+FormID+"', "+lastPage+",'"+progressRequest+"','"+divID+"', "+rowModifier+", '"+URL+"')\">";
				combinedPanel = firstURL + prevURL + nextURL + lastURL;
				
				return combinedPanel;
			}
			
			function panelMiddlePage(FormID, prevPage, nextPage, lastPage, progressRequest, divID, rowModifier, URL)
			{
				firstURL = "<img src='./Resource/JQuery/JMesa/firstPage.png' alt='PT Emobile Indonesia - MMBS, Button First Page'onclick=\"navigateTable('"+FormID+"', 1,'"+progressRequest+"','"+divID+"', "+rowModifier+", '"+URL+"')\">";
				prevURL = "<img src='./Resource/JQuery/JMesa/prevPage.png' alt='PT Emobile Indonesia - MMBS, Button Previous Page'onclick=\"navigateTable('"+FormID+"', "+prevPage+",'"+progressRequest+"','"+divID+"', "+rowModifier+", '"+URL+"')\">";
				nextURL = "<img src='./Resource/JQuery/JMesa/nextPage.png' alt='PT Emobile Indonesia - MMBS, Button Next Page' onclick=\"navigateTable('"+FormID+"', "+nextPage+",'"+progressRequest+"','"+divID+"', "+rowModifier+", '"+URL+"')\">";
				lastURL = "<img src='./Resource/JQuery/JMesa/lastPage.png' alt='PT Emobile Indonesia - MMBS, Button Last Page' onclick=\"navigateTable('"+FormID+"', "+lastPage+",'"+progressRequest+"','"+divID+"', "+rowModifier+", '"+URL+"')\">";
				combinedPanel = firstURL + prevURL + nextURL + lastURL;
				
				return combinedPanel;
			}
			
			function panelLastPage(FormID, prevPage, progressRequest, divID, rowModifier, URL)
			{
				firstURL = "<img src='./Resource/JQuery/JMesa/firstPage.png' alt='PT Emobile Indonesia - MMBS, Button First Page'onclick=\"navigateTable('"+FormID+"', 1,'"+progressRequest+"','"+divID+"', "+rowModifier+", '"+URL+"')\">";
				prevURL = "<img src='./Resource/JQuery/JMesa/prevPage.png' alt='PT Emobile Indonesia - MMBS, Button Previous Page'onclick=\"navigateTable('"+FormID+"', "+prevPage+",'"+progressRequest+"','"+divID+"', "+rowModifier+", '"+URL+"')\">";
				nextURL = "<img src='./Resource/JQuery/JMesa/nextPageDisabled.png' alt='PT Emobile Indonesia - MMBS, Button Next Page' class='TableButtonDisable'>";
				lastURL = "<img src='./Resource/JQuery/JMesa/lastPageDisabled.png' alt='PT Emobile Indonesia - MMBS, Button Last Page' class='TableButtonDisable'>";
				combinedPanel = firstURL + prevURL + nextURL + lastURL;
				
				return combinedPanel;
			}
			
			function panelExports(FormID, URL)
			{
				var pdfURL = "<img src='./Resource/JQuery/JMesa/pdf.gif' alt='PT Emobile Indonesia - MMBS, Button First Page'onclick=\"exportLink('"+FormID+"', '"+URL+"','PDF')\">";
				var excelURL = "<img src='./Resource/JQuery/JMesa/excel.gif' alt='PT Emobile Indonesia - MMBS, Button First Page'onclick=\"exportLink('"+FormID+"', '"+URL+"','XLS')\">";
				var csvURL = "<img src='./Resource/JQuery/JMesa/csv.gif' alt='PT Emobile Indonesia - MMBS, Button First Page'onclick=\"exportLink('"+FormID+"', '"+URL+"','CSV')\">";
				var combinedPanel = pdfURL + excelURL + csvURL;
				
				return combinedPanel;
			}
			
			function exportLink(FormID, URL, exportType)
			{
				$('#exportType').val(exportType);
				$(FormID).attr('action', URL).submit();
			}
			
			function ajaxPostNoProcessTime(formParam, FormID, ProgressRequest, divID, URL, messageSubject)
			{
				ProcessStart = Date.now();
				$(ProgressRequest).show();
				
				$.ajax
				({
					type 		: 'POST', // define the type of HTTP verb we want to use (POST for our form)
					url 		: URL, // the url where we want to POST
					data 		: formParam, // our data object
				}).done(function(resultJson) 
				{
					if(resultJson.substr(2,7)=="DOCTYPE")
					{
						window.location.href='<s:property value="@com.emobile.mmbs.web.data.WebConstants@PATH_LOGIN_EXPIRED"/>';
					}
					
					$(ProgressRequest).hide();
					resultSearch = JSON.parse(resultJson);
					createPanelTop(FormID, divID, resultSearch, ProgressRequest, URL);
					createTable(FormID, divID, resultSearch, ProgressRequest, URL);
					createPanelBottom(FormID, divID, resultSearch, ProgressRequest, URL);
					
					ProcessEnd = Date.now();
					ProcessTime = (ProcessEnd - ProcessStart) / 1000;					
 				})
			}
			
        </script>