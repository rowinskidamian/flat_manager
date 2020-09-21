<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - Twój program do zarządzania najmem mieszkań</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
    <script src="https://kit.fontawesome.com/a1834f9866.js" crossorigin="anonymous"></script>
</head>
<body>
<section>
    <jsp:include page="layout/nav.jsp"/>
</section>

<section class="hero is-info">
    <div class="hero-body">
        <div class="container">
            <h1 class="title">
                Twoja baza mieszkań
            </h1>
            <h2 class="subtitle">
                Witaj ${userFirstName} na stronie gdzie możesz zarządzać bazą mieszkań, najemców, płatności.
            </h2>
        </div>
    </div>
</section>


<section class="section">
    <div class="container is-fluid">
        <sec:authorize access="isAnonymous()">
            <jsp:include page="layout/main_public.jsp"/>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <jsp:include page="layout/main_authorized.jsp"/>
        </sec:authorize>
    </div>
</section>

<footer class="footer">
    <jsp:include page="layout/footer.jsp"/>
</footer>

</body>
</html>
