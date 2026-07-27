/* AQI Monitoring System - Light Theme Dashboard Logic */

const searchInput = document.getElementById("citySearch");
const suggestionsBox = document.getElementById("searchSuggestions");
const searchBtn = document.getElementById("searchBtn");
const loadingOverlay = document.getElementById("loadingOverlay");

const products = [
    {
        name: "3M N95 Respirator Mask",
        price: "₹569",
        image: "images/3M_N95.jpg",
        link: "https://www.amazon.in/s?k=3m+n95"
    },
    {
        name: "Reusable Anti-Pollution Mask",
        price: "₹299",
        image: "images/N95.jpg",
        link: "https://www.amazon.in/s?k=n95+mask"
    },
    {
        name: "LEVOIT HEPA Air Purifier",
        price: "₹9,999",
        image: "images/purifier.webp",
        link: "https://www.amazon.in/s?k=levoit+air+purifier"
    },
    {
        name: "Coway Airmega 150 Purifier",
        price: "₹15,998",
        image: "images/Coway_purifier.jpg",
        link: "https://www.amazon.in/s?k=coway+air+purifier"
    }
];

let debounceTimer;

document.addEventListener("DOMContentLoaded", () => {
    loadUser();
    setupTooltips();
    setupSearchListeners();
});

/* Tooltips */
function setupTooltips() {
    const tooltip = document.getElementById("tooltip");
    if (!tooltip) return;

    document.querySelectorAll(".pollutant-card").forEach(card => {
        card.addEventListener("mouseenter", () => {
            const info = card.getAttribute("data-info");
            if (info) {
                tooltip.innerText = info;
                tooltip.style.opacity = 1;
            }
        });

        card.addEventListener("mousemove", (e) => {
            tooltip.style.left = e.pageX + 12 + "px";
            tooltip.style.top = e.pageY + 12 + "px";
        });

        card.addEventListener("mouseleave", () => {
            tooltip.style.opacity = 0;
        });
    });
}

/* Search Listeners */
function setupSearchListeners() {
    if (!searchInput) return;

    searchInput.addEventListener("input", function () {
        const query = this.value.trim();
        clearTimeout(debounceTimer);

        if (query.length < 2) {
            suggestionsBox.classList.remove("active");
            suggestionsBox.innerHTML = "";
            return;
        }

        debounceTimer = setTimeout(() => {
            fetchCities(query);
        }, 300);
    });

    if (searchBtn) {
        searchBtn.addEventListener("click", () => {
            const query = searchInput.value.trim();
            if (query.length >= 2) {
                fetchCities(query);
            }
        });
    }

    /* Hide suggestions on click outside */
    document.addEventListener("click", function (e) {
        if (!e.target.closest(".search-section")) {
            suggestionsBox.classList.remove("active");
        }
    });
}

/* Fetch Autocomplete City Suggestions */
async function fetchCities(query) {
    try {
        const response = await fetch(`/autocomplete?query=${encodeURIComponent(query)}`, {
            headers: { "X-Requested-With": "XMLHttpRequest" }
        });

        if (!response.ok) return;
        const data = await response.json();

        suggestionsBox.innerHTML = "";

        if (!data || data.length === 0) {
            suggestionsBox.classList.remove("active");
            return;
        }

        // Take top 5 matches
        const topMatches = data.slice(0, 5);

        topMatches.forEach(place => {
            const item = document.createElement("div");
            item.className = "suggestion-item";
            item.textContent = `${place.city}, ${place.state}`;

            item.addEventListener("click", () => {
                const lat = place.location.y;
                const lon = place.location.x;

                searchInput.value = `${place.city}, ${place.state}`;
                suggestionsBox.classList.remove("active");

                fetchAQI(lat, lon);
            });

            suggestionsBox.appendChild(item);
        });

        suggestionsBox.classList.add("active");

    } catch (error) {
        console.error("Error fetching city suggestions:", error);
    }
}

/* Fetch AQI Data by Coordinates */
async function fetchAQI(lat, lon) {
    try {
        if (loadingOverlay) loadingOverlay.style.display = "flex";

        const response = await fetch(`/Aqi?lat=${lat}&lon=${lon}`);
        if (!response.ok) {
            if (loadingOverlay) loadingOverlay.style.display = "none";
            return;
        }

        const data = await response.json();

        updateDashboard(data);
        renderHealthRecommendations(data.Aqi);

        if (loadingOverlay) loadingOverlay.style.display = "none";

    } catch (error) {
        console.error("AQI fetch error:", error);
        if (loadingOverlay) loadingOverlay.style.display = "none";
    }
}

