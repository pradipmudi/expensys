let currentPage = 1;
const itemsPerPage = 10; // Adjust as needed
let currentSortField = null;
let currentSortOrder = 1;
let data = []; // Declare data at a higher scope
let isMobileView = window.innerWidth <= 768;

const transactionsContainer = document.getElementById('transactionsContainer');
const monthSelect = document.getElementById('monthSelect');
const yearFilter = document.getElementById("year-filter");
const nextPageButtonTop = document.querySelector('#paginationTop button:last-of-type');
const nextPageButtonBottom = document.querySelector('#paginationBottom button:last-of-type');
const loadingSpinner = document.getElementById('loadingSpinner');
const errorMessage = document.getElementById('errorMessage');
let currentView = null; // Variable to store the current view type

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

var getCategoryMappingLabel = function(category) {
    if(category === null) return null;
    return categoryKeyToLabelMappings(category);
}

function renderTransactionsForMobile(data) {
    transactionsContainer.innerHTML = '';

    const transactionsHTML = data.map(transaction => {
        const transactionInfo = `
            <div class="transaction">
                <p class="info" data-label="date"><span style="font-weight: bold;">${emojis.date} Date:</span> ${transaction.date}</p>
                <p class="info" data-label="item"><span style="font-weight: bold;">${emojis.item} Item:</span> ${transaction.item}</p>
                <p class="info" data-label="category"><span style="font-weight: bold;">${emojis.category} Category:</span> ${categoryKeyToLabelMappings[transaction.category]}</p>
                <p class="info" data-label="spent"><span style="font-weight: bold;">${emojis.spent} Spent:</span> ${typeof transaction.spent === 'number' ? transaction.spent.toFixed(2) : transaction.spent}</p>
                <p class="info" data-label="spentBy"><span style="font-weight: bold;">${emojis.spentBy} Spent By:</span> ${transaction.spentBy}</p>
            </div>`;
        return transactionInfo;
    }).join('');

    transactionsContainer.innerHTML = transactionsHTML;
}

function renderTransactionsForWeb(data) {
    transactionsContainer.innerHTML = '';
    const tableHeader = `
        <table id="expenseTable" class="responsive-table">
            <thead>
            <tr>
                <th class="sortable" data-field="Date" onclick="sortColumn('date')">${emojis.date} Date
                    <span class="sort-icon" id="sort-date-icon"></span>
                </th>
                <th class="sortable" data-field="Item" onclick="sortColumn('item')">${emojis.item} Item
                    <span class="sort-icon" id="sort-item-icon"></span>
                </th>
                <th class="sortable" data-field="Category" onclick="sortColumn('category')">${emojis.category} Category
                    <span class="sort-icon" id="sort-category-icon"></span>
                </th>
                <th class="sortable" data-field="Spent" onclick="sortColumn('spent')">${emojis.spent} Spent
                    <span class="sort-icon" id="sort-spent-icon"></span>
                </th>
                <th class="sortable" data-field="Spent By" onclick="sortColumn('spentBy')">${emojis.spentBy} Spent By
                    <span class="sort-icon" id="sort-spentBy-icon"></span>
                </th>
            </tr>
            </thead>
            <tbody>
    `;

    const tableRows = data.map(transaction => `
        <tr>
            <td>${transaction.date}</td>
            <td>${transaction.item}</td>
            <td>${categoryKeyToLabelMappings[transaction.category]}</td>
            <td>${typeof transaction.spent === 'number' ? transaction.spent.toFixed(2) : transaction.spent}</td>
            <td>${transaction.spentBy}</td>
        </tr>
    `).join('');

    const tableFooter = `
            </tbody>
        </table>
    `;

    transactionsContainer.innerHTML = tableHeader + tableRows + tableFooter;
}

function renderTransactions(data) {
    const newIsMobileView = window.innerWidth <= 768; // Adjust this width for your mobile view

    if (newIsMobileView !== isMobileView) {
        isMobileView = newIsMobileView;
        location.reload(); // Refresh the page if the view changes
    } else {
        if (isMobileView) {
            renderTransactionsForMobile(data);
        } else {
            renderTransactionsForWeb(data);
        }
    }
}

function handleWindowResize() {
    window.addEventListener('resize', () => {
        renderTransactions(data);
    });
}

function fetchExpenseData() {
    const selectedYear = yearFilter.value;
    const selectedMonth = monthSelect.value;
    const apiUrl = `http://localhost:8080/expense/${selectedYear}/${selectedMonth}?page=${currentPage}&itemsPerPage=${itemsPerPage}&sortField=${currentSortField}&sortOrder=${currentSortOrder}`;

    loadingSpinner.innerHTML = 'Loading...';
    loadingSpinner.style.display = 'block';
    errorMessage.style.display = 'none';

    fetch(apiUrl)
        .then(response => {
        if (!response.ok) {
            throw new Error(`API request failed with status: ${response.status}`);
        }
        return response.json();
    })
        .then(responseData => {
        data = responseData;

        renderTransactions(data);

        nextPageButtonTop.disabled = data.length == 0;
        nextPageButtonBottom.disabled = data.length == 0;

        if (data.length > 0) {
            updateCurrentPageElement();
        }
        if(data.length < 10 || data.length == 0) {
            nextPageButtonTop.disabled = true;
            nextPageButtonBottom.disabled = true;
            if(data.length == 0){
                console.error('Error fetching data:', error);
                errorMessage.innerHTML = `No data available or there was some error fetching data. Please try again.<br>Error: ${error.message}`;
                errorMessage.style.display = 'block';
            }
        }
    })
        .catch(error => {
        console.error('Error fetching data:', error);
        errorMessage.innerHTML = `No data available or there was some error fetching data. Please try again.<br>Error: ${error.message}`;
        errorMessage.style.display = 'block';

        nextPageButtonTop.disabled = true;
        nextPageButtonBottom.disabled = true;
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

    document.querySelectorAll(".sort-icon").forEach(icon => (icon.textContent = ''));

    const icon = document.getElementById(`sort-${field}-icon`);
    icon.textContent = currentSortOrder === 1 ? '▲' : '▼';

    currentPage = 1;
    fetchExpenseData();
    updateCurrentPageElement();
}

function nextPage() {
    if (errorMessage.style.display === 'none') {
        currentPage++;
        fetchExpenseData();
    } else {
        console.error('Fix the error before going to the next page.');
    }
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

document.addEventListener('DOMContentLoaded', () => {
    const months = [
        "January", "February", "March", "April",
        "May", "June", "July", "August",
        "September", "October", "November", "December"
    ];

    months.forEach((month, index) => {
        const option = document.createElement('option');
        option.value = month.toUpperCase();
        option.textContent = month;
        monthSelect.appendChild(option);
    });

    const savedMonth = localStorage.getItem('selectedMonth');

    if (savedMonth) {
        monthSelect.value = savedMonth;
    }

    fetchExpenseData();
    updateCurrentPageElement();
    handleWindowResize();
});

monthSelect.addEventListener('change', () => {
    const selectedMonth = monthSelect.value;
    localStorage.setItem('selectedMonth', selectedMonth);
});

const debouncedFetchExpenseData = debounce(fetchExpenseData, 300);
const debouncedSortColumn = debounce(sortColumn, 300);
const debouncedNextPage = debounce(nextPage, 300);
const debouncedPreviousPage = debounce(previousPage, 300);
