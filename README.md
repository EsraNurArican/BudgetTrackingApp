# Budget Tracker

A simple Android app for tracking daily expenses, built using **MVVM architecture**, **Room Database**, **Dependency Injection with Hilt**, and **Data Binding**.

## Technologies Used

- **MVVM Architecture**: 
  - Separates business logic from UI. The **ViewModel** handles data and business logic, while the **Fragment** handles UI updates
  - **AddExpenseViewModel** manages user inputs and communicates with the repository to save expenses in the Room database.
  - **MainScreenViewModel** fetches expenses and total balance from db, with the UI automatically updating using LiveData.
  - **Data Binding** ensures that changes in the UI (e.g., input values) are reflected in the ViewModel.

- **Room Database**: 
  - Used for persisting expenses, with **ExpenseDao** handling data access and manipulation.

- **Dependency Injection (DI) with Hilt**:
  - **Hilt** injects the `ExpenseRepository` and `ExpenseDao` into the ViewModel and other components. It ensures efficient object management across the app's lifecycle.

- **Data Binding**:
  - **Two-way data binding** is used for fields like amount, category, and date, allowing UI changes to be reflected in the ViewModel and vice versa. **Binding Adapters** handle formatting and UI management based on expense type.

## Key Features:

1. **Expense Entry**: 
   - Users can input details like category, amount, date, and type (income/expense). Data is sent to the ViewModel using two-way data binding.

2. **Expense List**:
   - Expenses are displayed dynamically using LiveData, with real-time updates when the database changes.

3. **Total Balance**:
   - The total balance is calculated based on income and expenses, updated in real-time with LiveData.




