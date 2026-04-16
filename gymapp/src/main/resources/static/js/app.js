const BASE_URL = "https://gym-app-47sf.onrender.com/api/gym";

let AUTH_HEADER = "";
let currentUserId = "";

async function attemptLogin() {
    const role = document.getElementById("loginRole").value;
    const id = document.getElementById("userId").value;
    const pass = document.getElementById("userPass").value;

    try {
        const adminCreds = 'Basic ' + btoa('admin:admin123');

        if (role === "ADMIN") {
            const userEnteredCreds = 'Basic ' + btoa(id + ':' + pass);

            // We try to fetch members to see if these credentials work
            const res = await fetch(`${BASE_URL}/members/all`, {
                headers: { 'Authorization': userEnteredCreds }
            });

            if (res.ok) {
                // Save these credentials for future calls!
                setupDashboard("ADMIN", userEnteredCreds);
            } else {
                showToast("Invalid Admin Credentials", "bg-danger");
            }
        }
        else if (role === "TRAINER") {
            // Check if your Java uses POST or GET for this endpoint
            const res = await fetch(`${BASE_URL}/trainers/login?id=${id}&password=${pass}`, {
                method: 'POST',
                headers: { 'Authorization': adminCreds }
            });
            const msg = await res.text();
            if (msg.includes("Successful")) setupDashboard("TRAINER", adminCreds);
            else showToast("Invalid Trainer", "bg-danger");
        }
        else if (role === "MEMBER") {
            const res = await fetch(`${BASE_URL}/members/${id}`, {
                headers: { 'Authorization': adminCreds }
            });
            if (res.ok) {
                const member = await res.json();
                currentUserId = id;
                setupDashboard("MEMBER", adminCreds);
            } else {
                showToast("Member ID Not Found", "bg-danger");
            }
        }
    } catch (err) {
        console.error(err);
        showToast("Server Connection Failed", "bg-danger");
    }
}

function setupDashboard(role, creds) {
    AUTH_HEADER = creds;

    // 1. Swap main screens
    document.getElementById("loginSection").classList.add("d-none");
    document.getElementById("mainDashboard").classList.remove("d-none");

    // 2. Hide all tab buttons and panes to reset
    const allNavItems = ["nav-admin", "nav-trainer", "nav-customer"];
    const allPanes = ["admin", "trainer", "customer"];

    allNavItems.forEach(id => document.getElementById(id).classList.add("d-none"));
    allPanes.forEach(id => {
        const pane = document.getElementById(id);
        pane.classList.remove("show", "active");
    });

    // 3. Show correct content based on role
    let targetBtnId = "";
    if (role === "ADMIN") {
        document.getElementById("nav-admin").classList.remove("d-none");
        targetBtnId = "admin-tab"; // Matches your original button ID
        refreshData();
    }
    else if (role === "TRAINER") {
        document.getElementById("nav-trainer").classList.remove("d-none");
        targetBtnId = "trainer-tab";
    }
    else if (role === "MEMBER") {
        document.getElementById("nav-customer").classList.remove("d-none");
        targetBtnId = "customer-tab";
    }

    // 4. Trigger Bootstrap to activate the tab pane
    const targetBtn = document.getElementById(targetBtnId);
    if (targetBtn) {
        const tab = new bootstrap.Tab(targetBtn);
        tab.show();
    }

    showToast(`Logged in as ${role}`);
}

// --- SECURE FETCH WRAPPER ---

async function secureFetch(url, options = {}) {
    const defaultHeaders = {
        'Authorization': AUTH_HEADER,
        'Content-Type': 'application/json'
    };
    options.headers = { ...defaultHeaders, ...options.headers };

    const response = await fetch(url, options);

    // Handle specific errors like the 404 seen in Postman
    if (response.status === 404) {
        showToast("Endpoint not found (404). Check Backend URLs.", "bg-danger");
    }
    if (response.status === 401 || response.status === 403) {
        showToast("Session Expired or Unauthorized", "bg-danger");
    }
    return response;
}

