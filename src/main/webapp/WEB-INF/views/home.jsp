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
    <div class="container">
        <div class="tile is-ancestor">
            <div class="tile is-4 is-vertical is-parent">
                <div class="tile is-child box">
                    <p class="title is-4">Co możesz robić w aplikacji?</p>

                    <div class="content">
                        <ol type="1">
                            <li>Założyć konto i zbudować swoją prywantą bazę</li>
                            <li>Dodawać mieszkania i pokoje (jednostki najmu)</li>
                            <li>Dodawać najemców i ich dane</li>
                            <li>Dodawać płatności od najemców</li>
                            <li>Przeglądać, edytować, usuwać wpisy</li>
                        </ol>
                    </div>

                </div>
                <div class="tile is-child box">
                    <p class="title is-4">Co aplikacja może zrobić dla Ciebie?</p>

                    <div class="content">
                        <ol type="1">
                            <li>Wyliczy wynik finansowy Twojego biznesu</li>
                            <li>Pokaże Ci historię przepływów finansowych</li>
                            <li>Przedstawi Ci listę dłużników</li>
                            <li>Przedstawi Ci statystyki najmu</li>
                        </ol>
                    </div>

                </div>
            </div>
            <div class="tile is-parent">
                <div class="tile is-child box">
                    <p class="title is-4">Zaloguj się i zacznij pracę</p>
                    <jsp:include page="/WEB-INF/views/login/login-form.jsp"/>
                </div>
            </div>
        </div>
    </div>
</section>

<footer class="footer">
    <jsp:include page="layout/footer.jsp"/>
</footer>

</body>
</html>
