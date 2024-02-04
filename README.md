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
![Screen Shot 2024-01-30 at 19 38 14](https://github.com/pradipmudi/expensys/assets/6489613/f7aa06b2-2a85-463a-8752-bd21f4ef986a)
![Screen Shot 2024-01-30 at 19 33 25](https://github.com/pradipmudi/expensys/assets/6489613/3ac4f2a3-fe99-45a2-8426-36855f02a0fb)
![Screen Shot 2024-01-30 at 19 30 26](https://github.com/pradipmudi/expensys/assets/6489613/8e3ff050-62c6-46cf-923d-b826e9f646ba)
![Screen Shot 2024-01-30 at 19 30 12](https://github.com/pradipmudi/expensys/assets/6489613/9f5e7c15-5af2-4e8a-bd41-e7624b5f2b81)
![Screenshot 2024-01-30 at 7 34 01 PM](https://github.com/pradipmudi/expensys/assets/6489613/aeb5e239-b1da-44e9-9ba0-dddd85d7b659)
![Screenshot 2024-01-30 at 7 44 15 PM](https://github.com/pradipmudi/expensys/assets/6489613/16bcc45d-fe49-47f0-83f0-3d76d8a06bba)
![Screen Shot 2024-01-30 at 19 45 36](https://github.com/pradipmudi/expensys/assets/6489613/69e8b80e-ac48-450b-9e6a-a1d92015d243)
![Screenshot 2024-01-30 at 7 49 43 PM](https://github.com/pradipmudi/expensys/assets/6489613/d400dcff-06cf-4650-8909-d0b02ab4f235)
![Screen Shot 2024-01-30 at 19 52 08](https://github.com/pradipmudi/expensys/assets/6489613/e654306f-0007-44fe-a550-601de481e58f)

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
