<?php
$username=USERNAME;
$password=PASSWORD;
$database=DATABASE;

$conn = mysql_connect("localhost","$username","$password") or die("Unable to connect to database. ".mysql_error());
mysql_select_db("$database") or die( "Unable to select database. ". mysql_error());

$query = $_REQUEST['query'];
$result=mysql_query($query);

while($e=mysql_fetch_assoc($result))
        $output[]=$e;
 
print(json_encode($output));


mysql_close($conn);

?>