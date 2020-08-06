<?php

	require 'credentials.php';

	$username = $_POST ["username"];
	$password = $_POST ["password"];
	
	$name = $_POST ["name"];
	
	if(isset($_POST ["enginecc"]){
		$lastname = $_POST ["lastname"];
	}
		
	$email = $_POST ["email"];
	$phone = $_POST ["phone"];
	
	if(isset($_POST ["enginecc"]){
		$bikecc = $_POST ["enginecc"];
	}	
	if(isset($_POST ["helmet"])){
		$helmet = $_POST ["helmet"];
	}
			
	$type = $_POST ["type"];
	

	//CONNECTION

	$mysqli = new mysqli($server, $dbusername, $dbpassword, $database);

	if ($mysqli->connect_errno) {
		echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
	}

	$mysqli->set_charset('utf8');

	
	
	if($type == "driver"){
	
		//QUERY

		$stmt = $mysqli->prepare("SELECT * FROM drivers WHERE email=?");
		$stmt->bind_param('s', $email);
		$stmt->execute();
		$result = $stmt->get_result();
		

		//RESULTS
		
		if ($result->num_rows > 0) {
			
			$mysqli->close();		
			echo "Account already registered";		
			
		} else {
			
			//QUERY

			$stmt = $mysqli->prepare("SELECT * FROM drivers WHERE username=?");
			$stmt->bind_param('s', $username);
			$stmt->execute();
			$result = $stmt->get_result();
			
			if ($result->num_rows > 0) {
			
			$mysqli->close();		
			echo "Username already used";		
			
			} else {
				
				//QUERY

				$mysqli->begin_transaction();
				$stmt = $mysqli->prepare("INSERT INTO drivers VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0)");
				$stmt->bind_param('ssssssss', DEFAULT, $username, $password, $name, $lastname, $email, $phone, $enginecc, $helmet);


				//TRANSACTION

				if($stmt->execute()){			
						
					$mysqli->commit();	

					date_default_timezone_set('Europe/Athens'); // CDT
					$current_date = date('d/m/Y == H:i');
							
					$msg = "New driver registration: " . $username . " on " . $current_date;				
					mail("mihalisp@protonmail.com", "New Driver Registration", $msg);							
				
					$msg = "Congratulations you for entering Road Nation, the global motorcycle transportation network! We shall review your request and answer shortly. \n\n Best Regards";								
					mail($email, "Road Nation Registration", $msg);
								
					echo "Registration success";	
							
				}else{
					echo "Driver registration error: " . $stmt->error;
					$mysqli->rollback();
				}		
					
			}
		
		}
			
	}else{
	
		//QUERY

		$stmt = $mysqli->prepare("SELECT * FROM clients WHERE email=?");
		$stmt->bind_param('s', $email);
		$stmt->execute();
		$result = $stmt->get_result();
		
		
		//RESULTS
		
		if ($result->num_rows > 0) {
			
			$mysqli->close();		
			echo "Account already registered";		
			
		} else {
			
			//QUERY

			$stmt = $mysqli->prepare("SELECT * FROM clients WHERE username=?");
			$stmt->bind_param('s', $username);
			$stmt->execute();
			$result = $stmt->get_result();
			
			if ($result->num_rows > 0) {
			
			$mysqli->close();		
			echo "Username already in use";		
			
			} else {

				//QUERY

				$mysqli->begin_transaction();
				$stmt = $mysqli->prepare("INSERT INTO clients VALUES (default, ?, ?, ?, ?, ?, ?, 0, 0)");
				$stmt->bind_param('ssssss', DEFAULT, $username, $password, $name, $lastname, $email, $phone);
				

				//TRANSACTION

				if($stmt->execute()){			
						
					$mysqli->commit();	

					date_default_timezone_set('Europe/Athens'); // CDT
					$current_date = date('d/m/Y == H:i');
					
					$msg = "New client registration: " . $username . " on " . $current_date;				
					mail("mihalisp@protonmail.com", "New Client Registration", $msg);							
				
					$msg = "Congratulations you for entering Road Nation, the global motorcycle transportation network! We shall review your request and answer shortly. \n\n Best Regards";								
					mail($email, "Road Nation Registration", $msg);
								
					echo "Registration success";	
							
				}else{
					echo "Client registration error: " . $stmt->error;
					$mysqli->rollback();
				}		
					
			}
		
		}
	
	}

?>