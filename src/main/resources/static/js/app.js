// DOM Elements
const temperatureInput = document.getElementById('temperatureInput');
const convertBtn = document.getElementById('convertBtn');
const resultSection = document.getElementById('resultSection');
const resultValue = document.getElementById('resultValue');
const resultLabel = document.getElementById('resultLabel');
const formulaText = document.getElementById('formulaText');
const saveBtn = document.getElementById('saveBtn');
const nicknameInput = document.getElementById('nicknameInput');
const historyList = document.getElementById('historyList');
const clearHistoryBtn = document.getElementById('clearHistoryBtn');
const toastNotification = document.getElementById('toastNotification');
const toastMessage = document.getElementById('toastMessage');
const themeToggle = document.getElementById('themeToggle');

// Bootstrap Toast
const toast = new bootstrap.Toast(toastNotification);

// Initialize app
document.addEventListener('DOMContentLoaded', () => {
    loadHistory();
    initTheme();

    // Event listeners
    convertBtn.addEventListener('click', convertTemperature);
    saveBtn.addEventListener('click', saveToHistory);
    clearHistoryBtn.addEventListener('click', clearAllHistory);
    themeToggle.addEventListener('click', toggleTheme);

    // Enter key to convert
    temperatureInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            convertTemperature();
        }
    });

    // Radio button change animation
    document.querySelectorAll('input[name="unitToggle"]').forEach(radio => {
        radio.addEventListener('change', () => {
            if (resultSection.classList.contains('d-none') === false) {
                convertTemperature();
            }
        });
    });
});

// Convert temperature
let lastConversion = null;

async function convertTemperature() {
    const value = parseFloat(temperatureInput.value);

    // Validation
    if (isNaN(value)) {
        showToast('Please enter a valid number', 'warning');
        temperatureInput.focus();
        return;
    }

    // Get selected unit
    const fromUnit = document.querySelector('input[name="unitToggle"]:checked').value;

    // Show loading
    convertBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Converting...';
    convertBtn.disabled = true;

    try {
        // API Call
        const response = await fetch('/api/temperature/convert', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                value: value,
                fromUnit: fromUnit
            })
        });

        if (!response.ok) {
            throw new Error('Conversion failed');
        }

        const data = await response.json();

        // Store last conversion for saving
        lastConversion = data;

        // Display result with animation
        resultSection.classList.remove('d-none');
        resultSection.style.animation = 'none';
        setTimeout(() => {
            resultSection.style.animation = 'scaleIn 0.4s ease-out';
        }, 10);

        // Format and display
        const outputSymbol = data.outputUnit === 'CELSIUS' ? '°C' : '°F';
        resultValue.textContent = `${data.outputValue.toFixed(2)} ${outputSymbol}`;
        resultLabel.textContent = `${data.inputValue}${data.inputUnit === 'CELSIUS' ? '°C' : '°F'} = ${data.outputValue.toFixed(2)}${outputSymbol}`;
        formulaText.textContent = data.formula;

        // Clear nickname input for new conversion
        nicknameInput.value = '';

        showToast('Conversion completed!', 'info');

    } catch (error) {
        console.error('Error:', error);
        showToast('Failed to convert temperature. Please try again.', 'danger');
    } finally {
        // Reset button
        convertBtn.innerHTML = '<i class="bi bi-arrow-repeat"></i> Convert';
        convertBtn.disabled = false;
    }
}

// Save conversion to history
async function saveToHistory() {
    if (!lastConversion) {
        showToast('Please convert a temperature first', 'warning');
        return;
    }

    const nickname = nicknameInput.value.trim();

    // Show loading
    saveBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Saving...';
    saveBtn.disabled = true;

    try {
        const response = await fetch('/api/temperature/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                inputValue: lastConversion.inputValue,
                inputUnit: lastConversion.inputUnit,
                outputValue: lastConversion.outputValue,
                outputUnit: lastConversion.outputUnit,
                nickname: nickname || null
            })
        });

        if (!response.ok) {
            throw new Error('Failed to save');
        }

        await loadHistory();
        nicknameInput.value = '';
        showToast('Conversion saved to history!', 'success');

    } catch (error) {
        console.error('Error:', error);
        showToast('Failed to save conversion. Please try again.', 'danger');
    } finally {
        saveBtn.innerHTML = '<i class="bi bi-bookmark-check"></i> Save to History';
        saveBtn.disabled = false;
    }
}

