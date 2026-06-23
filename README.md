# ing-sw-2026-Grillo-Iavarone-Luzzi-Hu

### 👥 Group Members

*   **Grillo Giuseppepio Salvatore Pasquale**
*   **Hu Xiayi**
*   **Iavarone Mattia**
*   **Luzzi Denise**

* * *

### 🎯 Implemented Features

*   **Game Rules**: Complete Game Rules.
*   **User Interfaces**: Dual interface support.
    *   **TUI** (Text-based User Interface) via terminal.
    *   **GUI** (Graphical User Interface) via windows.
*   **Connection Types**: Network architecture supporting both:
    *   **Socket**
    *   **RMI**
*   **Advanced Feature**: Game history persistence via **Database**.

* * *

### 💻 System Prerequisites

*   **Java**: JDK 17 or higher installed.
*   **Database**: MySQL Server and MySQL Workbench installed and running.

* * *

### 🚀 Execution Instructions

### 1\. Download Binaries

*   Download the `server.jar` and `client.jar` files from the project release/distribution folder.

### 2\. Database Configuration (MySQL)

To enable game persistence, follow these steps:

1.  Open **MySQL Workbench**.
2.  Create and start a **local connection** (Local Instance) using the credentials specified in the `database.properties` file (host, port, username, password).
3.  To import the database dump:
    *   In the left sidebar of Workbench, click on **Data Import/Restore**.
    *   Select the **Import from Self-Contained File** option.
    *   Browse and select the project's `.sql` dump file.
    *   Click the **Start Import** button in the bottom right corner to generate the required schema and tables.

### 3\. Running the Application

*   Open your **command prompt** (or terminal).
*   Navigate to the folder where the `.jar` files are located:
    
    bash
    
        cd /path/to/your/folder
        
    
    Usa il codice con cautela.
    
*   Launch the **Server** component first:
    
    bash
    
        java -jar server.jar
        
    
    Usa il codice con cautela.
    
*   Open a new command prompt window, navigate to the same folder, and launch the **Client**:
    
    bash
    
        java -jar client.jar
        
    
    Usa il codice con cautela.
    

%%MAGIT\_PARSER\_PROTECT%% \`\`\`
