## Table of Contents
- [ExpenSys](#ExpenSys)
- [Intuition](#intuition)
- [Key Features](#key-features)
    - [Expense Logging](#expense-logging)
    - [Monthly Reports](#monthly-reports)
    - [Categorization](#categorization)
    - [Subcategories](#subcategories)
    - [User-Specific Reports](#user-specific-reports)
    - [User-Centric View](#user-centric-view)
    - [Subcategory Mapping](#subcategory-mapping)
    - [Database Integration](#database-integration)

- [API Contract](#expensys-openapi-contract)
- [How to Use](#how-to-use)
- [Contribution Guidelines](#contribution-guidelines)


# **ExpenSys**

**ExpenSys** is an efficient **expense tracking** application designed to help you manage your **finances** and maintain a detailed record of your **expenses**. Whether you are an individual looking to **budget** your personal **finances** or a **business owner** tracking **expenses**, **ExpenSys** streamlines the process for you.

For this, I've made the UI from scratch as well and it's a responsive design. Few screenshots from the application.
### **Add Expense**
![Screenshot 2024-01-30 at 7 34 01 PM](https://github.com/pradipmudi/expensys/assets/6489613/cdd600bc-c566-45fd-a376-2e2d22d4d7cb)
![Screenshot 2024-01-30 at 7 49 43 PM](https://github.com/pradipmudi/expensys/assets/6489613/487f6976-9b11-4e0a-8310-aab240e90f33)
### **View expenses**
![Screenshot 2024-01-30 at 7 44 15 PM](https://github.com/pradipmudi/expensys/assets/6489613/ca1eb21e-3363-4a6d-8d48-470a43bc9bcd)
![Screen Shot 2024-01-30 at 19 45 36](https://github.com/pradipmudi/expensys/assets/6489613/2f7ef909-50d7-41ad-be4b-77dabe43feb2)
### Monthly Report
#### **By Main category and spent by user**
![Screen Shot 2024-01-30 at 19 52 08](https://github.com/pradipmudi/expensys/assets/6489613/e5d0f9de-fa55-455a-9fcf-8eb4d337ca05)
![Screen Shot 2024-01-30 at 19 38 14](https://github.com/pradipmudi/expensys/assets/6489613/d6827927-375a-458d-a5fc-0ceb7b88e288)
#### **By Sub category and spent by user**
![Screen Shot 2024-01-30 at 19 33 25](https://github.com/pradipmudi/expensys/assets/6489613/d8e30e90-82fe-41bd-99ce-2da946a357b7)
![Screen Shot 2024-01-30 at 19 30 26](https://github.com/pradipmudi/expensys/assets/6489613/21781599-a1aa-4382-8fe4-1bd88475d859)

#### **By Sub category and spent by all**
![Screen Shot 2024-01-30 at 19 30 12](https://github.com/pradipmudi/expensys/assets/6489613/2607f71a-ac61-474b-8c43-e7841979747e)

**Generated the logo for my application from here:** https://app.logo.com/dashboard/

## Intuition
I found it challenging to track my daily **expenses** using traditional methods such as Google Spreadsheets or existing **expense management apps**. They often lacked the flexibility and data organization I needed. As a result, I decided to develop **ExpenSys** to address these issues and create a more user-friendly **expense tracking** solution. Please feel free to submit **issues**, **feature requests** or **pull requests**; I'm open to accommodating them in future releases.

## Key Features

**ExpenSys** offers several key features to enhance your **expense tracking** experience:

### **Expense Logging**
- Users can easily log their daily **expenses** into the system, providing a detailed record of their spending.

### **Monthly Reports**
- Generate **monthly reports** based on your spending, allowing you to track your financial habits over time.

### **Categorization**
- **Expenses** are categorized into main categories, including **"Essential", "Expense", and "Loss of Money".** Users can also view **monthly reports** based on these categories.

### **Subcategories**
- **Subcategories** provide more granularity, such as **"Groceries", "Vegetables & Fruits", "Outside Food", "Salon", "Transport", "Medical", "Shopping", and "Loss of Money"**. These **subcategories** are displayed alongside their respective main categories.

### **User-Specific Reports**
- Users can select a specific month or view records for all months. They can also filter data based on categories (main or sub) and view **expenses** for all users.

### **User-Centric View**
- **ExpenSys** offers the ability to see reports based on main categories, allowing users to track their spending patterns effectively.

### **Subcategory Mapping**
- The application includes a dictionary of mappings for **subcategories**, ensuring consistency and clarity in **expense tracking**.

### **Database Integration**
- Users can add **expenses** directly into the database, ensuring data accuracy and easy retrieval.

### **Sorting**
- We can sort the result by any field based on report

## **How to Use**

To get started with **ExpenseTrackr**, follow these steps:

1. Clone the repository.
2. Set up the necessary **database** for **expense recording**.
3. Update DB configs on **application.yml**
4. Run the application.
5. Begin logging your daily **expenses** and utilize the various reporting and filtering options to manage your **financial data** effectively.

Your feedback and contributions are valuable to me, and I look forward to enhancing **ExpenTrackr** further based on your needs.
### **How to use the app in your local?**
These are the UI endpoints:
- **Add expense:** http://localhost:8080/new
- **View report:** http://localhost:8080/
- **View Expenses** http://localhost:8080/expenses

**Current TODO:** I plan to make the design more user friendly by creating a panel to redirect to the above pages. Please feel free to raise an PR on the same if you've already.

## **ExpenSys OpenAPI Contract**

The ExpenSys OpenAPI contract defines the interface for interacting with the ExpenSys application through its API. This contract allows developers to understand and integrate with ExpenSys seamlessly. You can access the full OpenAPI specification directly through Swagger UI:

[ExpenSys OpenAPI Contract - Swagger UI](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/pradipmudi/expensys/main/src/main/java/com/expensys/openapi/expensys_openapi.yml#/default/get_report)

Simply click on the link above to view and interact with the ExpenSys OpenAPI contract in your web browser.

The ExpenSys OpenAPI contract is the foundation for building applications that interact with ExpenSys. If you have any questions or need further assistance, please don't hesitate to reach out. Happy API development!


## Contribution Guidelines

If you have any **feature requests** or would like to **contribute** to **ExpenSys**, please follow our contribution guidelines and submit **pull requests**. I'm committed to continually improving the application to meet the diverse needs of the users.

Thank you for choosing **ExpenSys** for your **expense tracking** needs!