/* Update Dashboard Interface */
function updateDashboard(data) {
    if (!data) return;

    // Location & Timestamp
    const cityElem = document.getElementById("currentCity");
    if (cityElem) cityElem.textContent = `${data.city}, ${data.state}`;

    const timestampElem = document.getElementById("timestamp");
    if (timestampElem) timestampElem.textContent = data.lastUpdate || "Just now";

    const lastUpdateElem = document.getElementById("lastUpdate");
    if (lastUpdateElem) lastUpdateElem.textContent = data.lastUpdate || "Just now";

    // AQI Value
    const aqiVal = Number(data.Aqi) || 0;
    const aqiElem = document.getElementById("aqiValue");
    if (aqiElem) aqiElem.textContent = aqiVal;

    // Category Badge
    const categoryInfo = getAQICategory(aqiVal);
    const categoryElem = document.getElementById("aqiCategory");
    if (categoryElem) {
        categoryElem.textContent = categoryInfo.label;
        categoryElem.style.backgroundColor = categoryInfo.bg;
        categoryElem.style.color = categoryInfo.color;
        categoryElem.style.borderColor = categoryInfo.border;
    }

    // Gauge Fill Animation
    updateGauge(aqiVal);

    // Pollutants
    if (data.pollutants) {
        updatePollutants(data.pollutants);
    }
}

/* Update Pollutant Cards */
function updatePollutants(pollutants) {
    pollutants.forEach(p => {
        const val = p.avgValue || "--";
        const status = getPollutantStatus(val);

        if (p.pollutantId === "PM2.5") {
            const v = document.getElementById("pm25Value");
            const s = document.getElementById("pm25Status");
            if (v) v.textContent = val;
            if (s) {
                s.textContent = status.label;
                s.style.backgroundColor = status.bg;
                s.style.color = status.color;
            }
        }
        if (p.pollutantId === "PM10") {
            const v = document.getElementById("pm10Value");
            const s = document.getElementById("pm10Status");
            if (v) v.textContent = val;
            if (s) {
                s.textContent = status.label;
                s.style.backgroundColor = status.bg;
                s.style.color = status.color;
            }
        }
        if (p.pollutantId === "NO2") {
            const v = document.getElementById("no2Value");
            const s = document.getElementById("no2Status");
            if (v) v.textContent = val;
            if (s) {
                s.textContent = status.label;
                s.style.backgroundColor = status.bg;
                s.style.color = status.color;
            }
        }
        if (p.pollutantId === "CO") {
            const v = document.getElementById("coValue");
            const s = document.getElementById("coStatus");
            if (v) v.textContent = val;
            if (s) {
                s.textContent = status.label;
                s.style.backgroundColor = status.bg;
                s.style.color = status.color;
            }
        }
    });
}

/* Get Category Styles */
function getAQICategory(aqi) {
    if (aqi <= 50) {
        return { label: "Good (0–50)", bg: "var(--good-bg)", color: "var(--good-text)", border: "var(--good-border)" };
    }
    if (aqi <= 100) {
        return { label: "Satisfactory (51–100)", bg: "var(--satisfactory-bg)", color: "var(--satisfactory-text)", border: "var(--satisfactory-border)" };
    }
    if (aqi <= 200) {
        return { label: "Moderate (101–200)", bg: "var(--moderate-bg)", color: "var(--moderate-text)", border: "var(--moderate-border)" };
    }
    if (aqi <= 300) {
        return { label: "Poor (201–300)", bg: "var(--poor-bg)", color: "var(--poor-text)", border: "var(--poor-border)" };
    }
    if (aqi <= 400) {
        return { label: "Very Poor (301–400)", bg: "var(--very-poor-bg)", color: "var(--very-poor-text)", border: "var(--very-poor-border)" };
    }
    return { label: "Severe (401–500)", bg: "var(--severe-bg)", color: "var(--severe-text)", border: "var(--severe-border)" };
}

/* Update SVG Arc Gauge */
function updateGauge(aqi) {
    const gauge = document.getElementById("gaugeFill");
    if (!gauge) return;

    const maxAQI = 500;
    const circumference = 251.2;
    const percent = Math.min(Math.max(aqi / maxAQI, 0), 1);
    const offset = circumference * (1 - percent);

    gauge.style.transition = "stroke-dashoffset 0.8s ease-in-out";
    gauge.style.strokeDashoffset = offset;
}

