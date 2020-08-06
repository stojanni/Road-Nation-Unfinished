<?php

require 'credentials.php';

$username = $_GET ["username"];
$password = $_GET ["password"];
$type = $_GET ["type"];


//CONNECTION

$mysqli = new mysqli($server, $dbusername, $dbpassword, $database);

if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}

$mysqli->set_charset('utf8');

if($type == "True"){

	//QUERY

	$stmt = $mysqli->prepare("SELECT username, password, name, lastname, email, phone, enginecc, helmet, votes, ratings, FROM drivers WHERE username=? AND password=?");
	$stmt->bind_param("ss", $username, $password);
	$stmt->execute();
	$result = $stmt->get_result();


	//RESULTS

	if ($result->num_rows > 0) {
		
		// output data of each row
		while($row = $result->fetch_assoc()) {
			echo $row["username"] . "-" . $row["password"] . "-" . $row["name"] . "-" . $row["lastname"] . "-" . $row["email"] . "-" . $row["phone"] . "-" . $row["enginecc"] . "-" . $row["helmet"] . "-" . $row["votes"] . "-" . $row["ratings"];
		}
		
	} else {
		echo "No results";
	}

}else{
	
	//QUERY

	$stmt = $mysqli->prepare("SELECT username, password, name, lastname, email, phone, votes, ratings FROM clients WHERE username=? AND password=?");
	$stmt->bind_param("ss", $username, $password);
	$stmt->execute();
	$result = $stmt->get_result();


	//RESULTS

	if ($result->num_rows > 0) {
		
		// output data of each row
		while($row = $result->fetch_assoc()) {
			echo $row["username"] . "-" . $row["password"] . "-" . $row["name"] . "-" . $row["lastname"] . "-" . $row["email"] . "-" . $row["phone"] . "-" . $row["votes"] . "-" . $row["ratings"];
		}
		
	} else {
		echo "No results";
	}
	
}


$mysqli->close();

?>