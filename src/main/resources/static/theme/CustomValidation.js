// JScript File
// developed by
// @Vishal Mahajan on July 26, 2006
// Modified by 
// @Vishal Mahajan on January 9, 2007 

// trim functionality
String.prototype.trim = function()
{
    a = this.replace(/^\s+/, '');
    return a.replace(/\s+$/, '');
}; 

// required field validation function
function validate_required(field,alerttxt1, vcase, alerttxt2)
{
    
    var check="";
    var fvalue = field.value.trim();
    if (fvalue == null || fvalue == "")
        return alerttxt1;
    else
    {
        check = validate_expression(field, vcase, alerttxt2);
        return check;            
    }
}


// regular expression check function

function validate_expression(field, vcase, alerttxt)
{
    var regex = "";
    switch(vcase)
    {
        case 'd':    // Only Digits
            regex = "^[0-9]*$"
            break;
        case 'f':   // floating point numbers
            //regex = "^[0-9]+$|[0-9]+(?:(.|))[0-9]+$";
            regex = "^[0-9]+$|^[0-9]+(?:(\\.|))[0-9]+$";
            break;
        case 'a':    // Only alphabets and space(s)
            regex = "^[a-zA-Z ]*$"
            break;
        case 'p':    // Everything except < > ' ;
            regex = "^[^<>';]*$"
            break;
        case 'o':    // Only alphabets and space(s) and period
            regex = "^[a-zA-Z. ]*$"
            break;
        case 'n':    // Aplphanumeric [Alphabets+Digits] (Space Not Allowed)
            regex = "^[a-zA-Z0-9]*$"
            break;   
        case 'h':    // Aplphanumeric [Alphabets+Digits] + space(s) + hyphen
            regex = "^[a-zA-Z0-9 -]*$"
            break;
        case 'z':   // used for having clause in drg for between and not between
            regex = "^[1-9][0-9]* (?:(and|AND)) [1-9][0-9]*$";
            break;
        case 'y':   // used for having clause in drg for numbers
            regex = "^[1-9][0-9]*$";
            break;
        case 't':   // used for date validation in dd/mm/yyyy format
	     regex = "^(?:(31)(/)(0?[13578]|1[02])\\2|(29|30)(/)(0?[13-9]|1[0-2])\\5|(0?[1-9]|1\\d|2[0-8])(/)(0?[1-9]|1[0-2])\\8)((?:1[6-9]|[2-9]\\d)?\\d{2})$|^(29)(/)(0?2)\\12((?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:16|[2468][048]|[3579][26])00)$";
             break;
        case 'aadhaar':   // Aadhaar
               regex = "^[0-9]{12}$";
               break;
        case 'email':   // Email validation
               regex = ".+@.+\\.[a-z]+";
               break;
        case 'epic':   // EPIC validation
               regex = "^[a-zA-Z]{3}[0-9]{7}$";
               break;
        case 'epic1':   // EPIC validation
               regex = "^[A-Za-z0-9]+$";
               break;
        case 'passport':   // PASSPORT validation
               regex = "^[A-Z]{1}[0-9]{7}$";
               break;
         case 'b': // For PAN number
             regex = "[A-Za-z]{5}\\d{4}[A-Za-z]{1}";                          
        break;
    }
    var fvalue = field.value.trim();
    if(fvalue.length >0)
    {
        var re = new RegExp(regex);
        var str = fvalue.match(re);
        if (str != null && str != "")
            return "";
        else
            return alerttxt;
    }
    else
        return "";
}

// function to retrieve url parametes using regular expressions.
function Get_Parameter(name)
{
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp(regexS);
  var winUrl = window.location.href;
  var results = regex.exec(winUrl);
  if( results == null )
    return "";
  else
    return results[1];
}

function SearchEmpId(priv,idtxtbox){
    if(priv == "E"){
		alert('You don\'t have rights to see the list of employees.'); 
    }else{
		var mine = window.open('/menumodule/svr/MiscellaneousServlet?status=EmpIdSearchView&idtxtbox='+idtxtbox,'','top=10,left=10,resizable=1,height=500,width=740, scrollbars=1, status=yes');
        if(!mine)
            alert("Popup Blocked. Please Disable Pop Up Blocker.");
	}
}

function SearchGroupEmpId(priv,idtxtbox){
    if(priv == "E"){
        alert('You don\'t have rights to see the list of employees.'); 
    }else{
        //var mine = window.open('/menumodule/jsp/SearchEmployee/SearchEmployee.jsp?status=first&idtxtbox='+idtxtbox,'','top=10,left=10,resizable=1,height=500,width=740, scrollbars=1, status=yes');
		var mine = window.open('/menumodule/svr/MiscellaneousServlet?status=EmpIdSearchView&idtxtbox='+idtxtbox,'','top=10,left=10,resizable=1,height=500,width=740, scrollbars=1, status=yes');
        if(!mine)
            alert("Popup Blocked. Please Disable Pop Up Blocker.");
    }
}

