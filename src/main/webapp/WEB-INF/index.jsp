<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Стрельба по тарелкам</title>
    <script>
        function run() {
            let objects = document.getElementById("form").childNodes;
            let params = new URLSearchParams();
            let select = document.getElementById("typeShoot");
            params.append("typeShoot", select.value);
            for (i=0; i< objects.length; ++i){
                let obj = objects.item(i);
                let name = obj.name;
                if (name) {
                    params.append(name, obj.value);
                }
            }
            httpPostRequest('/api/v1/shoot', params, 'res');
        }

        function httpPostRequest(path, params, emel) {
            var req = new XMLHttpRequest();
            var address = location.origin + path;
            req.open('POST', address, true);
            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            req.setRequestHeader('Access-Control-Allow-Origin', location.origin);
            req.onreadystatechange = function () {
                if (req.readyState == req.DONE) {
                    document.getElementById(emel).innerHTML = req.response;
                }
            }
            req.send(params);
        }
        function loadJsp(select) {
            let params = new URLSearchParams({jspName: select.value})
            httpPostRequest('/api/v1/loadClass', params, 'form');
        }
    </script>
</head>
<div>
    <h1>Стрельба по мишеням</h1>
</div>
<div>
    <select name="typeShoot" id="typeShoot" onchange="loadJsp(this)">
        <option selected disabled>выберите...</option>
        <c:forEach items="${typeList}" var="item">
            <option value="${item.key}"> ${item.value}</option>
        </c:forEach>
    </select>
    <div id="form">
    </div>
    <div>
        <button onclick="run()">!!!SHOOT!!!</button>
    </div>
    <div id="res"></div>
</div>
</html>