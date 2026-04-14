const searchInput = document.getElementById("citySearch");
const suggestionsBox = document.getElementById("searchSuggestions");

const products = [
    {
        name: "3M N95 Mask",
        price: "₹569",
        image: "images/3M_N95.jpg",
        link: "https://www.amazon.in/s?k=3m+n95"
    },
    {
        name: "Reusable N95 Mask",
        price: "₹299",
        image: "images/N95.jpg",
        link: "https://www.amazon.in/s?k=n95+mask"
    },
    {
        name: "LEVOIT Air Purifier",
        price: "₹9,999",
        image: "images/purifier.webp",
        link: "https://www.amazon.in/s?k=levoit+air+purifier"
    },
    {
        name: "Coway Airmega 150",
        price: "₹15,998",
        image: "images/Coway_purifier.jpg",
        link: "https://www.amazon.in/s?k=coway+air+purifier"
    }
];

let debounceTimer;

/* Listen for typing */
searchInput.addEventListener("input", function () {

    const query = this.value.trim();

    clearTimeout(debounceTimer);

    if (query.length < 2) {
        suggestionsBox.classList.remove("active");
        suggestionsBox.innerHTML = "";
        return;
    }

    /* Delay API request */
    debounceTimer = setTimeout(() => {
        fetchCities(query);
    }, 100);
});

document.addEventListener("DOMContentLoaded", () => {

    const tooltip = document.getElementById("tooltip");


    document.querySelectorAll(".pollutant-card").forEach(card => {

        card.addEventListener("mouseenter", () => {
            tooltip.innerText = card.getAttribute("data-info");
            tooltip.style.opacity = 1;
        });

        card.addEventListener("mousemove", (e) => {
            tooltip.style.left = e.pageX + 15 + "px";
            tooltip.style.top = e.pageY + 15 + "px";
        });

        card.addEventListener("mouseleave", () => {
            tooltip.style.opacity = 0;
        });

    });

});

function createProductCard(p) {
    return `
        <div class="product-card">
            <img src="${p.image}" class="product-img" alt="${p.name}">
            <div class="product-info">
                <div class="product-title">${p.name}</div>
                <div class="product-price">${p.price}</div>
                <a href="${p.link}" target="_blank" class="buy-btn">Buy</a>
            </div>
        </div>
    `;
}

function renderHealthProducts(aqi) {

    const container = document.getElementById("productRecommendations");
    const healthContent = document.querySelector(".health-content");

    container.innerHTML = "";

    if (aqi < 120) {
        healthContent.style.display = "block";
        container.style.display = "none";
        return;
    }

    healthContent.style.display = "none";
    container.style.display = "grid";

    products.slice(0, 4).forEach(p => {
        container.innerHTML += `
            <div class="amazon-card">
                <img src="${p.image}" alt="${p.name}">
                <div class="amazon-overlay">
                    <div class="amazon-title">${p.name}</div>
                    <div class="amazon-price">${p.price}</div>
                    <a href="${p.link}" target="_blank" class="amazon-btn">Buy</a>
                </div>
            </div>
        `;
    });
}

async function fetchCities(query) {

    try {

        const response = await fetch(`/autocomplete?query=${query}`,{headers:{"X-Requested-With":"XMLHttpRequest"}});
        const data = await response.json();

        suggestionsBox.innerHTML = "";

        if (!data || data.length === 0) {
            suggestionsBox.classList.remove("active");
            return;
        }

        // Take only top 5 matches
        const topMatches = data.slice(0, 5);

        topMatches.forEach(place => {

                       const item = document.createElement("div");
                       item.className = "suggestion-item";

                       item.textContent = `${place.city}, ${place.state}`;

                       item.addEventListener("click", () => {

                           const lat = place.location.y;
                           const lon = place.location.x;

                           fetchAQI(lat, lon);

                           suggestionsBox.classList.remove("active");
                           searchInput.value = `${place.city}, ${place.state}`;

                       });

                       suggestionsBox.appendChild(item);

                   });

        suggestionsBox.classList.add("active");

    } catch (error) {
        console.error("Error fetching city suggestions:", error);
    }
}



