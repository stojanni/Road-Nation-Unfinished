<?php
	require 'credentials.php';		$username = $_POST ["username"];
	$type = $_POST ["type"];	
	//CONNECTION
	$mysqli = new mysqli($server, $dbusername, $dbpassword, $database);
	if ($mysqli->connect_errno) {
		echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
	}
	$mysqli->set_charset('utf8');
	if($_SERVER['REQUEST_METHOD'] =='POST'){
		if(type == "driver"){
			$driverProfile = $_POST['driverProfile'];
			$driverIdentity = $_POST['driverIdentity'];
			$driverLicense = $_POST['driverLicense'];
			$bikeProfile = $_POST['bikeProfile'];
			$bikeLicense = $_POST['bikeLicense'];
			$bikeEnsurance = $_POST['bikeEnsurance'];	
			
			//QUERY
			$mysqli->begin_transaction();
			$stmt = $mysqli->prepare("INSERT INTO drivers (profile, identity, license, bikeProfile, bikeLicense, bikeEnsurance) VALUES (?, ?, ?, ?, ?, ?)");
			$stmt->bind_param('ssssss', $driverProfile, $driverIdentity, $driverLicense, $bikeProfile, $bikeLicense, $bikeEnsurance);
			
			//TRANSACTIO
			if($stmt->execute()){				
				$mysqli->commit();		
				echo "Upload success";					
			}else{				
				$result = "Upload error: " . $stmt->error;				$mysqli->rollback();												//QUERY						$stmt = $mysqli->prepare("DELETE FROM drivers WHERE username=?");				$stmt->bind_param('s', $username);				$stmt->execute();				$result = $stmt->get_result();								//RESULTS					if ($result->num_rows > 0) {									echo $result . "\n" . "Delete error";								} else {								echo "Delete success";						}				
			}
			$mysqli->close();
		}else{
			$clientProfile = $_POST['clientProfile'];
			$clientIdentity = $_POST['Identity'];
			
			//QUERY
			$mysqli->begin_transaction();
			$stmt = $mysqli->prepare("INSERT INTO clients (profile, identity) VALUES (?, ?)");
			$stmt->bind_param('ssssss', $clientProfile, $clientIdentity);
	
			//TRANSACTION
			if($stmt->execute()){				
				$mysqli->commit();		
				echo "Upload success";					
			}else{
								$result = "Upload error: " . $stmt->error;
				$mysqli->rollback();								//QUERY				$stmt = $mysqli->prepare("DELETE FROM clients WHERE username=?");				$stmt->bind_param('s', $username);				$stmt->execute();				$result = $stmt->get_result();				//RESULTS								if ($result->num_rows > 0) {								echo $result . "\n" . "Delete error";								} else {								echo "Delete success";						}				
			}
			$mysqli->close();
		}
	} else {
		echo "Request Method Error";
	}
?>