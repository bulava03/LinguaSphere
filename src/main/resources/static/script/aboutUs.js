const article = document.querySelector('article');
const h2Text = "LinguaSphere: інтегрована платформа вивчення іноземних мов";
const h2Element = document.createElement('h2');
article.insertBefore(h2Element, article.firstChild);

let leftIndex = h2Text.length / 2;
let rightIndex = h2Text.length / 2;
if (rightIndex == leftIndex) {
    rightIndex += 1;
}
let leftText = "";
let rightText = "";

const printH2Text = () => {
    if (leftIndex >= 0) {
        leftText = h2Text[leftIndex] + leftText;
        leftIndex--;
    }
    if (rightIndex < h2Text.length) {
        rightText += h2Text[rightIndex];
        rightIndex++;
    }
    if (leftIndex >= -1 || rightIndex <= h2Text.length) {
        h2Element.innerHTML = leftText + rightText;
        setTimeout(printH2Text, 350);
    }
};

printH2Text();
