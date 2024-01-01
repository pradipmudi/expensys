
    const yearFilter = document.getElementById("year-filter");
    const monthFilter = document.getElementById("month-filter");
    const categoryFilter = document.getElementById("category-filter");
    const spentByFilter = document.getElementById("spent-by-filter");
    const monthlyReport = document.getElementById("monthly-report");
    let currentSortField = null;
    let currentSortOrder = 1;
    const categoryKeyToLabelMappings = {
        GROCERIES: 'Groceries',
        VEGETABLES_FRUITS_DAIRY_AND_MEAT: 'Vegetables, Fruits, Dairy and Meat',
        MEDICAL: 'Medical',
        OUTSIDE_FOOD: 'Outside Food',
        LOSE_OF_MONEY: 'Loss of Money',
        SALON_AND_COSMETICS: 'Salon and Beauty Products',
        TRANSPORT: 'Transport',
        SHOPPING: 'Shopping',
        ENTERTAINMENT: 'Entertainment',
        RENT_AND_OTHER_BILLS: 'Rent and Other Bills',
        INVESTMENTS: 'Investments',
        OTHERS: 'Others',
        ESSENTIAL: 'Essential',
        EXPENSE: 'Expense'
    };

    const categoryKeyToEmojiMappings = {
        GROCERIES: '🛒', // Groceries
        VEGETABLES_FRUITS_DAIRY_AND_MEAT: '🥦🍏🥛🥩', // Vegetables, Fruits, Dairy and Meat
        MEDICAL: '️💊', // Medicine and other medical expenses
        OUTSIDE_FOOD: '🍔', // Outside Food
        LOSS_OF_MONEY: '💸', // Loss of Money
        SALON_AND_COSMETICS: '💇‍♀️', // Salon and Beauty Products
        TRANSPORT: '🚗', // Transport related expenses
        SHOPPING: '🛍️', // Shopping
        ENTERTAINMENT: '🎬', // Entertainment
        RENT_AND_OTHER_BILLS: '🏠', // Rent and Other Bills
        INVESTMENTS: '💼', // Investments
        ESSENTIAL: '🆘', // Essentials
        EXPENSE: '💰', // Expense
        OTHERS: '🌟' // Others
    };




    // Function to fetch and update the data based on filters
    async function updateReport() {
        try {
            const selectedYear = yearFilter.value;
            const selectedMonth = monthFilter.value;
            const selectedCategory = categoryFilter.value;
            const selectedSpentBy = spentByFilter.value;

            // Make an API request based on selected filters
            const apiUrl = `http://localhost:8080/expense/report?year=${selectedYear}&month=${selectedMonth}&category=${selectedCategory}&spentBy=${selectedSpentBy}`;
            const response = await fetch(apiUrl);

            if (!response.ok) {
                throw new Error(`No data available or API request failed with status: ${response.status}`);
            }

            const data = await response.json();

            // Clear previous data
            monthlyReport.innerHTML = "";

            // Show data for the selected months
            data.forEach(monthData => {
                const reportCard = createReportCard(monthData, selectedSpentBy);
                monthlyReport.appendChild(reportCard);
            });
        } catch (error) {
            console.error(error);
        }
    }

    // Function to create a report card
    function createReportCard(data, selectedSpentBy) {
        const reportCard = document.createElement("div");
        reportCard.className = "report-card";
        const formattedTotalSpendings = parseFloat(data.totalSpendings).toFixed(2);
        reportCard.innerHTML = `
            <h2>${data.month}</h2>
            <h3>Total Spendings: ₹${formattedTotalSpendings}</h3>
            <table>
                <thead>
                    <tr>
                        <th class="sortable" data-field="category" onclick="sortColumn('category')">Category
                            <span class="sort-icon" id="sort-category-icon">
                                ${currentSortField === 'category' ? (currentSortOrder === 1 ? '▲' : '▼') : ''}
                            </span>
                        </th>
                        <th class="sortable" data-field="spent" onclick="sortColumn('spent')">Spent
                            <span class="sort-icon" id="sort-spent-icon">
                                ${currentSortField === 'spent' ? (currentSortOrder === 1 ? '▲' : '▼') : ''}
                            </span>
                        </th>
                        ${selectedSpentBy === "ALL" ? "" : `<th class="sortable" data-field="spentBy" onclick="sortColumn('spentBy')">Spent By
                            <span class="sort-icon" id="sort-spentBy-icon">
                                ${currentSortField === 'spentBy' ? (currentSortOrder === 1 ? '▲' : '▼') : ''}
                            </span>
                        </th>`}
                    </tr>
                </thead>
                <tbody>
                    ${sortReportData(data.reportInfo, selectedSpentBy).map(info => `
                        <tr>
                            <td>${categoryKeyToEmojiMappings[info.subCategory]} ${categoryKeyToLabelMappings[info.subCategory]}</td>
                            <td>₹${parseFloat(info.spent).toFixed(2)}</td>
                            ${selectedSpentBy === "ALL" ? "" : `<td>${info.spentBy}</td>`}
                        </tr>
                    `).join('')}
                </tbody>
            </table>`;

        return reportCard;
    }

    // Function to sort column and update sorting icon
    function sortColumn(field) {
        if (currentSortField === field) {
            currentSortOrder *= -1; // Toggle between ascending and descending
        } else {
            currentSortField = field;
            currentSortOrder = 1; // Set to ascending by default
        }

        // Update the sort icons based on the sort order
        const sortIcons = document.querySelectorAll(".sort-icon");
        sortIcons.forEach(icon => (icon.textContent = ''));

        // Update the icon for the clicked column
        const icon = document.getElementById(`sort-${field}-icon`);
        icon.textContent = currentSortOrder === 1 ? '▲' : '▼';

        updateReport();
    }

    // Function to sort report data based on the selected field
    function sortReportData(reportInfo, selectedSpentBy) {
        if (currentSortField === 'category') {
            return reportInfo.slice().sort((a, b) => a.subCategory.localeCompare(b.subCategory) * currentSortOrder);
        } else if (currentSortField === 'spent') {
            return reportInfo.slice().sort((a, b) => (a.spent - b.spent) * currentSortOrder);
        } else if (currentSortField === 'spentBy') {
            return reportInfo.slice().sort((a, b) => a.spentBy.localeCompare(b.spentBy) * currentSortOrder);
        } else {
            return reportInfo;
        }
    }

    // Listen for changes in filters and update the report
    monthFilter.addEventListener("change", updateReport);
    categoryFilter.addEventListener("change", updateReport);
    spentByFilter.addEventListener("change", updateReport);
    yearFilter.addEventListener("change", updateReport);

    // Initial update
    updateReport();
