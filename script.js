document.addEventListener('DOMContentLoaded', function () {
    const jsonInput = document.getElementById('json-input');
    const jsonViewer = new JSONViewer();
    const viewerContainer = document.getElementById('json-tree-view');
    viewerContainer.appendChild(jsonViewer.getContainer());

    jsonInput.addEventListener('input', function () {
        const input = this.value.trim(); // Trim whitespace from both ends
        viewerContainer.textContent = ''; // Clear the previous view

        try {
            const json = JSON.parse(input); // Parse JSON
            jsonViewer.showJSON(json);
        } catch (e) {
            console.error("Parsing error:", e); // Output error details to the console
            viewerContainer.textContent = 'Invalid JSON! ' + e.message;
        }
    });
});
