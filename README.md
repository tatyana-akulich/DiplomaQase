<h1>Qase/Defects</h1>
<h2>Description</h2>
<p>Qase is a test case management systems, providing such base functionality as:</p>
<li>creating of projects</li>
<li>adding of test cases and suites</li>
<li>creating of test plans and running them</li>
<li>creating of defects as a result of tests running</li>
<li>adding of tags, milestones.</li>
<p></p>
<p>In defects section it is possible to:</p>
<li>add and delete defects</li>
<li>filter defects by status, assignee, severity, author, date of creating, milestones, tags</li>
<li>search defects by title</li>
<li>edit defects - change their parameters or status</li>
<li>add new tags</li>
<li>attach documents to defects</li>
<li>comment defects</li>
<li>export defects.</li>

<h2>Checklist</h2>
<li>test authorization (valid login and password, empty, invalid values) - UI</li>
<li>test adding of new defect (with obligatory fields, all fields and empty values) - UI, API</li>
<li>test removal of defect - UI, API</li>
<li>test navigation (pages and arrows) - UI</li>
<li>test search by defect title - UI</li>
<li>test status filter - UI</li>
<li>test severity filter - UI</li>
<li>test defect updating (updating fields, resolving, changing status) - API</li>
<li>test getting of defects (all, by status, by id) - UI, API</li>
    
<h2>Stack of technologies</h2>
<li>TestNG - for creating and running of tests</li>
<li>Maven - for project guiding, gathering and running tests</li>
<li>Selenide - for automating of interaction with web browsers</li>
<li>Allure - for creating reports</li>
<li>Jenkins - for running tests</li>
<li>REST Assured and GSON - for automation of API tests</li>
<li>Lombok and log4j - for logging</li>

<h2>Test running and reporting</h2>
<h3>Test running</h3>
<p>Maven: mvn clean test (-DsuiteXmlFile=src/test/resources/{}, xmlFile)</p>
<p>Jenkins: choose project -> build / build with parameters</p>
<h3>Reporting</h3>
<p>Maven: mvn clean test -> allure report -> allure serve</p>
<p>Jenkins: add reporting: choose project -> Configure -> Post-build actions -> Allure Report<br>
view report: choose project -> click Allure Report on left-side menu</p>
