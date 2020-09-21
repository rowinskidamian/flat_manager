<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - Strona logowania</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
    <script src="https://kit.fontawesome.com/a1834f9866.js" crossorigin="anonymous"></script>
</head>
<body>

<section class="section">
    <div class="container">
        <h1 class="title">
            Logowanie
        </h1>
        <p class="subtitle">
            Wpisz swoje dane, aby się zalogować.
        </p>
    </div>

    <c:if test="${param['error'] != null}">
        <div class="notification is-danger is-light">
            <button class="delete"></button>
            Błędne dane logowania!
        </div>
    </c:if>

    <div class="container">
        <form method="post" action="/login">

            <div class="field">
                <label class="label">Nazwa użytkownika</label>
                <div class="control">
                    <input class="input" type="text" name="username" placeholder="Podaj nazwę użytkownika">
                </div>
            </div>

            <div class="field">
                <label class="label">Hasło</label>
                <div class="control has-icons-left">
                    <input class="input" type="password" name="password" placeholder="Hasło">
                    <span class="icon is-small is-left">
                        <i class="fas fa-lock"></i>
                    </span>
                </div>
            </div>

            <div class="field is-grouped">
                <div class="control">
                    <button class="button is-link" type="submit">Zaloguj</button>
                </div>
                <div class="control">
                    <button class="button is-link is-light" type="reset">Wyczyść</button>
                </div>
            </div>
            <sec:csrfInput/>
        </form>
        <p class="has-text is-centered">
            Nie masz konta? <a href="/register">Zarejestruj się</a>
        </p>
    </div>
</section>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        (document.querySelectorAll('.notification .delete') || []).forEach(($delete) => {
            $notification = $delete.parentNode;

            $delete.addEventListener('click', () => {
                $notification.parentNode.removeChild($notification);
            });
        });
    });
</script>


</body>
</html>
