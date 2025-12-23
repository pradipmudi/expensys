
document.addEventListener('DOMContentLoaded', function() {
    // --- DOM Elements ---
    const yearFilter = document.getElementById("year-filter");
    const monthFilter = document.getElementById("month-filter");
    const categoryFilter = document.getElementById("category-filter");
    const spentByFilter = document.getElementById("spent-by-filter");
    const monthlyReport = document.getElementById("monthly-report");

    // --- State ---
    let currentSortField = null;
    let currentSortOrder = 1;

    // --- Mappings ---
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
        GROCERIES: '🛒',
        VEGETABLES_FRUITS_DAIRY_AND_MEAT: '🥦🍏🥛🥩',
        MEDICAL: '️💊',
        OUTSIDE_FOOD: '🍔',
        LOSE_OF_MONEY: '💸',
        SALON_AND_COSMETICS: '💇‍♀️',
        TRANSPORT: '🚗',
        SHOPPING: '🛍️',
        ENTERTAINMENT: '🎬',
        RENT_AND_OTHER_BILLS: '🏠',
        INVESTMENTS: '💼',
        ESSENTIAL: '🆘',
        EXPENSE: '💰',
        OTHERS: '🌟'
    };

    // --- Functions ---
    async function updateReport() {
        try {
            let selectedYear = yearFilter.value;
            const selectedMonth = monthFilter.value;
            const selectedCategory = categoryFilter.value;
            const selectedSpentBy = spentByFilter.value;

            // SELF-HEALING LOGIC: If year is missing, default to current year
            if (!selectedYear) {
                selectedYear = new Date().getFullYear();
                if (yearFilter.options.length > 0) {
                    yearFilter.value = selectedYear;
                }
            }

            const apiUrl = `/expense/report?year=${selectedYear}&month=${selectedMonth}&category=${selectedCategory}&spentBy=${selectedSpentBy}`;
            const response = await fetch(apiUrl);

            if (!response.ok) {
                throw new Error(`API request failed with status: ${response.status}`);
            }

            const apiResponse = await response.json();
            
            let data = [];
            if (apiResponse.success) {
                data = apiResponse.data;
            } else {
                throw new Error(apiResponse.message || "Unknown error occurred");
            }

            monthlyReport.innerHTML = "";

            if (data.length === 0) {
                monthlyReport.innerHTML = "<p>No report data available for the selected filters.</p>";
            } else {
                data.forEach(monthData => {
                    const reportCard = createReportCard(monthData, selectedSpentBy);
                    monthlyReport.appendChild(reportCard);
                });
            }
        } catch (error) {
            console.error(error);
            monthlyReport.innerHTML = `<p style="color: red;">Error loading report: ${error.message}</p>`;
        }
    }

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
                        <th class="sortable" data-field="category">Category
                            <span class="sort-icon" id="sort-category-icon"></span>
                        </th>
                        <th class="sortable" data-field="spent">Spent
                            <span class="sort-icon" id="sort-spent-icon"></span>
                        </th>
                        ${selectedSpentBy === "ALL" ? "" : `<th class="sortable" data-field="spentBy">Spent By
                            <span class="sort-icon" id="sort-spentBy-icon"></span>
                        </th>`}
                    </tr>
                </thead>
                <tbody>
                    ${sortReportData(data.reportInfo, selectedSpentBy).map(info => `
                        <tr>
                            <td>${categoryKeyToEmojiMappings[info.subCategory] || '🏷️'} ${categoryKeyToLabelMappings[info.subCategory] || info.subCategory}</td>
                            <td>₹${parseFloat(info.spent).toFixed(2)}</td>
                            ${selectedSpentBy === "ALL" ? "" : `<td>${info.spentBy}</td>`}
                        </tr>
                    `).join('')}
                </tbody>
            </table>`;
        
        // Add event listeners for sorting after the card is created
        reportCard.querySelector('[data-field="category"]').addEventListener('click', () => sortColumn('category'));
        reportCard.querySelector('[data-field="spent"]').addEventListener('click', () => sortColumn('spent'));
        if (selectedSpentBy !== "ALL") {
            reportCard.querySelector('[data-field="spentBy"]').addEventListener('click', () => sortColumn('spentBy'));
        }

        return reportCard;
    }

    function sortColumn(field) {
        if (currentSortField === field) {
            currentSortOrder *= -1;
        } else {
            currentSortField = field;
            currentSortOrder = 1;
        }
        updateReport();
    }

    function sortReportData(reportInfo, selectedSpentBy) {
        if (!currentSortField) return reportInfo;

        return [...reportInfo].sort((a, b) => {
            let valA, valB;
            switch (currentSortField) {
                case 'category':
                    valA = a.subCategory;
                    valB = b.subCategory;
                    return valA.localeCompare(valB) * currentSortOrder;
                case 'spent':
                    valA = a.spent;
                    valB = b.spent;
                    return (valA - valB) * currentSortOrder;
                case 'spentBy':
                    valA = a.spentBy;
                    valB = b.spentBy;
                    return valA.localeCompare(valB) * currentSortOrder;
                default:
                    return 0;
            }
        });
    }

    // --- Initial Setup ---
    function initialize() {
        // 1. Populate Years
        const currentYear = new Date().getFullYear();
        const startYear = 2022;
        yearFilter.innerHTML = '';
        for (let year = currentYear; year >= startYear; year--) {
            const option = document.createElement('option');
            option.value = year;
            option.textContent = year;
            yearFilter.appendChild(option);
        }
        
        // Set value explicitly
        yearFilter.value = currentYear;

        // 2. Add Event Listeners
        yearFilter.addEventListener("change", updateReport);
        monthFilter.addEventListener("change", updateReport);
        categoryFilter.addEventListener("change", updateReport);
        spentByFilter.addEventListener("change", updateReport);
        
        // 3. Initial data load
        updateReport();
    }

    initialize();
});
