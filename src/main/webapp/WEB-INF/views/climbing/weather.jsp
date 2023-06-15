<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#apiCall').change(function() {
                
                var stnId = $(this).val();
                
                var request = $.ajax({
                    url: "/apiCallProcess",
                    method: "POST",
                    data: { stnId : stnId },
                    dataType: "json"
                });
                 
                request.done(function( data ) {
                    console.log(data);
                });
                 
                request.fail(function( jqXHR, textStatus ) {
                  alert( "Request failed: " + textStatus );
                });
                
            });
        });
    </script>
</head>
<body>
    <select id="apiCall">
        <option value="108"> :: 전국:: </option>
        <option value="109"> 서울/경기 </option>
        <option value="146"> 전라북도 </option>
        <option value="184"> 제주도 </option>
    </select>
</body>
</html>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>

</body>
</html>