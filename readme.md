# Contacts Demo Application

To use the Contacts application, you have to prepare your MariaDB server, the Payara application server, and NetBeans.
Please follow the instructions below.

## Installation

1. Create a database
    You can either use NetBeans or the mysql command line tool (like in the installation directions).

    a) With NetBeans
    - start NetBeans
    - open the "Services" tab.
    - open "Databases".
    - connect to the initial database "javaee" that was created during the installation.
    - use "Execute command..." to open a SQL editor window.
    - create the contacts database by executing the following command:
      create database contacts default character set = utf8mb4;

    b) In a terminal/command window, use the following commands:
     Login as "APP"

     mysql -u APP -p

     Enter the following commands:

     create database contacts default character set = utf8mb4;
     quit

2. Connect to the new database in NetBeans
    - start NetBeans
    - open "Services" tab
    - open "Databases"
    - open "Drivers"
    - right-click the MariaDB driver, then "Connect Using..."
      Enter the following values:
        Host: localhost
        Port: 3306
        Database: contacts
        User Name: APP
        Password: APP
        check the "remember password" box
    - click "Test Connection"
    - When the connection works, click "Finish"
      The database connection should be listed now, you can connect by doubleclick on the connection.
      (On some systems, in a fresh NetBeans installation, you'll be asked to
       provide a "master password" for the NetBeans password store. Please use "APP" as well)

3. Payara: Create a connection pool and a JDBC resource

    - in the NetBeans "Services" tab, right-click the Payara Server and select "View Domain Admin Console"
      (in your web browser, the url http://localhost:4848 should open)
    - Navigate to "Resources/JDBC/JDBC Connection Pools"
    - Create a new JDBC Connection Pool
        - Pool Name                contacts-pool
        - Resource Type            javax.sql.DataSource
        - Database Driver Vendor   leave blank
        - click "Next"
        - set the Datasource Classname to "org.mariadb.jdbc.MariaDbDataSource"
        - add the following three properties (at the bottom of the page):
            URL             jdbc:mariadb://localhost:3306/contacts
            user            APP
            password        APP
        - click "Finish"
    - Verify the connection
        - Select the connection pool and click "Ping"
          -> Should display "Ping Succeed"

    - Navigate to "Resources/JDBC/JDBC Resources"
    - Create a new JDBC Resource
        JNDI Name           jdbc/contacts
        Pool Name           contacts-pool
        Description         DB for Contacts application
        click "OK"
    - in the list of resources, verify that all the paramerers are correctly set as above

4. Payara: Create a JavaMail Session
    - This session will connect to the TestSmtpServer to avoid sending real E-Mails during development.
    - Open the domain admin console http://localhost:4848
    - Navigate to "Resources/JavaMail Sessions"
    - Create a new JavaMail Session (leave the default values in fields that are not mentioned)
        - JNDI Name                 mail/contacts-mail
        - Mail Host                 localhost
        - Default User              nobody
        - Default Sender Address    nobody@localhost
        - Additional Properties
                Name                Value       Description
                mail.smtp.host      2500        Local test SMTP server

5. Copy credentials file
    - the credentials file provided with the demo application holds some example users for the demo application
    - you may view the file to know the user names
    - all users have the same (encypted) password "x"
    - some users are in the group "employee"
    - Copy file "acme-edu-keyfile" to the Payara domain configuration folder
      ...payara5/glassfish/domains/domain1/config

6. Create a security realm
    - open the domain admin console http://localhost:4848
    - navigate to Configurations/server-config/Security/Realms
    - create a new security realm
        - Name              acme-edu-file-realm
        - Class Name        com.sun.enterprise.security.auth.realm.file.FileRealm
        - JAAS Context      fileRealm
        - Key File          ${com.sun.aas.instanceRoot}/config/acme-edu-keyfile
        - Assign Groups     AUTHENTICATED

7. Open the project in NetBeans
   - The folder "Contacts-V5" contains a NetBeans enterprise application project named "Contacts"
   - In the application, you'll find two Java EE Modules:
     Contacts-war.war (web tier)
     Contacts-ejb.jar (EJB tier)
   - open both modules to review the sources
   - possibly, you'll get a "missing server" problem announcement
     fix this by resolving the application server to your installation (everything should be fine when you completed the "HelloWorld" example)
   - on the enterprise application, right-click and select "Clean and Build"
   - after successful build, right-click again and select "Deploy"
   - after successful deployment, the contacts database should contain three tables "CONTACTENTITY", "SEQUENCE", and "USERENTITY"

8. Launch the application using your web browser
   - open the URL https://hera:8181/Contacts
   - your browser possibly warns about an insecure connection since payara only uses a self-signed certificate, you can safely ignore this warning
   - when you click on "Kontaktliste" (or "Contact List"), you'll be asked for a user name and a password
     you may use the names defined in the acme-edu-keyfile, for example, "alice", "bob", and "charlie", or any of the "...@acme.edu" names defined in the keyfile
   - after successful login, you can create, edit, and delete contacts
   - you can use the "create test data" button to create some entries
   - to test the "send contacts by mail" button, you have to start the TestSmtpServer (open the project in NetBeans and then click "run")
     the test server shows the recipients to the left, a message list to the right, and a simple log at the bottom

## Remarks

The Contacts demo application follows the architectural design that is also
required for the examination project. You may look into the exam project folder
to review the tss-architecture.pdf.

You may use the Contacts application as a blue print for the examination project.

It consists of two JavaEE Modules, a Web module with application logic, the
JSF pages and backing beans, and and EJB module with the business logic and
persistence layers.

### EJB module

The EJB module contains the following packages

- contacts.dao          Data Access Objects using JPA and PersistenceContext
- contacts.dto          Data Transfer Objects
- contacts.entities     JPA Entity Classes
- contacts.logic        Business Logic Facade Interface
- contacts.logic.impl   Business Logic Implementation

### WEB module

The WEB module contains the following packages

- contacts.web          backing beans
- contacts.web.i18n     property files with internationalized messages
- contacts.rest         REST endpoint (only for demonstration, not used in JSF)
- contacts.soap         SOAP endpoint (only for demonstration, not used in JSF)

The folder "web" contains the configuration files and libraries in "WEB-INF"
and the JSF pages in "pages".

The frontend makes use of the Bootsfaces tag  library. This library is not
strictly required for the examination project. It uses the rather old version 3
of the Bootstrap CSS framework. You may also use the recent version 5.2, but
then, you have to add all the css class and style declarations manually.