/* Hide suggestions when clicking outside */
document.addEventListener("click", function (e) {

    if (!e.target.closest(".search-section")) {
        suggestionsBox.classList.remove("active");
    }

});

searchInput.addEventListener("input", () => {

    const query = searchInput.value.trim();

    clearTimeout(debounceTimer);

    if (query.length < 2) {
        suggestionsBox.classList.remove("active");
        return;
    }

    debounceTimer = setTimeout(() => {
        fetchCities(query);
    }, 400);
});

document.addEventListener('DOMContentLoaded',(event)=>{LoadUser();});

item.addEventListener("click", () => {

    searchInput.value = `${place.city}, ${place.state}`;
    suggestionsBox.classList.remove("active");

    const lat = place.location.y;
    const lon = place.location.x;

    fetchAQI(lat, lon);

});



async function fetchAQI(lat, lon) {

    try {

        document.getElementById("loadingOverlay").style.display = "flex";

        const response = await fetch(`/Aqi?lat=${lat}&lon=${lon}`);
        const data = await response.json();

        updateDashboard(data);
        renderHealthProducts(data.Aqi);

        document.getElementById("loadingOverlay").style.display = "none";



    } catch (error) {
        console.error("AQI fetch error:", error);
    }
}
function updateDashboard(data) {

    // Location
    document.getElementById("currentCity").textContent =
        `${data.city}, ${data.state}`;

    // Timestamp
    document.getElementById("timestamp").textContent = data.lastUpdate;
    document.getElementById("lastUpdate").textContent = data.lastUpdate;

    // AQI Value
    document.getElementById("aqiValue").textContent = data.Aqi;

    // AQI Category
    const category = getAQICategory(data.Aqi);
    document.getElementById("aqiCategory").textContent = category;

    // Update Gauge
    updateGauge(data.Aqi);

    // Pollutants
    updatePollutants(data.pollutants);

}
function updatePollutants(pollutants) {

    pollutants.forEach(p => {

        if (p.pollutantId === "PM2.5") {
            document.getElementById("pm25Value").textContent = p.avgValue;
            document.getElementById("pm25Status").textContent = getPollutantStatus(p.avgValue);
        }

        if (p.pollutantId === "PM10") {
            document.getElementById("pm10Value").textContent = p.avgValue;
            document.getElementById("pm10Status").textContent = getPollutantStatus(p.avgValue);
        }

        if (p.pollutantId === "NO2") {
            document.getElementById("no2Value").textContent = p.avgValue;
            document.getElementById("no2Status").textContent = getPollutantStatus(p.avgValue);
        }

        if (p.pollutantId === "CO") {
            document.getElementById("coValue").textContent = p.avgValue;
            document.getElementById("coStatus").textContent = getPollutantStatus(p.avgValue);
        }

    });

}
function getAQICategory(aqi) {

    if (aqi <= 50) return "Good";
    if (aqi <= 100) return "Satisfactory";
    if (aqi <= 200) return "Moderate";
    if (aqi <= 300) return "Poor";
    if (aqi <= 400) return "Very Poor";

    return "Severe";
}
function updateGauge(aqi) {

    const maxAQI = 500;
    const gauge = document.getElementById("gaugeFill");

    const circumference = 251.2;

    const percent = Math.min(aqi / maxAQI, 1);

    const offset = circumference * (1 - percent);

    gauge.style.strokeDashoffset = offset;
}
function getPollutantStatus(value) {

    value = Number(value);

    if (value <= 50) return "Good";
    if (value <= 100) return "Moderate";
    if (value <= 200) return "Unhealthy";

    return "Hazardous";
}
function LoadUser(){
    fetch("/auth/me")
        .then(response => response.json())
        .then(data => {
            document.getElementById("username").innerText = data.username;
        })
        .catch(() => {
            document.getElementById("username").innerText = "Error loading user";
        });
}

// Logout function
function logout() {
    fetch("/logout", {
        method: "POST"
    }).then(() => {
        window.location.href = "/login.html";
    });
}

