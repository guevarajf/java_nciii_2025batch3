/*
 * DATABASE SETUP INSTRUCTIONS:
 * 
 * 1. Install MySQL Server on your system
 * 2. Create a database named 'gcash_db':
 *    CREATE DATABASE gcash_db;
 * 
 * 3. Update the database connection details in the code:
 *    - DB_URL: Your MySQL server URL
 *    - DB_USERNAME: Your MySQL username  
 *    - DB_PASSWORD: Your MySQL password	//P@ssw0rd
 * 
 * 4. Add MySQL JDBC Driver to your project:
 *    Download mysql-connector-java-x.x.x.jar and add to classpath
 *      https://dev.mysql.com/downloads/file/?id=544513
 * SECURITY FEATURES IMPLEMENTED:
 * - PIN hashing with SHA-256 and salt
 * - Account lockout after failed login attempts
 * - Input validation for all user data
 * - SQL injection prevention using PreparedStatement
 * - Secure random salt generation
 * - Session management for logged-in users
 * 
 * USAGE SCENARIOS HANDLED:
 * - New user registration with validation
 * - Secure user login with attempt tracking
 * - PIN change functionality
 * - Account lockout for security
 * - Proper error handling and user feedback
 */


Compile the program
javac -cp ".;lib\mysql-connector-j-9.4.0\mysql-connector-j-9.4.0.jar" GcashApp.java

Run the program
java -cp ".;lib\mysql-connector-j-9.4.0\mysql-connector-j-9.4.0.jar" GcashApp

