function ToggleTable5(trId1, trId2, trId3, trId4, trId5,exId, colId, status)
{
    if(status == 'C')
    {
        document.getElementById(trId1).style.display = 'none';
        document.getElementById(trId2).style.display = 'none';
        document.getElementById(trId3).style.display = 'none';
        document.getElementById(trId4).style.display = 'none';
        document.getElementById(trId5).style.display = 'none';
        document.getElementById(colId).style.display = 'none';
        document.getElementById(exId).style.display = '';
    }
    else
    {
        document.getElementById(trId1).style.display = '';
        document.getElementById(trId2).style.display = '';
        document.getElementById(trId3).style.display = '';
        document.getElementById(trId4).style.display = '';
        document.getElementById(trId5).style.display = '';
        document.getElementById(colId).style.display = '';
        document.getElementById(exId).style.display = 'none';
    }
}

function openHelp(moduleName, fileName){
    var url = "/menumodule/Help/" + moduleName + "/"+fileName;
    var mine = window.open( url ,"Help","width=650,heigth=500,scrollbars=1,resizable=1,status=1");
    if(!mine)
        alert("Popups blocked. Please disable popup blocker at client side.");
}

function ToggleTable1(trId1, exId, colId, status)
{
    if(status == 'C')
    {
        document.getElementById(trId1).style.display = 'none';
        document.getElementById(colId).style.display = 'none';
        document.getElementById(exId).style.display = '';
    }
    else
    {
        document.getElementById(trId1).style.display = '';
        document.getElementById(colId).style.display = '';
        document.getElementById(exId).style.display = 'none';
    }
}
function ToggleTable3(trId1, trId2, trId3, exId, colId, status)
{
    if(status == 'C')
    {
        document.getElementById(trId1).style.display = 'none';
        document.getElementById(trId2).style.display = 'none';
        document.getElementById(trId3).style.display = 'none';
        document.getElementById(colId).style.display = 'none';
        document.getElementById(exId).style.display = '';
    }
    else
    {
        document.getElementById(trId1).style.display = '';
        document.getElementById(trId2).style.display = '';
        document.getElementById(trId3).style.display = '';
        document.getElementById(colId).style.display = '';
        document.getElementById(exId).style.display = 'none';
    }
}

function ToggleTable2(trId1, trId2, exId, colId, status)
{
    if(status == 'C')
    {
        document.getElementById(trId1).style.display = 'none';
        document.getElementById(trId2).style.display = 'none';
        document.getElementById(colId).style.display = 'none';
        document.getElementById(exId).style.display = '';
    }
    else
    {

        document.getElementById(trId1).style.display = '';
        document.getElementById(trId2).style.display = '';
        document.getElementById(colId).style.display = '';
        document.getElementById(exId).style.display = 'none';
    }
}

function ToggleTable4(trId1, trId2, trId3, trId4, exId, colId, status)
{
    if(status == 'C')
    {
        document.getElementById(trId1).style.display = 'none';
        document.getElementById(trId2).style.display = 'none';
        document.getElementById(trId3).style.display = 'none';
        document.getElementById(trId4).style.display = 'none';
        document.getElementById(colId).style.display = 'none';
        document.getElementById(exId).style.display = '';
    }
    else
    {
        document.getElementById(trId1).style.display = '';
        document.getElementById(trId2).style.display = '';
        document.getElementById(trId3).style.display = '';
        document.getElementById(trId4).style.display = '';
        document.getElementById(colId).style.display = '';
        document.getElementById(exId).style.display = 'none';
    }
}

function handleKeyPress(e)
{
    if (e && e.keyCode == 13)
    {
        document.getElementById("Submit22").focus();
    }
}

//if(navigator.javaEnabled()==false)
  //  alert("Please Enable Java Script From Tools options. ");

