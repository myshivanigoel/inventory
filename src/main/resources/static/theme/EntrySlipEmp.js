        function Cancel_Operation(url)
        {
            document.frmEntrySlip.action=url;
            document.frmEntrySlip.submit(); 
        }
        function OpenFinalizedEntrySlips(viewAll){
            var mine = window.open(viewAll,"EntrySlip","left=20,top=20,width=760,scrollbars=1,status=1,resizable=1");
            mine.focus();
            if(!mine)
                alert("Popup Blocked. Please Enable Your Popups.");
               
                
        }

        function activateReasonBox(){
            
            if(document.getElementById("ddlEntrySlipReason").value == "1")
            {
                document.getElementById("reasonRow").style.display = "";
            }else{
                document.getElementById("reasonRow").style.display = "none";
            }
        }
        function Validate_Fields(url){
            var msg = "";
            // var sancAuth = document.getElementById("sancId").value ;
            // if(sancAuth == '-1')
                // msg +='Select authority to forward application for approval.\n';
            var entry_date = document.getElementById("txt_entry_date");
                msg += validate_required(entry_date,'Enter date for entry slip.\n','t',"Enter valid date.\n");
            var entry_time = document.getElementById("cmb_entry_hour").value;
            if( entry_time== "-1")
            {
                msg += "Select entry time\n";
                retSave = false;
            }
            var exit_time = document.getElementById("cmb_exit_hour").value;
            if( exit_time == "-1")
            {
                msg += "Select exit time\n";
                retSave = false;
            }
            var entry_min = document.getElementById("cmb_entry_min").value;
            var exit_min  = document.getElementById("cmb_exit_min").value;
            if(entry_date != ""){
                var diff=Number(exit_time)-Number(entry_time);
               
                if(diff == 0){
                    var minDiff = Number(exit_min)-Number(entry_min);//alert("minDiff = "+minDiff )
                    if(minDiff  < 0){
                        msg +="Exit time can not be less than entry time. \n";
                    }
                }else if(diff < 0)
                {
                    msg += "Exit time can not be less than entry time. \n";
                }  
            }
            var entrySlipReasonCode  = document.getElementById('ddlEntrySlipReason').value;
            if(entrySlipReasonCode == "-1")
                msg+="Select reason for application.\n";
            else
            {
                if(entrySlipReasonCode== "1"){
                    var reason = document.getElementById('entrySlipReason');
                    if(reason.value.length > 512)
                        msg += "Length of reason cannot be greater than 512 characters.";
                }
            }
                        
            var entryMin = '00';
            var exitMin = '00';

            if(Number(entry_min )== 0 || Number(entry_min )== -1)
                entryMin = '00';
            else
                entryMin = entry_min  +'';
                
            if ( Number( exit_min ) == 0 || Number( exit_min ) == -1 ) 
                exitMin = '00';
            else
                exitMin = exit_min +'';

            if(msg != "")
            {
                alert(msg);
                return false;
            }else {
                if(confirm("Entry Slip Date : "+entry_date.value+", Entry Time : "+entry_time +":"+entryMin +", Exit Time : "+exit_time+":"+exitMin+". \nSent for approval ?")){
                    document.frmEntrySlip.action=url;
                    document.frmEntrySlip.submit(); 
                    }
                }
        }
     function getAttDetailsOnEntrySlipDate(empId){
        
         try {
              if(document.frmEntrySlip.txt_entry_date.value!=''){
                    EntrySlipInTimeDet.getInTimeDetails(empId,document.frmEntrySlip.txt_entry_date.value,callbackFun);
              }else{
                  document.getElementById("tbInTimeDet").style.display = 'none';
              }
            } catch(ex) {
                alert("Exceptoin : "+ex);
                document.getElementById("tbInTimeDet").style.display = 'none';
            }
         
     }
     function callbackFun(data){
        var myJson = data;
        if(myJson.inTime){
            document.getElementById("tbInTimeDet").style.display = '';
            document.getElementById("tdinTimeDet").innerHTML = "Attendance details.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Swipe date : "+ myJson.swipDate+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;In Time : " +myJson.inTime+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Out Time : "+myJson.outTime;
            var intimeHrs = myJson.inTime.substring(0,2);
            var intimeMin = myJson.inTime.substring(3,5);
            var outtimeHrs = myJson.outTime.substring(0,2);
            var outimeMin = myJson.outTime.substring(3,5);
            document.getElementById("cmb_entry_hour").value = intimeHrs;
            document.getElementById("cmb_entry_min").value = intimeMin;
            document.getElementById("cmb_exit_hour").value = outtimeHrs;
            document.getElementById("cmb_exit_min").value = outimeMin;
        }
        if(myJson.Error){
            document.getElementById("tbInTimeDet").style.display = 'none';
            document.getElementById("tdinTimeDet").innerHTML ='';
           
        }
    }