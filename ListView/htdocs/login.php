<?php
    require_once "dbDetails.php";
    $conn = mysqli_connect(HOST, USER, PASS);

    if(mysqli_connect_errno()){
        echo "접속 실패";
    }
    mysqli_set_charset($conn, "utf8");
    mysqli_select_db($conn, DB);

    //$userId = $_POST['userId'];

    //$query = "select * from user where userId = '1';";
    $query = "selct * from custom";

    //연결된 데이터베이스에 위에서 작성한 쿼리를 전송하고 결과값을 res 변수에 저장
    $res = mysqli_query($conn, $query);
    
	//json array 형식으로 가공하기 위하여 배열 선언
	$rows = array();
	$result = array();
	
    while($row = mysqli_fetch_array($res)){
        //die("success");
		//$rows["여기는 안드로이드에서 사용할 부분"]
		$rows["id"] = $row[0];
		$rows["pass"] = $row[1];
		$rows["desc"] = $row[2];
		$rows["etc"] = $row[3];
		//$rows에 들어간 데이터들을 $result 배열에 add
		array_push($result, $rows);
    }
	//json encode 를 활용하여 result 배열 인ㄱ코딩
	echo json_encode(array("results"=>$result));
	
	mysqli_close($conn);
?>