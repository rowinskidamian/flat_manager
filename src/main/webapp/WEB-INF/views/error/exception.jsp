<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - komunikat błędu</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
</head>
<body>
<section class="navbar">
    <jsp:include page="/WEB-INF/views/layout/nav.jsp"/>
</section>

<section class="section">
    <div class="container has-text-centered">
        <div class="notification is-danger">
            <h3 class="title is-3">WYSTĄPIŁ BŁĄD</h3>
            ${errorMessage}
        </div>
    </div>
</section>


<footer class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>


</body>
</html>
