const GOOGLE_CLIENT_ID = "666048447334-t344ac51rjuea7jkf9iep58oboarn2h7.apps.googleusercontent.com";

function showFormMessage(message, type = "error") {
    const messageBox = document.getElementById("formMessage");
    if (!messageBox) return;

    messageBox.textContent = message || "Something went wrong. Please try again.";
    messageBox.className = `form-message ${type}`;
    messageBox.style.display = "block";
}

function clearFormMessage() {
    const messageBox = document.getElementById("formMessage");
    if (!messageBox) return;

    messageBox.textContent = "";
    messageBox.className = "form-message";
    messageBox.style.display = "none";
}

function decodeGoogleJwt(token) {
    const payload = token.split(".")[1];
    const normalized = payload.replace(/-/g, "+").replace(/_/g, "/");
    const json = decodeURIComponent(
        atob(normalized)
            .split("")
            .map(char => `%${(`00${char.charCodeAt(0).toString(16)}`).slice(-2)}`)
            .join("")
    );
    return JSON.parse(json);
}

function setupGoogleAuth(config) {
    const button = document.getElementById(config.buttonId);
    if (!button) return;

    let attempts = 0;
    const initialise = () => {
        attempts += 1;

        if (!window.google || !window.google.accounts || !window.google.accounts.id) {
            if (attempts < 30) {
                window.setTimeout(initialise, 150);
                return;
            }
            showFormMessage("Google authentication is unavailable. Please check your internet connection.", "error");
            return;
        }

        window.google.accounts.id.initialize({
            client_id: GOOGLE_CLIENT_ID,
            callback: response => {
                try {
                    const profile = decodeGoogleJwt(response.credential);
                    const displayName = profile.name || profile.given_name || "Google User";
                    const email = profile.email || "";

                    localStorage.setItem("authToken", response.credential);
                    localStorage.setItem("authProvider", "google");
                    localStorage.setItem("profileName", displayName);
                    localStorage.setItem("profileEmail", email);

                    if (config.role === "FACULTY") {
                        localStorage.setItem("facultyName", displayName);
                    }
                    if (config.role === "ADMIN") {
                        localStorage.setItem("adminName", displayName);
                    }
                    if (config.role === "STUDENT") {
                        localStorage.setItem("studentName", displayName);
                    }

                    showFormMessage("Google authentication successful. Redirecting...", "success");
                    window.setTimeout(() => {
                        window.location.href = config.redirectUrl;
                    }, 900);
                } catch (error) {
                    console.error("Google authentication error:", error);
                    showFormMessage("Something went wrong while signing in with Google.", "error");
                }
            }
        });

        window.google.accounts.id.renderButton(button, {
            theme: "outline",
            size: "large",
            shape: "rectangular",
            width: Math.min(button.clientWidth || 360, 400),
            text: config.text || "continue_with"
        });
    };

    initialise();
}
