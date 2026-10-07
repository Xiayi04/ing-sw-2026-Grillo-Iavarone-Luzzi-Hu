# ing-sw-2026-Grillo-Iavarone-Luzzi-Hu

### Copyright
Il gioco da tavolo Mesos e tutto il relativo materiale grafico è di esclusiva proprietà di Cranio Creations.

The board game Mesos and all related graphic material are the exclusive property of Cranio Creations.

### Group Members

*   **Grillo Giuseppepio Salvatore Pasquale**
*   **Hu Xiayi**
*   **Iavarone Mattia**
*   **Luzzi Denise**

* * *

### Implemented Features

*   **Game Rules**: Complete Game Rules.
*   **User Interfaces**: .
    *   **TUI** (Text-based User Interface) via terminal.
    *   **GUI** (Graphical User Interface).
*   **Connection Types**: Network architecture supporting both:
    *   **Socket**
    *   **RMI**
*   **Advanced Feature**: Game history persistence via **Database**.

* * *

### System Prerequisites

*   **Java**: JDK 17 or higher installed.
*   **Database**: MySQL Server and MySQL Workbench installed and running.

* * *

### Execution Instructions

### 1\. Download Jars

*   Download the server.jar and client.jar files from the repository.

### 2\. Database Configuration (MySQL)

To enable game persistence, follow these steps:

1.  Open **MySQL Workbench**.
2.  Create and start a **local connection** (Local Instance) using the credentials specified in the database.properties file (host, port, username, password).
3.  To import the database dump:
    *   In the left sidebar of Workbench, click on **Data Import/Restore**.
    *   Select the **Import from Self-Contained File** option.
    *   Download the file 'mesos_database_dump.sql' from the repository.
    *   Click the **Start Import** button in the bottom right corner to generate the required schema and tables.

### 3\. Running the Application

*   Open your **command prompt**.
*   Navigate to the folder where the .jar files are located.
*   Start both files using : java -jar 'JAR_NAME'. Start the server before the client or the game will not start.

###  Paying Across Multiple Computers (LAN)
If you want to play with multiple computers connected to the same network, the clients need to know the Server's IP address.
Find the Server IP:
* On the machine running the server, open the command prompt and type: 'ipconfig'.
* Look for the IPv4 Address (e.g., 192.168.1.X) under your active network adapter.Use this IP address on the client machines to connect to the server.



    
