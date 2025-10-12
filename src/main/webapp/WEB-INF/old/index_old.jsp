<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html xml:lang="ru">
<head>
    <title>Стрельба по тарелкам</title>
    <script>

        config = { wrongTemplateList :true };
        document.addEventListener('DOMContentLoaded', function() {
            readSaveTemplates();
        });

        function run() {
            let params = getParamsForPost();
            httpPostAjaxRequest('/api/v1/shoot', params, 'res');
        }

        function save() {
            let project = document.getElementById("projectName")
            if (project.value === '') {
                alert("Сохранение безимянного проекта невозможно");
                project.focus();
                return ;
            }
            let choice =  document.getElementById("typeShoot");
            if (choice.selectedIndex === 0) {
                alert("Выберите действие перед сохранением");
                return;
            }
            let params = getParamsForPost();
            config.wrongTemplateList = true;
            httpPostAjaxRequest('/api/v1/save', params, 'res');
        }

        function getParamsForPost() {
            let objects = document.getElementById("form").childNodes;
            let params = new URLSearchParams();
            let select = document.getElementById("typeShoot");
            let project = document.getElementById("projectName");

            params.append("typeShoot", select.value);
            params.append("projectName", project.value);
            for (i = 0; i < objects.length; ++i) {
                let obj = objects.item(i);
                let name = obj.name;
                if (name) {
                    params.append(name, obj.value);
                }
            }
            return params;
        }

        function waitAndClean() {
            setTimeout(() => clean('res'), 5000);
        }

        function clean(elem) {
            document.getElementById(elem).innerHTML = '';
        }

        function httpPostAjaxRequest(path, params, emel) {
            httpPostRequest (path, params, function(data) {
                document.getElementById(emel).innerHTML = data;
                waitAndClean();
            });
        }

        function httpPostRequest(path, params, calbackfunc) {
            var req = new XMLHttpRequest();
            var address = location.origin + path;
            req.open('POST', address, true);
            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            req.setRequestHeader('Access-Control-Allow-Origin', location.origin);
            if (calbackfunc !== undefined) {
                req.onreadystatechange = function () {
                    if (req.readyState === req.DONE) {
                        calbackfunc(req.response);
                    }
                }
            }
            if (params === undefined) {
                req.send();
            } else {
                req.send(params);
            }
            return req.response;
        }

        function loadJsp(select) {
            let params = new URLSearchParams({jspName: select.value})
            httpPostAjaxRequest('/api/v1/loadClass', params, 'form');
        }

        function loadTemplate(select) {
            let params = new URLSearchParams({templateName: select.value})
            httpPostAjaxRequest('/api/v1/loadFromTemplate', params, 'form');

        }

        function readSaveTemplates() {
            const select = document.getElementById("templates");
            httpPostRequest('/api/v1/readTemplate', undefined, function(data) {
                while (select.options.length > 1) {
                    select.options.remove(1);
                }
                const templatesNames = JSON.parse(data);
                for (templateName of templatesNames) {
                    const opt = document.createElement('option');
                    opt.text = templateName;
                    opt.index = select.options.length + 1;
                    select.options.add(opt);
                };
                config.wrongTemplateList = false;
            });
        }

        function clickTemplate() {
            if (config.wrongTemplateList === true) {
                readSaveTemplates();
            }
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
    <div>
        <table>
            <th/><th/>
            <tr>
                <td>Проект</td>
                <td><input name="projectName" id="projectName"></td>
            </tr>
            <tr>
                <td>Загрузить</td>
                <td><select name="templates" id="templates" onchange="loadTemplate(this)" onclick="clickTemplate()">
                    <option selected disabled>выберите шаблон....</option>
                </select></td>
            </tr>
        </table>
    </div>
    <div id="form">
    </div>
    <div>
        <button name="save" onclick="save()">save</button>
        <button name="run" onclick="run()">!!!SHOOT!!!</button>
    </div>
    <div id="res"></div>
</div>
</html>