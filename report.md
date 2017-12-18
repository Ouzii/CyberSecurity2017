Cyber Security MOOC Course Project I report

Application source code at https://github.com/Ouzii/CyberSecurity2017
Project is made with SpringBoot and H2 database. It is a simple notebook application, that lets every user to save their own notes. The application’s authentication is done by simple queries to database, where usernames and passwords are stored in clear text. If username is found with matching password, an attribute called “user” is added to HttpSession, which indicates logging in. Software is purposefully made to be highly vulnerable and simple. Here are 5 OWASP listed vulnerabilities, that can be easily found within the software:

First of all, clone the source code from Github (see the repository at the start of  this report, and see information on cloning repositories from Github.com or from google). Then open the code in some IDE (Integrated Development Environment), for example NetBeans. To run the application, in NetBeans you can right-click on the project name and click “Run”. In any other IDE, see their tutorials on how to run applications. Starting the application can take up to two minutes, depending on your setup.

Issue: Broken authentication and session management
Steps to reproduce:
1. Start the application
2. Go to http://localhost:8080 (or any port you configure the application to open, default is 8080)
3. Click register
4. Make a new account, any password and username will do (given the username is not already in use)
5. Log in with your newly made account (registering should redirect you to login screen)
6. Click change your password
7. Modify the address to some other id in it, for example http://localhost:8080/1/changePassword
8. Give a new password
9. Click change your password
10. You have now changed the password of user with id 1, and can see their notes.

Steps 2-4. are to make an example account to use for logging in.

Fix for this issue: Make changing your password more private. Ask for old password before accepting the new password and check access for every site. Also, never use ids as path variables.


Issue: Insecure Direct Object References
Steps to reproduce:
1. Start the application
2. Go to http://localhost:8080 (or any port you configure the application to open, default is 8080)
3. Click register
4. Make a new account, any password and username will do (given the username is not already in use)
5. Log in with your newly made account (registering should redirect you to login screen)
6. You should now see your own notes. Modify the address to http://localhost:8080/notes/1
7. You should now see notes of a user, who’s id is 1.

Steps 2-4. are to make an example account to use for logging in.

Fix for this issue: Don’t use user ids as path variables. Instead, one should use authorized indirect parameters to identify who’s notes should be shown. Also, make access checking part of your code for every site with for example SpringBoot’s security configuration.







Issue: Security misconfiguration
Steps to reproduce:
1. Start the application
2. Go to http://localhost:8080 (or any port you configure the application to open, default is 8080)
3. Click login
4. Type “admin” to be username
5. Type “admin” to be password
6. Click login
7. You should now be logged in as the application’s admin.

Fix for this issue: Do not use this kind of username/password combination. Especially for system’s admin users, better would be something that is not related to admin status. Also, a separate development environment would be much more sufficient for development. Personal accounts for every moderator on the live site would help too.



Issue: Sensitive Data Exposure
Steps to reproduce:
1. Start the application
2. Go to http://localhost:8080 (or any port you configure the application to open, default is 8080)
3. Click register
4. Make a new account, any password and username will do (given the username is not already in use)
5. Go to http://localhost:8080/moderator
6. You can now see every username/password in the database unencrypted. You could also delete these accounts.
7. Use any of the accounts to log in.
 
Steps 2-4. are to make an example account to show on the site. 


Fix for this issue: Use an encryption algorithm to encrypt passwords prior saving. Also, use for example SpringBoot’s security configuration to block anyone viewing the moderator site (except for moderators!). And lastly, never make a site that lists all users and passwords, even encrypted ones.


Issue: Missing Function Level Access Control
Steps to reproduce:
1. Start the application
2. Go to http://localhost:8080 (or any port you configure the application to open, default is 8080)
3. Click register
4. Make a new account, any password and username will do (given the username is not already in use)
5. Log in with your newly made account (registering should redirect you to login screen)
6. Open element inspector (F12)
7. Find hidden <a href>-element and reveal it (double click on the hidden=”hidden” field and delete the text, then press enter)
8. Click the revealed link
9. You have now cleared the database of all data

Fix for this issue: Do not use “hidden” -elements, especially any elements, that are meant for moderator usage only. Instead, use proper authentication for moderator services, or alternatively completely different environment. A different application for moderators can also be a viable solution, taken it has proper security.


This application has also numerous different issues, but these 5 are chosen to be shown here. In short, you should always use proper authentication methods (a lot of ready application frames have already implemented tools for this, Spring too) and encryption. Moderators’ do not need a site with a list with users and passwords. Block access to pages with sensitive data for anyone who should not get there, by HTTP security or even HTML implementation helps, though it requires some kind of template engine. This application is meant to be vulnerable, and this kind of source code or functionality should not be used anywhere but for education purposes.
