<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>First Look with Boolma</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
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
    <div class="container">
        <h1 class="title">
            Hello World
        </h1>
        <p class="subtitle">
            My first website with <strong>Bulma</strong>!
        </p>
    </div>
</section>
</body>
</html>
