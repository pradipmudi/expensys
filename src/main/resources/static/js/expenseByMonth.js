let currentPage = 1;
const itemsPerPage = 10; // Adjust as needed
let currentSortField = null;
let currentSortOrder = 1;
let data = []; // Declare data at a higher scope

const monthSelect = document.getElementById('monthSelect');
const tbody = document.querySelector('#expenseTable tbody');
const loadingSpinner = document.getElementById('loadingSpinner');
const errorMessage = document.getElementById('errorMessage');

function fetchExpenseData() {
    const selectedMonth = monthSelect.value;
    const apiUrl = `http://localhost:8080/expense/${selectedMonth}?page=${currentPage}&itemsPerPage=${itemsPerPage}&sortField=${currentSortField}&sortOrder=${currentSortOrder}`;

    loadingSpinner.innerHTML = 'Loading...';
    loadingSpinner.style.display = 'block';
    errorMessage.style.display = 'none';

    const nextPageButtonTop = document.querySelector('#paginationTop button:last-of-type');
    const nextPageButtonBottom = document.querySelector('#paginationBottom button:last-of-type');

    fetch(apiUrl)
        .then(response => {
        if (!response.ok) {
            throw new Error(`API request failed with status: ${response.status}`);
        }
        return response.json();
    })
        .then(responseData => {
        data = responseData; // Assign responseData to the data variable
        tbody.innerHTML = '';

        data.forEach(expense => {
            const row = tbody.insertRow(-1);
            ['date', 'item', 'category', 'spent', 'spentBy'].forEach((field, index) => {
                const cell = row.insertCell(index);
                if (field === 'spent') {
                    // Check if 'spent' is a number before formatting
                    const spentValue = typeof expense[field] === 'number' ? expense[field] : parseFloat(expense[field]);
                    if (!isNaN(spentValue)) {
                        const formattedSpent = spentValue.toFixed(2);
                        cell.textContent = formattedSpent;
                    } else {
                        cell.textContent = expense[field]; // If not a number, display original value
                    }
                } else {
                    cell.textContent = expense[field];
                }
                cell.setAttribute('data-label', field);
            });
        });


        document.getElementById('currentPageTop').textContent = currentPage;
        document.getElementById('currentPageBottom').textContent = currentPage;

        nextPageButtonTop.disabled = data.length < itemsPerPage;
        nextPageButtonBottom.disabled = data.length < itemsPerPage;

        if (data.length >= itemsPerPage) {
            updateCurrentPageElement();
        } else {
            // No more items on the current page
            nextPageButtonTop.disabled = true;
            nextPageButtonBottom.disabled = true;
        }
    })
        .catch(error => {
        console.error('Error fetching data:', error);
        errorMessage.innerHTML = `Error fetching data. Please try again.<br>Error: ${error.message}`;
        errorMessage.style.display = 'block';

        // No more items on the current page
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
});

monthSelect.addEventListener('change', () => {
    const selectedMonth = monthSelect.value;
    localStorage.setItem('selectedMonth', selectedMonth);
});