function SearchITSPNewId(priv,idtxtbox){
    if(priv == "E"){
            alert('You don\'t have rights to see the list of employees.');
    }else{
          //  var mine = window.open('/menumodule/jsp/SearchEmployee/ITSPSearchEmployee.jsp?status=first&idtxtbox='+idtxtbox,'','top=10,left=10,resizable=1,height=500,width=740, scrollbars=1, status=yes');
          var mine = window.open('/menumodule/svr/ITSPAttendanceServlet?status=ITSPEmpSearchListView&idtxtbox='+idtxtbox,'','top=10,left=10,resizable=1,height=500,width=740, scrollbars=1, status=yes');

        if(!mine)
            alert("Popup Blocked. Please Disable Pop Up Blocker.");
	}
}

function validateRequiredEmpId(field, alerttxt1, alerttxt2) {
    if(alerttxt1 == '') alerttxt1 = "Enter employee Id.\n";
    if(alerttxt2 == '') alerttxt2 = "Enter valid employee Id.\n";   
   return validate_required(field,alerttxt1,'d',alerttxt2);
}

function validateEmpId(field, alerttxt) {
   if(alerttxt == '') alerttxt = "Enter valid employee Id.\n";
   return validate_expression(field, 'd',alerttxt);
}

/* below functions added on 25-APR-2016 */

//for validating any empty filed in pages 
function validateEmptyField(field, alerttxt) {
   if (field == null || field == "")
   {
      return alerttxt;             
   }
   else
       return "";
}
//check date is less than given date
  
//check date is less than given date
 function checkDate(date,alertText) {
    // alert("Entered date in js ");
           var EnteredDate = date; //for javascript
           //alert("Entered date in js "+EnteredDate);
           var date ="";
           if ( EnteredDate.indexOf("-") > -1 ) {
               // alert("found - ");
                date =EnteredDate.split('-');
            }
          else if ( EnteredDate.indexOf("/") > -1 ) {
              //alert("found / ");
              date =EnteredDate.split('/');
          }
           
           var day=date[0];
           var month=date[1];
           var year=date[2];
           
           var myDate = new Date(year, month - 1, day,0,0,0,0);
           var today = new Date((new Date()).setHours(0, 0, 0, 0));
          // alert("Entered date is less than today's date "+myDate+today);
           var tomorrow=today.setDate(today.getDate() + 1);
           //alert("Entered date is less than today's date "+tomorrow);
            if (myDate < tomorrow) {
              //  alert("Entered date is less than today's date "+myDate+today);
                return alertText;
            }
            else {
               // alert("Entered date is greater than today's date ");
                return "";
            }
      //return "";
        }
 //check date is less than today date
 function verifyEndDate(date,alertText) {
    // alert("Entered date in js ");
           var EnteredDate = date; //for javascript
           //alert("Entered date in js "+EnteredDate);
           var date ="";
           if ( EnteredDate.indexOf("-") > -1 ) {
               // alert("found - ");
                date =EnteredDate.split('-');
            }
          else if ( EnteredDate.indexOf("/") > -1 ) {
              //alert("found / ");
              date =EnteredDate.split('/');
          }
           
           var day=date[0];
           var month=date[1];
           var year=date[2];
           
           var myDate = new Date(year, month - 1, day,0,0,0,0);
           var today = new Date((new Date()).setHours(0, 0, 0, 0));
         
           //alert("myDate date is"+myDate);
          // alert("today date is"+today);
            if (myDate < today) {
              //  alert("Entered date is less than today's date "+myDate+today);
                return alertText;
            }
            else {
               // alert("Entered date is greater than today's date ");
                return "";
            }
      //return "";
        }
//check date is greater than satrt date
function checkEndDate(StartDate,EndDate,alertText) {
     
           
           //alert("StartDate date  "+StartDate);
           //alert("EndDate date "+EndDate);
           var newstdate=convertDate(StartDate);
          // alert("converted StartDate date in js "+newstdate);
           var Sdate =newstdate.split('-');
           var sday=Sdate[0];
           var smonth=Sdate[1];
           var syear=Sdate[2];
           var StDate = new Date(syear, smonth - 1, sday);
           //alert("StartDate date in js "+StDate);
           var Edate =EndDate.split('/');
           var eday=Edate[0];
           var emonth=Edate[1];
           var eyear=Edate[2];
           
           var EnDate = new Date(eyear, emonth - 1, eday);
          // alert("EndDate date in js "+EnDate);
           
           
            if (EnDate <= StDate) {
               // alert("Entered date is less than today's date ");
                return alertText;
            }
            else {
               // alert("Entered date is greater than today's date ");
                return "";
            }
        }
//check date is greater than satrt date in holiday for posting
function checkEndDateForHolidayCenter(StartDate,EndDate,alertText) {
           
           //var newstdate=StartDate;
           //alert("checkEndDateForHolidayCenter at 201");
           //alert("converted StartDate date in js "+StartDate);
           var Sdate =StartDate.split('-');
           var sday=Sdate[0];
           var smonth=Sdate[1];
           var syear=Sdate[2];
           var StDate = new Date(syear, smonth - 1, sday);
           //alert("StartDate date in js "+StDate);
           var Edate =EndDate.split('/');
           var eday=Edate[0];
           var emonth=Edate[1];
           var eyear=Edate[2];
           var EnDate = new Date(eyear, emonth - 1, eday);
          // alert("EndDate date in js "+EnDate);
           if (EnDate <= StDate) {
          // alert("Entered date is less than today's date ");
                return alertText;
            }
            else {
               // alert("Entered date is greater than today's date ");
                return "";
            }
}
  
