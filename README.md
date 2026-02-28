# flat_manager
You can use this app to manage flats, rooms, tenants, payments. It is your small business database with financial analytics. Enjoy!

<b>Tech stack</b><br>
<b>Language:</b> Java 11, basic JavaScript<br>
<b>Frameworks:</b> Spring Boot, Spring Security, Spring Data JPA/Hibernate, Spring Web (MVC), bulma.io (front-end CSS).<br>
<b>Database:</b> H2 database.<br>
<b>Project build:</b> Maven.<br>
<b>Deployed:</b> Heroku.<br>

<b>What you can do in app:</b>
- add/edit/remove: flats, rooms, tenants, payments,
- you can register your account and have your own personal database,
- app will generate for you financial summary, will help you monitor payments,

<b>Here you can find demo version with randomly generated data. Feel free to check it:</b>
user: damian
pass: pass
You can additionally register your account, however data will be automatically cleared and randomized during next loggin in.


=== You can check working demo app here ===<br>
=== http://flatmanagerpl.herokuapp.com/ ===<br>
<i>*when you will check app at Heroku (trial version), it will require about 2 minutes for Heroku containter to startup.<br>
Leave browser open, do something else, make a cup of coffee or tea, enjoy your time, come back, enjoy app.</i>

## SSL Configuration

To run this application with SSL, you will need to generate a Java KeyStore (JKS) file and set an environment variable for the keystore password.

### 1. Generate the Keystore

Use the following `keytool` command to generate a `keystore.jks` file in the `src/main/resources` directory:

```bash
keytool -genkeypair -alias flat_manager -keyalg RSA -keysize 2048 -keystore src/main/resources/keystore.jks -validity 3650 -storepass YourPassword -keypass YourPassword -dname "CN=flat_manager, OU=Development, O=Damian Rowinski, L=Warsaw, ST=Mazovia, C=PL"
```

Replace `YourPassword` with a secure password.

### 2. Set the Environment Variable

Set the `KEYSTORE_PASSWORD` environment variable to the password you used when generating the keystore.

**Linux/macOS:**
```bash
export KEYSTORE_PASSWORD=YourPassword
```

**Windows:**
```bash
set KEYSTORE_PASSWORD=YourPassword
```
