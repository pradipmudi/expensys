document.addEventListener('DOMContentLoaded', function() {
    // --- DOM Elements ---
    const transactionsContainer = document.getElementById('transactionsContainer');
    const monthSelect = document.getElementById('monthSelect');
    const yearFilter = document.getElementById("year-filter");
    const nextPageButtonTop = document.querySelector('#paginationTop button:last-of-type');
    const nextPageButtonBottom = document.querySelector('#paginationBottom button:last-of-type');
    const loadingSpinner = document.getElementById('loadingSpinner');
    const errorMessage = document.getElementById('errorMessage');
    const prevPageButtonTop = document.querySelector('#paginationTop button:first-of-type');
    const prevPageButtonBottom = document.querySelector('#paginationBottom button:first-of-type');

    // --- State ---
    let currentPage = 1;
    const itemsPerPage = 10;
    let currentSortField = null;
    let currentSortOrder = 1;
    let data = [];
    let isMobileView = window.innerWidth <= 768;

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
        OTHERS: 'Others'
    };

    const emojis = {
        date: '📅',
        item: '🛒',
        category: '🏷️',
        spent: '💵',
        spentBy: '👤'
    };

    // --- Functions ---
    function renderTransactionsForMobile(data) {
        transactionsContainer.innerHTML = '';
        const transactionsHTML = data.map(transaction => `
            <div class="transaction">
                <p class="info" data-label="date"><span style="font-weight: bold;">${emojis.date} Date:</span> ${transaction.date}</p>
                <p class="info" data-label="item"><span style="font-weight: bold;">${emojis.item} Item:</span> ${transaction.item}</p>
                <p class="info" data-label="category"><span style="font-weight: bold;">${emojis.category} Category:</span> ${categoryKeyToLabelMappings[transaction.category] || transaction.category}</p>
                <p class="info" data-label="spent"><span style="font-weight: bold;">${emojis.spent} Spent:</span> ${typeof transaction.spent === 'number' ? transaction.spent.toFixed(2) : transaction.spent}</p>
                <p class="info" data-label="spentBy"><span style="font-weight: bold;">${emojis.spentBy} Spent By:</span> ${transaction.spentBy}</p>
            </div>`).join('');
        transactionsContainer.innerHTML = transactionsHTML;
    }

    function renderTransactionsForWeb(data) {
        transactionsContainer.innerHTML = '';
        const tableHeader = `
            <table id="expenseTable" class="responsive-table">
                <thead>
                <tr>
                    <th class="sortable" data-field="date">${emojis.date} Date <span class="sort-icon" id="sort-date-icon"></span></th>
                    <th class="sortable" data-field="item">${emojis.item} Item <span class="sort-icon" id="sort-item-icon"></span></th>
                    <th class="sortable" data-field="category">${emojis.category} Category <span class="sort-icon" id="sort-category-icon"></span></th>
                    <th class="sortable" data-field="spent">${emojis.spent} Spent <span class="sort-icon" id="sort-spent-icon"></span></th>
                    <th class="sortable" data-field="spentBy">${emojis.spentBy} Spent By <span class="sort-icon" id="sort-spentBy-icon"></span></th>
                </tr>
                </thead>
                <tbody>
        `;

        const tableRows = data.map(transaction => `
            <tr>
                <td>${transaction.date}</td>
                <td>${transaction.item}</td>
                <td>${categoryKeyToLabelMappings[transaction.category] || transaction.category}</td>
                <td>${typeof transaction.spent === 'number' ? transaction.spent.toFixed(2) : transaction.spent}</td>
                <td>${transaction.spentBy}</td>
            </tr>
        `).join('');

        transactionsContainer.innerHTML = tableHeader + tableRows + `</tbody></table>`;
        
        // Add sort listeners
        document.querySelectorAll('.sortable').forEach(th => {
            th.addEventListener('click', () => sortColumn(th.dataset.field));
        });
        updateSortIcons();
    }

    function renderTransactions(data) {
        if (isMobileView) {
            renderTransactionsForMobile(data);
        } else {
            renderTransactionsForWeb(data);
        }
    }

    function fetchExpenseData() {
        let selectedYear = yearFilter.value;
        const selectedMonth = monthSelect.value;
        
        // SELF-HEALING LOGIC: If year is missing, default to current year
        if (!selectedYear) {
            selectedYear = new Date().getFullYear();
            // Try to sync the UI if possible, but don't block
            if (yearFilter.options.length > 0) {
                yearFilter.value = selectedYear;
            }
        }

        const apiUrl = `/expense/${selectedYear}/${selectedMonth}?page=${currentPage}&itemsPerPage=${itemsPerPage}&sortField=${currentSortField || ''}&sortOrder=${currentSortOrder === 1 ? 'asc' : 'desc'}`;

        loadingSpinner.style.display = 'block';
        errorMessage.style.display = 'none';

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) throw new Error(`API request failed: ${response.status}`);
                return response.json();
            })
            .then(apiResponse => {
                if (apiResponse.success) {
                    data = apiResponse.data;
                } else {
                    throw new Error(apiResponse.message || "Unknown error");
                }

                renderTransactions(data);
                updatePaginationButtons();
                if (data.length === 0) {
                    errorMessage.innerHTML = `No data available.`;
                    errorMessage.style.display = 'block';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                errorMessage.innerHTML = `Error: ${error.message}`;
                errorMessage.style.display = 'block';
                data = [];
                renderTransactions([]);
                updatePaginationButtons();
            })
            .finally(() => {
                loadingSpinner.style.display = 'none';
            });
    }

    function sortColumn(field) {
        if (currentSortField === field) {
            currentSortOrder *= -1;
        } else {
            currentSortField = field;
            currentSortOrder = 1;
        }
        currentPage = 1;
        fetchExpenseData();
    }
    
    function updateSortIcons() {
        document.querySelectorAll(".sort-icon").forEach(icon => (icon.textContent = ''));
        if (currentSortField) {
            const icon = document.getElementById(`sort-${currentSortField}-icon`);
            if (icon) icon.textContent = currentSortOrder === 1 ? '▲' : '▼';
        }
    }

    function nextPage() {
        currentPage++;
        fetchExpenseData();
        updateCurrentPageElement();
    }

    function previousPage() {
        if (currentPage > 1) {
            currentPage--;
            fetchExpenseData();
            updateCurrentPageElement();
        }
    }

    function updateCurrentPageElement() {
        document.getElementById('currentPageTop').textContent = currentPage;
        document.getElementById('currentPageBottom').textContent = currentPage;
    }
    
    function updatePaginationButtons() {
        const hasData = data.length > 0;
        const isLastPage = data.length < itemsPerPage; // Simple heuristic
        
        nextPageButtonTop.disabled = !hasData || isLastPage;
        nextPageButtonBottom.disabled = !hasData || isLastPage;
        
        prevPageButtonTop.disabled = currentPage === 1;
        prevPageButtonBottom.disabled = currentPage === 1;
    }

    // --- Initialization ---
    function initialize() {
        // 1. Populate Months
        const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        months.forEach(month => {
            const option = document.createElement('option');
            option.value = month.toUpperCase();
            option.textContent = month;
            monthSelect.appendChild(option);
        });

        const savedMonth = localStorage.getItem('selectedMonth');
        if (savedMonth) monthSelect.value = savedMonth;

        // 2. Populate Years
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

        // 3. Event Listeners
        monthSelect.addEventListener('change', () => {
            localStorage.setItem('selectedMonth', monthSelect.value);
            currentPage = 1;
            fetchExpenseData();
        });
        yearFilter.addEventListener('change', () => {
            currentPage = 1;
            fetchExpenseData();
        });
        
        nextPageButtonTop.addEventListener('click', nextPage);
        nextPageButtonBottom.addEventListener('click', nextPage);
        prevPageButtonTop.addEventListener('click', previousPage);
        prevPageButtonBottom.addEventListener('click', previousPage);

        window.addEventListener('resize', () => {
            isMobileView = window.innerWidth <= 768;
            renderTransactions(data);
        });

        // 4. Initial Fetch
        fetchExpenseData();
        updateCurrentPageElement();
    }

    initialize();
});
