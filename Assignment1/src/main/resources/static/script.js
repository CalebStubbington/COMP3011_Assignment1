const apiButton = document.querySelector("#apiButton");
const result = document.querySelector("#result");

apiButton.addEventListener("click", async () => {
    result.className = "result";
    result.textContent = "Contacting the backend...";

    try {
        const response = await fetch("/api/welcome");

        if (!response.ok) {
            throw new Error(
                `Request failed with status ${response.status}`
            );
        }

        const data = await response.json();

        result.classList.add("success");
        result.textContent = data.welcome;
    } catch (error) {
        result.classList.add("error");
        result.textContent = "The backend could not be reached.";

        console.error(error);
    }
});