// --- HELPER FUNCTIONS ---

function showToast(message, color = 'bg-success') {
    const toastEl = document.getElementById('liveToast');
    const toastBody = document.getElementById('toastMessage');
    toastEl.className = `toast align-items-center text-white ${color} border-0`;
    toastBody.innerText = message;
    const toast = new bootstrap.Toast(toastEl);
    toast.show();
}

function refreshData() {
    if (AUTH_HEADER) fetchMembers();
}

// --- DASHBOARD ACTIONS ---

async function fetchMembers() {
    try {
        const res = await secureFetch(`${BASE_URL}/members/all`);
        const data = await res.json();
        const table = document.getElementById("adminMemberTable");

        if (!data || data.length === 0) {
            table.innerHTML = `<tr><td colspan="5" class="text-center py-4 text-muted">No members registered yet.</td></tr>`;
            return;
        }

        table.innerHTML = data.map(m => `
            <tr>
                <td>${m.id}</td>
                <td><b>${m.name}</b></td>
                <td><span class="badge bg-info text-dark">${m.assignedTrainer ? m.assignedTrainer.trainerId : 'None'}</span></td>
                <td>${m.endDate}</td>
                <td><button class="btn btn-sm btn-danger" onclick="uiDeleteMember(${m.id})">Delete</button></td>
            </tr>
        `).join('');
    } catch (err) { console.error("Fetch error:", err); }
}

async function uiRegisterMember() {
    const name = document.getElementById("memName").value;
    const age = parseInt(document.getElementById("memAge").value);
    const type = document.getElementById("memType").value;

    if (!name.match(/^[a-zA-Z ]+$/) || isNaN(age) || age < 16) {
        showToast("Invalid Input: Letters only for name, Age min 16.", "bg-danger");
        return;
    }

    const res = await secureFetch(`${BASE_URL}/members/add`, {
        method: 'POST',
        body: JSON.stringify({ name, age, membershipType: type })
    });

    if (res.ok) {
        showToast(`Member ${name} added!`);
        document.getElementById("memName").value = "";
        document.getElementById("memAge").value = "";
        refreshData();
    }
}

async function uiAddTrainer() {
    const payload = {
        trainerId: document.getElementById("trId").value,
        password: document.getElementById("trPass").value,
        specialization: document.getElementById("trSpec").value
    };

    // Note: If you get a 404 here, ensure your Java Controller has @PostMapping("/trainers/add")
    const res = await secureFetch(`${BASE_URL}/trainers/add`, {
        method: 'POST',
        body: JSON.stringify(payload)
    });

    if (res.ok) {
        showToast("Trainer added successfully!");
        document.getElementById("trId").value = "";
        document.getElementById("trPass").value = "";
        document.getElementById("trSpec").value = "";
    }
}

async function uiAssignTrainer() {
    const mId = document.getElementById("assignMemId").value;
    const tId = document.getElementById("assignTrId").value;
    const res = await secureFetch(`${BASE_URL}/assign/${mId}/${tId}`, { method: 'PUT' });

    if (res.ok) {
        showToast("Trainer assigned successfully!");
        refreshData();
    } else {
        showToast("Assignment failed. Verify IDs.", "bg-danger");
    }
}

async function uiRenewMembership() {
    const id = document.getElementById("renewId").value;
    const type = document.getElementById("renewType").value;
    const res = await secureFetch(`${BASE_URL}/members/renew/${id}?type=${type}`, { method: 'PUT' });

    if (res.ok) {
        const m = await res.json();
        showToast(`Renewed until ${m.endDate}`);
        refreshData();
    } else {
        showToast("Renewal failed.", "bg-danger");
    }
}

async function uiDeleteMember(id) {
    if (confirm("Permanently delete this member?")) {
        const res = await secureFetch(`${BASE_URL}/members/${id}`, { method: 'DELETE' });
        if (res.ok) {
            showToast("Member deleted successfully.", "bg-secondary");
            refreshData();
        }
    }
}