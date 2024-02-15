function displayTestResult(result, color) {
    const questionDiv = document.getElementById('questionDiv');
    let h1Result = document.createElement('h1');
    h1Result.style.fontSize = '256px';
    h1Result.textContent = result;
    h1Result.style.color = color;
    h1Result.style.backgroundColor = 'white';
    h1Result.style.width = '512px';
    h1Result.style.height = '512px';
    h1Result.style.borderRadius = '256px';
    h1Result.style.display = 'flex';
    h1Result.style.alignItems = 'center';
    h1Result.style.justifyContent = 'center';
    h1Result.style.marginLeft = 'auto';
    h1Result.style.marginRight = 'auto';
    questionDiv.appendChild(h1Result);
}
