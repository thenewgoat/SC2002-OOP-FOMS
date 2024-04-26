# Fastfood Ordering and Management System Application

![Lorem ipsum dolor sit amet, consectetur adipiscing elit  Morbi tempor euismod mauris vestibulum commodo  Nunc quis nisi massa  In pretium erat vitae nisi venenatis consequat  Donec eu tristique er](https://github.com/Sai-Ajay/SC2002-OOP-FOMS-2.0/assets/126130422/f2b28504-51cc-4224-ba1b-5a62934ef3d7)

The Fastfood Ordering and Management System (FOMS) Application enhances fast food restaurant operations by facilitating menu management, order placement, and payment processing. It provides real-time order tracking for customers and allows staff and managers to efficiently handle orders and menu updates. Additionally, admins can oversee company and staff management. This README file provides detailed instructions on how to clone, compile, and run the application, ensuring users can easily set up and navigate the system.

## Links: 
[UML Class Diagram](https://github.com/Sai-Ajay/SC2002-OOP-FOMS-2.0/tree/main/foms/umldiagram) | Report | [Java Docs](https://github.com/Sai-Ajay/SC2002-OOP-FOMS-2.0/tree/main/foms/docs)

## Team Members
| Name               | GitHub Account      |
| ------------------ | ------------------- |
| Sally Ngui Yu Ying | [acrylicruler](https://github.com/acrylicruler)   |
| Shingamu Sai Ajay  | [Sai-Ajay](https://github.com/Sai-Ajay)           |
| Zhang Xinyang      | [thenewgoat](https://github.com/thenewgoat)       |
| Shen Jia Cheng     | [shenjc01](https://github.com/shenjc01)           |
| Sharanya Basu      | [SHAR0094](https://github.com/SHAR0094)             |

## FOMS Set-Up Instructions
### Compiling and Running the Code
#### Using the terminal
These setup instructions will guide you through the process of cloning the repository, navigating to the cloned repository, compiling the project, and running the project in your terminal.

1. Open your terminal

2. Clone the repository by entering the following command:
   ```Bash
   git clone https://github.com/Sai-Ajay/SC2002-OOP-FOMS-2.0.git
   ```
3. Navigate to the cloned repository by entering the following command:
   ```Bash
   cd SC2002-OOP-FOMS-2.0
   ```
4. Compile the project by entering the following command:
   ```Bash
   javac -cp foms/src -d bin foms/src/main/FomsApp.java
   ```
5. Run the project by entering the following command:
   ```Bash
   java -cp bin main.FomsApp
   ```
Congratulations, you have successfully cloned, compiled, and run the FOMS project!

## Generating Javadocs
### Using the terminal
Follow the steps below to generate JavaDocs using the terminal:

1. Open your terminal.

2. Navigate to the root directory of the project. Or enter the following command.
   ```Bash
   cd SC2002-OOP-FOMS-2.0/foms/src
   ```
3. Run the following command in the terminal:
   ```Bash
    javadoc -d ../docs -author -private -noqualifier all -version "controllers" "enums" "interfaces" "main" "models" "services" "stores" "test" "utils" "utils.exceptions" "views"
   ```
   This command will generate the JavaDocs and save them in the docs directory in HTML format.

4. Navigate to the docs directory using the following command:
   ```Bash
   cd ../docs
   ```
5. Open the index.html file in a web browser to view the generated JavaDocs.

Congratulations, you have successfully created and viewed the JavaDocs.