/* Pollutant Level Status */
function getPollutantStatus(value) {
    const num = Number(value) || 0;
    if (num <= 50) {
        return { label: "Good", bg: "var(--good-bg)", color: "var(--good-text)" };
    }
    if (num <= 100) {
        return { label: "Moderate", bg: "var(--moderate-bg)", color: "var(--moderate-text)" };
    }
    if (num <= 200) {
        return { label: "Unhealthy", bg: "var(--poor-bg)", color: "var(--poor-text)" };
    }
    return { label: "Hazardous", bg: "var(--severe-bg)", color: "var(--severe-text)" };
}

/* Health Recommendations & Gear */
function renderHealthRecommendations(aqi) {
    const healthTitle = document.getElementById("healthTitle");
    const healthDesc = document.getElementById("healthDescription");
    const healthTips = document.getElementById("healthTips");
    const productsBox = document.getElementById("productRecommendations");

    if (!healthTitle || !healthDesc || !healthTips || !productsBox) return;

    let title = "";
    let desc = "";
    let tips = [];

    if (aqi <= 50) {
        title = "Ideal Air Quality";
        desc = "Air quality is considered satisfactory, and air pollution poses little or no risk.";
        tips = [
            "🌿 Great time for outdoor physical activities and walks.",
            "🪟 Keep windows open to ventilate indoor spaces with fresh air."
        ];
    } else if (aqi <= 100) {
        title = "Acceptable Air Quality";
        desc = "Air quality is acceptable; however, sensitive individuals may experience minor irritation.";
        tips = [
            "🏃 Unusually sensitive people should consider reducing prolonged outdoor exertion.",
            "🪟 Enjoy normal outdoor activities."
        ];
    } else if (aqi <= 200) {
        title = "Moderate Pollution Alert";
        desc = "Breathing discomfort may affect sensitive groups such as children, elderly, and asthma patients.";
        tips = [
            "😷 Wear an N95 mask if spending extended time near heavy traffic.",
            "🏠 Sensitive groups should limit prolonged outdoor exertion."
        ];
    } else if (aqi <= 300) {
        title = "Poor Air Quality Alert";
        desc = "Healthy individuals may experience respiratory discomfort on prolonged exposure.";
        tips = [
            "😷 Wear certified N95 masks when stepping outdoors.",
            "🏠 Avoid strenuous outdoor workouts; move activities indoors.",
            "🌀 Run an indoor HEPA air purifier in bedroom and living spaces."
        ];
    } else {
        title = "Severe Air Quality Warning";
        desc = "Emergency environmental condition. Serious health impacts for the entire population.";
        tips = [
            "🚨 Avoid all non-essential outdoor travel and activities.",
            "😷 Always wear an N95/N99 respirator mask outdoors.",
            "🌀 Keep windows closed and operate HEPA air purifiers inside."
        ];
    }

    healthTitle.textContent = title;
    healthDesc.textContent = desc;

    healthTips.innerHTML = tips.map(tip => `
        <div class="tip-item">
            <span>${tip}</span>
        </div>
    `).join("");

    // Render Product Cards if AQI > 100
    productsBox.innerHTML = "";
    if (aqi > 100) {
        products.forEach(p => {
            const productHtml = `
                <div class="product-item-card">
                    <img src="${p.image}" class="product-img" alt="${p.name}">
                    <div class="product-name">${p.name}</div>
                    <div class="product-price">${p.price}</div>
                    <a href="${p.link}" target="_blank" rel="noopener noreferrer" class="buy-btn">Buy on Amazon</a>
                </div>
            `;
            productsBox.innerHTML += productHtml;
        });
    }
}

/* Load Current User Information */
function loadUser() {
    fetch("/auth/me")
        .then(res => {
            if (res.ok) return res.json();
            throw new Error("Failed to load user");
        })
        .then(data => {
            const username = data.username || "User";
            const userElem = document.getElementById("username");
            const avatarElem = document.getElementById("avatarInitial");

            if (userElem) userElem.textContent = username;
            if (avatarElem) avatarElem.textContent = username.charAt(0).toUpperCase();
        })
        .catch(() => {
            const userElem = document.getElementById("username");
            if (userElem) userElem.textContent = "Guest User";
        });
}
