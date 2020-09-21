<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Strona główna treść - dla niezalogowanych</title>
</head>
<body>

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

</body>
</html>
