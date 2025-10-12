<%--
  Created by IntelliJ IDEA.
  User: MKononovich
  Date: 24.09.2025
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<body>
  <h1>Стрельба по мишеням</h1>
  <form:form>
    <div>
      Шутер:
      <select name="typeShoot" id="typeShoot">
        <option selected disabled>выберите...</option>
        <c:forEach items="${typeList}" var="item">
          <option value="${item.key}"> ${item.value}</option>
        </c:forEach>
      </select>
    </div>
  </form:form>
</body>
</html>
