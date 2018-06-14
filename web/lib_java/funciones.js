function TrimRight(str) {
	var resultStr = "";
	var i = 0;

	// Return immediately if an invalid value was passed in
	if (str+"" == "undefined" || str == null)	
		return null;

	// Make sure the argument is a string
	str += "";
	
	if (str.length == 0) 
		resultStr = "";
	else {
  		// Loop through string starting at the end as long as there
  		// are spaces.
  		i = str.length - 1;
  		while ((i >= 0) && (str.charAt(i) == " "))
 			i--;
 			
 		// When the loop is done, we're sitting at the last non-space char,
 		// so return that char plus all previous chars of the string.
  		resultStr = str.substring(0, i + 1);
  	}
  	
  	return resultStr;  	
}
/*########################################################*/
function TrimLeft(str) {
	var resultStr = "";
	var i = len = 0;

	// Return immediately if an invalid value was passed in
	if (str+"" == "undefined" || str == null)	
		return null;

	// Make sure the argument is a string
	str += "";

	if (str.length == 0) 
		resultStr = "";
	else {	
  		// Loop through string starting at the beginning as long as there
  		// are spaces.
//	  	len = str.length - 1;
		len = str.length;
		
  		while ((i <= len) && (str.charAt(i) == " "))
			i++;

   	// When the loop is done, we're sitting at the first non-space char,
 		// so return that char plus the remaining chars of the string.
  		resultStr = str.substring(i, len);
  	}

  	return resultStr;
}
/*########################################################*/
function Trim(str) {
	var resultStr = "";
	
	resultStr = TrimLeft(str);
	resultStr = TrimRight(resultStr);
	
	return resultStr;
}
/*########################################################*/
function textCounter(field, countfield, maxlimit) {
	if (field.value.length > maxlimit) // if too long...trim it!
	field.value = field.value.substring(0, maxlimit);
	// otherwise, update 'characters left' counter
	else 
	countfield.value = maxlimit - field.value.length;
}
/*########################################################*/
function esAlphaNum( str ) {
	
	if (str+"" == "undefined" || str+"" == "null" || str+"" == "")	
		return false;

	var isValid = true;
	
	
   	str += "";	
	
   	for (i = 0; i < str.length; i++)
   	{
			// Alphanumeric must be between "0"-"9", "A"-"Z", or "a"-"z"
      	if (!(((str.charAt(i) >= "0") && (str.charAt(i) <= "9")) || 
      			((str.charAt(i) >= "a") && (str.charAt(i) <= "z")) ||
      			((str.charAt(i) >= "A") && (str.charAt(i) <= "Z"))))
			{
				isValid = false;
				break;
			}	
   	} 
   
   	return isValid;
}  
/*#######################################################################*/
function esNumero( str ) {

	if (str+"" == "undefined" || str+"" == "null" || str+"" == "")	
		return false;

	var isValid = true;
	
	
   	str += "";	
	
   	for (i = 0; i < str.length; i++)
   	{
			// Alphanumeric must be between "0"-"9", "A"-"Z", or "a"-"z"
      	if (!(((str.charAt(i) >= "0") && (str.charAt(i) <= "9")))) 
			{
				isValid = false;
				break;
			}	
   	} 
   
   	return isValid;
	
}  
/*#######################################################################*/
function esCadena( str ) {

	if (str+"" == "undefined" || str+"" == "null" || str+"" == "")	
		return false;

	var isValid = true;
	
	
   	str += "";	
	
   	for (i = 0; i < str.length; i++)
   	{
			// Alphanumeric must be between "0"-"9", "A"-"Z", or "a"-"z"
      	if (!(((str.charAt(i) >= "a") && (str.charAt(i) <= "z")) ||
      		  ((str.charAt(i) >= "A") && (str.charAt(i) <= "Z"))))
			{
				isValid = false;
				break;
			}	
   	} 
   
   	return isValid;
	
} 
/*#######################################################################*/
function esDigito(sChr){
var sCod = sChr.charCodeAt(0);
return ((sCod > 47) && (sCod < 58));
}

function valSep(oTxt){
var bOk = false;
bOk = bOk || ((oTxt.value.charAt(2) == "-") && (oTxt.value.charAt(5) == "-"));
bOk = bOk || ((oTxt.value.charAt(2) == "/") && (oTxt.value.charAt(5) == "/"));
return bOk;
}

function finMes(oTxt){
var nMes = parseInt(oTxt.value.substr(3, 2), 10);
var nRes = 0;
switch (nMes){
case 1: nRes = 31; break;
case 2: nRes = 29; break;
case 3: nRes = 31; break;
case 4: nRes = 30; break;
case 5: nRes = 31; break;
case 6: nRes = 30; break;
case 7: nRes = 31; break;
case 8: nRes = 31; break;
case 9: nRes = 30; break;
case 10: nRes = 31; break;
case 11: nRes = 30; break;
case 12: nRes = 31; break;
}
return nRes;
}

function valDia(oTxt){
var bOk = false;
var nDia = parseInt(oTxt.value.substr(0, 2), 10);
bOk = bOk || ((nDia >= 1) && (nDia <= finMes(oTxt)));
return bOk;
}

function valMes(oTxt){
var bOk = false;
var nMes = parseInt(oTxt.value.substr(3, 2), 10);
bOk = bOk || ((nMes >= 1) && (nMes <= 12));
return bOk;
}

function valAno(oTxt){
var bOk = true;
var nAno = oTxt.value.substr(6);
bOk = bOk && ((nAno.length == 2) || (nAno.length == 4));
if (bOk){
for (var i = 0; i < nAno.length; i++){
bOk = bOk && esDigito(nAno.charAt(i));
}
}
return bOk;
}

function valFecha(oTxt){
var bOk = true;
if (oTxt.value != ""){
bOk = bOk && (valAno(oTxt));
bOk = bOk && (valMes(oTxt));
bOk = bOk && (valDia(oTxt));
bOk = bOk && (valSep(oTxt));
if (!bOk){
alert("Fecha inválida");
oTxt.value = "";
oTxt.focus();
}
}
}

function botonSeleccionado(radioObject) {

	if (radioObject == null)
		return false;

	for (var i=0; i < radioObject.length; i++) { 	
	
		if (radioObject[i].checked) { 
			return true ; 
		} 
	}

return false ; 
}