// Load conversion history
async function loadHistory() {
    try {
        const response = await fetch('/api/temperature/history');

        if (!response.ok) {
            throw new Error('Failed to load history');
        }

        const history = await response.json();

        if (history.length === 0) {
            historyList.innerHTML = `
                <div class="text-center text-muted py-5">
                    <i class="bi bi-inbox display-1"></i>
                    <p class="mt-3">No conversion history yet</p>
                    <small>Convert temperatures and save them to see history</small>
                </div>
            `;
        } else {
            historyList.innerHTML = history.map(item => createHistoryItem(item)).join('');

            // Add delete listeners
            document.querySelectorAll('.btn-delete').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const id = e.target.closest('.btn-delete').dataset.id;
                    deleteHistoryItem(id);
                });
            });
        }
    } catch (error) {
        console.error('Error loading history:', error);
        historyList.innerHTML = `
            <div class="text-center text-danger py-5">
                <i class="bi bi-exclamation-triangle display-1"></i>
                <p class="mt-3">Failed to load history</p>
                <small>Please check if the database is running</small>
            </div>
        `;
    }
}

// Create history item HTML
function createHistoryItem(item) {
    const date = new Date(item.timestamp);
    const formattedDate = date.toLocaleString('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });

    const inputSymbol = item.inputUnit === 'CELSIUS' ? '°C' : '°F';
    const outputSymbol = item.outputUnit === 'CELSIUS' ? '°C' : '°F';

    const nicknameHtml = item.nickname
        ? `<div class="history-nickname"><i class="bi bi-tag-fill"></i> ${item.nickname}</div>`
        : '';

    return `
        <div class="history-item">
            <div class="history-content">
                <div class="history-values">
                    ${nicknameHtml}
                    <div class="history-conversion">
                        <i class="bi bi-thermometer-half text-primary"></i>
                        ${item.inputValue.toFixed(2)}${inputSymbol} 
                        <i class="bi bi-arrow-right text-muted"></i> 
                        ${item.outputValue.toFixed(2)}${outputSymbol}
                    </div>
                    <div class="history-timestamp">
                        <i class="bi bi-clock"></i> ${formattedDate}
                    </div>
                </div>
                <button class="btn btn-delete" data-id="${item.id}">
                    <i class="bi bi-trash"></i>
                </button>
            </div>
        </div>
    `;
}

// Delete single history item
async function deleteHistoryItem(id) {
    try {
        const response = await fetch(`/api/temperature/history/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to delete');
        }

        await loadHistory();
        showToast('History item deleted', 'success');
    } catch (error) {
        console.error('Error deleting history:', error);
        showToast('Failed to delete history item', 'danger');
    }
}

// Clear all history
async function clearAllHistory() {
    if (!confirm('Are you sure you want to clear all conversion history?')) {
        return;
    }

    try {
        const response = await fetch('/api/temperature/history', {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to clear history');
        }

        await loadHistory();
        showToast('All history cleared', 'success');
    } catch (error) {
        console.error('Error clearing history:', error);
        showToast('Failed to clear history', 'danger');
    }
}

// Show toast notification
function showToast(message, type = 'info') {
    const iconMap = {
        success: 'bi-check-circle-fill text-success',
        danger: 'bi-exclamation-circle-fill text-danger',
        warning: 'bi-exclamation-triangle-fill text-warning',
        info: 'bi-info-circle-fill text-primary'
    };

    const icon = iconMap[type] || iconMap.info;
    const toastHeader = toastNotification.querySelector('.toast-header i');

    toastHeader.className = `bi ${icon} me-2`;
    toastMessage.textContent = message;

    toast.show();
}

// Add smooth scroll
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth'
            });
        }
    });
});

// Auto-focus on input
temperatureInput.focus();

// Dark Mode Functions
function initTheme() {
    const savedTheme = localStorage.getItem('theme') || 'light';
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-mode');
        updateThemeIcon(true);
    }
}

function toggleTheme() {
    const isDark = document.body.classList.toggle('dark-mode');
    localStorage.setItem('theme', isDark ? 'dark' : 'light');
    updateThemeIcon(isDark);

    // Add animation feedback
    themeToggle.style.transform = 'rotate(360deg) scale(1.2)';
    setTimeout(() => {
        themeToggle.style.transform = '';
    }, 300);
}

function updateThemeIcon(isDark) {
    const icon = themeToggle.querySelector('i');
    if (isDark) {
        icon.className = 'bi bi-sun-fill';
    } else {
        icon.className = 'bi bi-moon-stars-fill';
    }
}

